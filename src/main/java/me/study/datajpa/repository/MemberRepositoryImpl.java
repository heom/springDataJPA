package me.study.datajpa.repository;

import lombok.RequiredArgsConstructor;
import me.study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @FileName MemberRepositoryImpl.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-23
 * @Description [Custom Repository] class 생성 실질적 구현부 작성
 **/
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
