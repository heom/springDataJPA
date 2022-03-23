package me.study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * @FileName JpaBaseEntity.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-23
 * @Description [순수 JPA 경우, Auditing] Entity
 **/
@MappedSuperclass
@Getter
public class JpaBaseEntity {
    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    @PrePersist // insert 전
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }
    @PreUpdate // update 전
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
