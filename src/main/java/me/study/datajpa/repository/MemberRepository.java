package me.study.datajpa.repository;

import me.study.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @FileName MemberRepository.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-22
 * @Description Spring-Data-JPA Member Repository
 **/
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * @Description 1. 메소드 이름으로 쿼리 생성
     * @Param username
     * @Param age
     * @Retrun List<Member>
     **/
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    /**
     * @Description 2. 메소드 이름으로 JPA NamedQuery 호출  <= 실무에선 잘 사용안함
     * @Param username
     * @Retrun List<Member>
     **/
//    @Query(name = "Member.findByUsername") //없어도 되긴 함, 순서가 메소드명으로 NamedQuery로 먼저 찾기 때문에
                                            //만약, 메소드명과 다를 경우, 꼭 @Query 있어야함
    List<Member> findByUsername(@Param("username") String username);
}
