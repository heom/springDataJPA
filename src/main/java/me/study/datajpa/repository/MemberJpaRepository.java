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
    EntityManager em;

    /**
     * @Description [순수 JPA 경우, Spring-Data-JPA 기본 메소드 예시]
     **/
    public Member save(Member member){
        em.persist(member);
        return member;
    }
    public void delete(Member member){
        em.remove(member);
    }
    public List<Member> findAll(){
        //JPQL
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }
    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
    public long count(){
        //JPQL
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }
    public Member find(Long id){
        return em.find(Member.class, id);
    }


    /**
     * @Description [순수 JPA 경우, Query Method]
     **/
    // 1번, 메소드 이름으로 쿼리 생성 예시
    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age){
        //JPQL
        return em.createQuery("select m from Member m where m.username = :username and m.age > :age")
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }
    // 2번, @NamedQuery
    public List<Member> findByUsername(String username){
        //JPQL
        return em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    /**
     * @Description [순수 JPA 경우, Paging]
     **/
    public List<Member> findByPage(int age, int offset, int limit){
        return em.createQuery("select m from Member m where m.age = :age order by m.username desc")
                .setParameter("age", age)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    public long totalCount(int age){
        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }

    /**
     * @Description [순수 JPA 경우, Bulk-Update Query]
     **/
    public int bulkAgePlus(int age){
        return em.createQuery("update Member m set m.age = m.age+1 where m.age >= :age")
                .setParameter("age", age)
                .executeUpdate();
    }
}
