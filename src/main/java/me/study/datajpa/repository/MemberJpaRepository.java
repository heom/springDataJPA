package me.study.datajpa.repository;

import me.study.datajpa.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * @FileName MemberJpaRepository.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-22
 * @Description 순수 JPA Member Repository
 **/
@Repository
public class MemberJpaRepository {

    @PersistenceContext
    private EntityManager em;

    //순수 JPA 경우, Spring-Data-JPA 기본 메소드 예시
    public Member save(Member member){
        em.persist(member);
        return member;
    }

    //순수 JPA 경우, Spring-Data-JPA 기본 메소드 예시
    public void delete(Member member){
        em.remove(member);
    }

    //순수 JPA 경우, Spring-Data-JPA 기본 메소드 예시
    public List<Member> findAll(){
        //JPQL
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }

    //순수 JPA 경우, Spring-Data-JPA 기본 메소드 예시
    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    //순수 JPA 경우, Spring-Data-JPA 기본 메소드 예시
    public long count(){
        //JPQL
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }

    //순수 JPA 경우, Spring-Data-JPA 기본 메소드 예시
    public Member find(Long id){
        return em.find(Member.class, id);
    }

    /**
     * @Description 순수 JPA 경우, 1. 메소드 이름으로 쿼리 생성 예시
     * @Param username
     * @Param age
     * @Retrun List<Member>
     **/
    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age){
        //JPQL
        return em.createQuery("select m from Member m where m.username = :username and m.age > :age")
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }

    /**
     * @Description 순수 JPA 경우, 2. 메소드 이름으로 JPA NamedQuery 호출 <= 실무에선 잘 사용안함
     * @Param username
     * @Retrun List<Member>
     **/
    public List<Member> findByUsername(String username){
        return em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", username)
                .getResultList();
    }
}
