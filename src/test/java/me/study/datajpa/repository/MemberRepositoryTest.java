package me.study.datajpa.repository;

import me.study.datajpa.dto.MemberDto;
import me.study.datajpa.entity.Member;
import me.study.datajpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Test
    @Transactional
    public void testMember(){
        Member member= new Member("memberA");
        Member saveMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @Transactional
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 조회 검증
        Member finMember1 = memberRepository.findById(member1.getId()).get();
        Member finMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(finMember1).isEqualTo(member1);
        assertThat(finMember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //갯수 조회 검증
        Long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        //갯수 조회 검증
        Long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    @Transactional
    public void findByUsernameAndAgeGreaterThen(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> members = memberRepository.findByUsernameAndAgeGreaterThan("AAA",15);

        assertThat(members.get(0).getUsername()).isEqualTo("AAA");
        assertThat(members.get(0).getAge()).isEqualTo(20);
        assertThat(members.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void testNamedQuery(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> members = memberRepository.findByUsername("AAA");
        assertThat(members.get(0)).isEqualTo(m1);
    }

    @Test
    @Transactional
    public void testQuery(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> members = memberRepository.findUser("AAA", 20);
        assertThat(members.get(0)).isEqualTo(m2);
    }

    @Test
    @Transactional
    public void findUsernameList(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> findUsernameList = memberRepository.findUsernameList();
        assertThat(findUsernameList.get(0)).isEqualTo(m1.getUsername());
        assertThat(findUsernameList.get(1)).isEqualTo(m2.getUsername());
    }

    @Test
    @Transactional
    public void findMemberDto(){
        Team teamA = new Team("teamA");
        teamRepository.save(teamA);
        Member m1 = new Member("AAA", 10);
        m1.setTeam(teamA);
        memberRepository.save(m1);

        List<MemberDto> findUsernameList = memberRepository.findMemberDto();
        assertThat(findUsernameList.get(0).getId()).isEqualTo(m1.getId());
    }

    @Test
    @Transactional
    public void findByNames(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> names = memberRepository.findByNames(Arrays.asList("AAA", "CCC"));
        assertThat(names.size()).isEqualTo(1);
        assertThat(names.get(0).getUsername()).isEqualTo("AAA");
    }

    @Test
    @Transactional
    public void findByUsernameIn(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> names = memberRepository.findByUsernameIn(Arrays.asList("AAA", "CCC"));
        assertThat(names.size()).isEqualTo(1);
        assertThat(names.get(0).getUsername()).isEqualTo("AAA");
    }

    @Test
    @Transactional
    public void returnType(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> members = memberRepository.findListByUsername("CCC");
        Member member = memberRepository.findMemberByUsername("CCC");
        Optional<Member> memberOptional = memberRepository.findOptionalByUsername("CCC");

        assertThat(members.size()).isEqualTo(0);
        assertThat(member).isEqualTo(null);
        assertThat(memberOptional.isPresent()).isEqualTo(false);
    }
}