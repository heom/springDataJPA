package me.study.datajpa.repository;

import me.study.datajpa.entity.Team;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * @FileName TeamJpaRepository.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-22
 * @Description 순수 JPA Team Repository
 **/
@Repository
public class TeamJpaRepository {

    @PersistenceContext
    private EntityManager em;

    //순수 JPA 경우, Spring-Data-JPA 기본 메소드 예시
    public Team save(Team team){
        em.persist(team);
        return team;
    }

    //순수 JPA 경우, Spring-Data-JPA 기본 메소드 예시
    public void delete(Team team){
        em.remove(team);
    }

    //순수 JPA 경우, Spring-Data-JPA 기본 메소드 예시
    public List<Team> findAll(){
        return em.createQuery("select t from Team t", Team.class)
                .getResultList();
    }

    //순수 JPA 경우, Spring-Data-JPA 기본 메소드 예시
    public Optional<Team> findById(Long id){
        Team team = em.find(Team.class, id);
        return Optional.ofNullable(team);
    }

    //순수 JPA 경우, Spring-Data-JPA 기본 메소드 예시
    public long count(){
        return em.createQuery("select count(t) from Team t", Long.class)
                .getSingleResult();
    }

    //순수 JPA 경우, Spring-Data-JPA 기본 메소드 예시
    public Team find(Long id){
        return em.find(Team.class, id);
    }
}
