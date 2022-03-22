package me.study.datajpa.repository;

import me.study.datajpa.dto.MemberDto;
import me.study.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @FileName MemberRepository.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-22
 * @Description Spring-Data-JPA Member Repository
 **/
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * @Description [Query Method]
     **/
    // 1번, 메소드 이름으로 쿼리 생성 예시
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
    // 2번, @NamedQuery
    //@Query(name = "Member.findByUsername") //없어도 되긴 함, 순서가 메소드명으로 NamedQuery로 먼저 찾기 때문에
                                             //만약, 메소드명과 다를 경우, 꼭 @Query 있어야함
    List<Member> findByUsername(@Param("username") String username);
    // 3번, @Query
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);


    /**
     * @Description [반환 객체가 다를 경우]
     **/
    // List<String> 받는 경우
    @Query("select m.username from Member m")
    List<String> findUsernameList();
    // List<MemberDto> 받는 경우, 다만 QueryDsl 쓰면 더 쉬워지니 우선 참고할 용도로만 씀
    @Query("select new me.study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();


    /**
     * @Description [Binding]
     **/
    // Collection Binding, 메소드 이름으로 쿼리 생성
    List<Member> findByUsernameIn(@Param("names") List<String> names);
    // Collection Binding, @Query 쿼리 생성
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);


    /**
     * @Description [반환 타입]
     **/
    List<Member> findListByUsername(String username); // 컬렉션
    Member findMemberByUsername(String username); // 단건
    Optional<Member> findOptionalByUsername(String username); //단건 Optional
}
