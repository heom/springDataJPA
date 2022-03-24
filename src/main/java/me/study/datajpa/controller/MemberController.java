package me.study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import me.study.datajpa.entity.Member;
import me.study.datajpa.repository.MemberRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @FileName MemberController.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-24
 * @Description [Domain Converter]
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

    @PostConstruct
    public void init(){
        memberRepository.save(new Member("userA"));
    }
}
