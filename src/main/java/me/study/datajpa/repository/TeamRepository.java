package me.study.datajpa.repository;

import me.study.datajpa.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @FileName TeamRepository.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-22
 * @Description  Spring-Data-JPA Team Repository
 **/
public interface TeamRepository extends JpaRepository<Team,Long> {
}
