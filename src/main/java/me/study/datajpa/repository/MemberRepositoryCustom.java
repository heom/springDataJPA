package me.study.datajpa.repository;

import me.study.datajpa.entity.Member;

import java.util.List;

/**
 * @FileName MemberRepositoryCustom.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-23
 * @Description [Custom Repository] interface
 **/
public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
