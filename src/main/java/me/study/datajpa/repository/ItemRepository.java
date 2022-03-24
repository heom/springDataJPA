package me.study.datajpa.repository;

import me.study.datajpa.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @FileName ItemRepository.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-24
 * @Description [새로운 Entity인지 판별하는 법?] Repository
 **/
public interface ItemRepository extends JpaRepository<Item,Long> {

}
