package me.study.datajpa.repository;

import me.study.datajpa.dto.MemberDto;
import me.study.datajpa.entity.Member;
import me.study.datajpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @FileName MemberRepositoryTest.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-23
 * @Description Spring Data JPA TEST
 **/
@SpringBootTest
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    /**
     * @Description [Spring-Data-JPA 기본 메소드]
     **/
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

    /**
     * @Description [Query Method] - 1번, 메소드 이름으로 쿼리 생성
     **/
    @Test
    @Transactional
    public void methodNameQuery(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> members = memberRepository.findByUsernameAndAgeGreaterThan("AAA",15);

        assertThat(members.get(0).getUsername()).isEqualTo("AAA");
        assertThat(members.get(0).getAge()).isEqualTo(20);
        assertThat(members.size()).isEqualTo(1);
    }

    /**
     * @Description [Query Method] - 2번, @NamedQuery
     **/
    @Test
    @Transactional
    public void namedQueryAnnotation(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> members = memberRepository.findByUsername("AAA");
        assertThat(members.get(0)).isEqualTo(m1);
    }

    /**
     * @Description [Query Method] -3번, @Query
     **/
    @Test
    @Transactional
    public void queryAnnotation(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> members = memberRepository.findUser("AAA", 20);
        assertThat(members.get(0)).isEqualTo(m2);
    }

    /**
     * @Description [반환 객체가 다를 경우] - List<String> 받는 경우
     **/
    @Test
    @Transactional
    public void resultStringList(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> findUsernameList = memberRepository.findUsernameList();
        assertThat(findUsernameList.get(0)).isEqualTo(m1.getUsername());
        assertThat(findUsernameList.get(1)).isEqualTo(m2.getUsername());
    }

    /**
     * @Description [반환 객체가 다를 경우] - List<MemberDto> 받는 경우, 다만 QueryDsl 쓰면 더 쉬워지니 우선 참고할 용도로만 씀
     **/
    @Test
    @Transactional
    public void resultAnotherDTO(){
        Team teamA = new Team("teamA");
        teamRepository.save(teamA);
        Member m1 = new Member("AAA", 10);
        m1.setTeam(teamA);
        memberRepository.save(m1);

        List<MemberDto> findUsernameList = memberRepository.findMemberDto();
        assertThat(findUsernameList.get(0).getId()).isEqualTo(m1.getId());
    }

    /**
     * @Description [Binding] - Collection Binding, 메소드 이름으로 쿼리 생성
     **/
    @Test
    @Transactional
    public void bindingMethodName(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> names = memberRepository.findByUsernameIn(Arrays.asList("AAA", "CCC"));
        assertThat(names.size()).isEqualTo(1);
        assertThat(names.get(0).getUsername()).isEqualTo("AAA");
    }

    /**
     * @Description [Binding] - Collection Binding, @Query 쿼리 생성
     **/
    @Test
    @Transactional
    public void bindingQueryAnnotation(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> names = memberRepository.findByNames(Arrays.asList("AAA", "CCC"));
        assertThat(names.size()).isEqualTo(1);
        assertThat(names.get(0).getUsername()).isEqualTo("AAA");
    }


    /**
     * @Description [Response Type] - 컬렉션/단건/단건(Optional)
     **/
    @Test
    @Transactional
    public void responseType(){
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

    /**
     * @Description [Paging]
     **/
    @Test
    @Transactional
    public void paging(){
        Team teamA = new Team("teamA");
        teamRepository.save(teamA);
        Member m1 = new Member("member1", 10);
        Member m2 = new Member("member2", 10);
        Member m3 = new Member("member3", 10);
        Member m4 = new Member("member4", 10);
        Member m5 = new Member("member5", 10);
        m1.setTeam(teamA);
        m2.setTeam(teamA);
        m3.setTeam(teamA);
        m4.setTeam(teamA);
        m5.setTeam(teamA);

        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);
        memberRepository.save(m4);
        memberRepository.save(m5);

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        Slice<Member> slice = memberRepository.findByAgeSlice(age, pageRequest);

        //꿀팁!!
//        Page<MemberDto> pageDtos = page.map(m -> new MemberDto());
//        Slice<MemberDto> sliceDtos = slice.map(member -> new MemberDto(member.getId(), member.getUsername(), member.getTeam().getName()));

        //page
        List<Member> content = page.getContent(); //조회된 데이터
        assertThat(content.size()).isEqualTo(3); //조회된 데이터 수
        assertThat(page.getTotalElements()).isEqualTo(5); //전체 데이터 수
        assertThat(page.getNumber()).isEqualTo(0); //페이지 번호
        assertThat(page.getTotalPages()).isEqualTo(2); //전체 페이지 번호
        assertThat(page.isFirst()).isTrue(); //첫번째 항목인가?
        assertThat(page.hasNext()).isTrue(); //다음 페이지가 있는가?

        //slice
        List<Member> contentSlice = slice.getContent(); //조회된 데이터
        assertThat(contentSlice.size()).isEqualTo(3); //조회된 데이터 수
        assertThat(slice.getNumber()).isEqualTo(0); //페이지 번호
        assertThat(slice.isFirst()).isTrue(); //첫번째 항목인가?
        assertThat(slice.hasNext()).isTrue(); //다음 페이지가 있는가?
    }

    /**
     * @Description [Bulk-Update Query]
     **/
    @Test
    @Transactional
    public void bulkUpdate(){
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        int resultCount = memberRepository.bulkAgePlus(20);
        //영속성 유지가 안되어, DB == 41인데 캐쉬때문에 40이 반환됨
//        List<Member> members5 = memberRepository.findByUsername("member5");
//        Member member5 = members5.get(0);
//      assertThat(member5.getAge()).isEqualTo(41); //error

        //위의 문제를 해결하기 위하여 EntityManager flush()/clear()를 해주거나
//        em.flush();
//        em.clear();
//        List<Member> members5 = memberRepository.findByUsername("member5");
//        Member member5 = members5.get(0);
//        assertThat(member5.getAge()).isEqualTo(41);

        //@Modifying(clearAutomatically = true)
        List<Member> members5 = memberRepository.findByUsername("member5");
        Member member5 = members5.get(0);
        assertThat(member5.getAge()).isEqualTo(41);

        assertThat(resultCount).isEqualTo(3);
    }

    /**
     * @Description [@EntityGraph]
     **/
    @Test
    @Transactional
    public void entityGraphAnnotation(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member1", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // FetchType.LAZY 경우 N+1 문제가 발생, 즉 추가 Query 발생
//        List<Member> members = memberRepository.findAll();
        // 1. @Query 사용 시 fetch 추가
//        List<Member> members = memberRepository.findMemberFetchJoin();
        // 2. @EntityGraph(attributePaths = {})
//        List<Member> members = memberRepository.findAll();
        // 3. 3. @NamedEntityGraph Entity 추가
        List<Member> members = memberRepository.findEntityGraphByUsername("member1");
        for(Member member:members){
            System.out.println("member => "+member);
            System.out.println("member.team.name => "+member.getTeam().getName());
        }
    }
}