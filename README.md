# SPRING-DATA-JPA
- 순수 JPA와 비교하며 SPRING-DATA-JPA 공부하기 

## 프로젝트 개발 구성
- Java 8
- Spring Boot(+Gradle) 2.2.1
- H2(Embeded)
- SpringDataJPA
- JUnit5

## 추가 정리
- **SPRING-DATA-JPA란?**
  - SpringDataJPA는 기존 JPA의 기능에 SpringData의 기능을 추가하여 더욱 추상화 하여 좀 더 손쉽게 쓸 수 있도록한 Framework  
- **SPRING-DATA-JPA 장점**
  - 기본 (crud)메소드 추상화함으로 중복되는 코드가 간결해짐
    - EntityManager를 활용한 클래스 개발 안함 / JpaRepository를 상속받은 인터페이스만 만들면 됨
  - Query Method 및 추가기능(Paging ...)이 있어 코드가 간결해짐
- **Query Method 3가지(1번과 3번만 실무에 사용하면 됨)**
  - 메소드 이름으로 쿼리 생성
    - 장점 : 컴파일 시 에러 발견 가능
    - 장점 : 간편함 - 메소드명 작성만으로 저절로 쿼리 생성 
    - 단점 : 조건이 길어질 수록 메소드명이 너무 난잡해짐
  - @NamedQuery
    - 장점 : 컴파일시 에러 발견 가능
      - 순수 JPQL 사용시 컬럼명을 잘 못써도 호출까지 에러발견을 못하지만, @NamedQuery로 작성할 경우 컴파일에서 에러 발견 가능
    - 단점 : Entity에 쿼리 작성
  - @Query
    - 장점 : 컴파일시 에러 발견 가능
    - 장점 : 2번(@NamedQuery)와 다르게 Entity에 쿼리 정의 안해도됨
- **Binding** 
  - Parameter Binding
    - 위치 기반 <- 거의 미사용
    - 이름 기반 <- 주로 사용(코드 가독성과 유지보수를 위해)
      - 메소드 이름으로 쿼리 생성 ex -> findByUsername
      - @Query 쿼리 생성 ex -> m.username = :username / @Param("username") String username
  - Collection Binding
    - in 절
      - 메소드 이름으로 쿼리 생성 ex -> findByUsernameIn
      - @Query 쿼리 생성 ex -> m.username in :names / @Param("names") List<String> names
- **Response Type**
  - [List(T), T, Optional(T), Page(T) ...] 자유로움(https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-return-types)
    - 대신 무조건 복수건과 단건 유의(IncorrectResultSizeDataAccessException)
  - NLP CHECK
    - List(T) ex -> if(members.size() == 0) 
    - T ex -> if(member == null)
    - Optional(T) ex -> if(memberOptional.isPresent() == false)
  - 순수 JPA 경우, NoResultException
- **Paging**
  - 페이징과 정렬 파라미터 
    - Sort(T) - 정렬 기능
      - org.springframework.data.domain.Sort
    - Pageable(T) - 페이징 기능(정렬 포함) 
      - org.springframework.data.domain.Pageable
  - 특별한 반환 타입
    - Page(T) - 추가 count 쿼리 결과를 포함하는 페이징
      - org.springframework.data.domain.Page
      - DTO 변환 : page.map(T -> new T(...)
      - 보통 count 쿼리가 전체 조회이기 때문에 서비스 속도에 많은 영향을 끼침!!!
        - @Query(value="목록", countQuery="갯수") 이런 형식으로 구분지어 주는게 필수!!!!
    - Slice(T) - 추가 count 쿼리 없이 다음 페이지만 확인 가능(내부적으로 limit + 1조회)
      - org.springframework.data.domain.Slice
      - DTO 변환 : slice.map(T -> new T(...)
    - List(T)
      - 추가 count 쿼리 없이 결과만 반환
- **BulkUpdate Query**
  - returnType : int / @Modifying 필수
  - 만약 한 트랜젝션이면 영속성 유지를 위하여, 꼭 BulkUpdate 이 후
    1. EntityManager flush()/clear()를 해주거나
    2. @Modifying(clearAutomatically = true) 해야한다. 
     