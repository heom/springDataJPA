package me.study.datajpa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.study.datajpa.entity.Member;

/**
 * @FileName MemberDto.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-22
 * @Description Member Dto
 **/
@Data
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    public MemberDto(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
