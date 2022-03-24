package me.study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import me.study.datajpa.dto.MemberDto;
import me.study.datajpa.entity.Member;
import me.study.datajpa.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @FileName MemberController.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-24
 * @Description [Domain Converter] // [Paging & Sort]
 **/
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    /**
     * @Description 기본적인 @PathVariable 값으로 조회하는 경우
     **/
    @GetMapping("/members/{id}")
    public  String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    /**
     * @Description [Domain Converter] DB 조회를 하지 않아도 자동으로 Spring-Data-JPA가 조회해줌
     **/
    @GetMapping("/members2/{id}")
    public  String findMember(@PathVariable("id") Member member){
        return member.getUsername();
    }

    /**
     * @Description [Paging & Sort] Pageable 파라미터
     **/
    @GetMapping("/members")
    public Page<MemberDto> list(Pageable pageable){
        return memberRepository.findAll(pageable)
                .map(MemberDto::new); // Member 파라미터로 하는  MemberDto 생성자 생성하면 이렇게 소스를 줄일 수 있음
    }

    /**
     * @Description [Paging & Sort] Pageable 파라미터 Default 값 변경
     **/
    @GetMapping(value = "/members_page")
    public Page<MemberDto> listDefaultChange(@PageableDefault(size = 12, sort = "username"
                                            , direction = Sort.Direction.DESC) Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(MemberDto::new); // Member 파라미터로 하는  MemberDto 생성자 생성하면 이렇게 소스를 줄일 수 있음
    }

//    @PostConstruct //잠깐 제외
    public void init(){
        for(int i =0; i < 100; i++){
            memberRepository.save(new Member("user" + i, i));
        }
    }
}
