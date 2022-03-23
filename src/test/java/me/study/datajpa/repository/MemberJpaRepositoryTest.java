package me.study.datajpa.repository;

import me.study.datajpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @FileName MemberJpaRepositoryTest.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-23
 * @Description 순수 JPA TEST
 **/
@SpringBootTest
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    /**
     * @Description [순수 JPA 경우, Spring-Data-JPA 기본 메소드 예시]
     **/
    @Test
    @Transactional
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        //단건 조회 검증
        Member finMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member finMember2 = memberJpaRepository.findById(member2.getId()).get();
        assertThat(finMember1).isEqualTo(member1);
        assertThat(finMember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //갯수 조회 검증
        Long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        //갯수 조회 검증
        Long deletedCount = memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    /**
     * @Description [순수 JPA 경우, Query Method] - 1번, 메소드 이름으로 쿼리 생성 예시
     **/
    @Test
    @Transactional
    public void methodNameQuery(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> members = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA",15);

        assertThat(members.get(0).getUsername()).isEqualTo("AAA");
        assertThat(members.get(0).getAge()).isEqualTo(20);
        assertThat(members.size()).isEqualTo(1);
    }

    /**
     * @Description [순수 JPA 경우, Query Method] - 2번, @NamedQuery
     **/
    @Test
    @Transactional
    public void namedQueryAnnotation(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> members = memberJpaRepository.findByUsername("AAA");
        assertThat(members.size()).isEqualTo(2);
    }

    /**
     * @Description [순수 JPA 경우, Paging]
     **/
    @Test
    @Transactional
    public void paging(){
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));

        int age = 10;
        int offset = 0;
        int limit = 3;

        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        Long totalCount = memberJpaRepository.totalCount(age);

        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(5);
    }

    /**
     * @Description [순수 JPA 경우, Bulk-Update Query]
     **/
    @Test
    @Transactional
    public void bulkUpdate(){
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 19));
        memberJpaRepository.save(new Member("member3", 20));
        memberJpaRepository.save(new Member("member4", 21));
        memberJpaRepository.save(new Member("member5", 40));

        int resultCount = memberJpaRepository.bulkAgePlus(20);
        assertThat(resultCount).isEqualTo(3);
    }
}
