# SPRING-DATA-JPA
- 순수 JPA와 비교하며 SPRING-DATA-JPA 공부하기 

## 프로젝트 개발 구성
- Java 8
- Spring Boot(+Gradle) 2.2.1
- H2(Embeded)
- SpringDataJPA
- JUnit5

## 추가 정리
- SPRING-DATA-JPA란?
  - SpringDataJPA는 기존 JPA의 기능에 SpringData의 기능을 추가하여 더욱 추상화 하여 좀 더 손쉽게 쓸 수 있도록한 Framework  
- 장점
  - 기본 (crud)메소드 추상화함으로 중복되는 코드가 간결해짐
    - EntityManager를 활용한 클래스 개발 안함 / JpaRepository를 상속받은 인터페이스만 만들면 됨
  - Query Method 기능이 추가되어 코드가 간결해짐
- Query Method 3가지
  - 메소드 이름으로 쿼리 생성
    - 장점 : 컴파일 시 에러 발견 가능
    - 장점 : 간편함 - 메소드명 작성만으로 저절로 쿼리 생성 
    - 단점 : 조건이 길어질 수록 메소드명이 너무 난잡해짐
  - 메소드 이름으로 JPA @NamedQuery 호출 - 실무에서 거의 미사용
    - 장점 : 컴파일시 에러 발견 가능
      - 순수 JPQL 사용시 컬럼명을 잘 못써도 호출까지 에러발견을 못하지만, @NamedQuery로 작성할 경우 컴파일에서 에러 발견 가능
    - 단점 : 3번(@Query)가 너무 막강함
  - @Query 어노테이션을 사용해서 리파지토리 인터페이스에 쿼리 직접 정의