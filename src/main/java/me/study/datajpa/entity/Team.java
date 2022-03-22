package me.study.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName Team.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-22
 * @Description Team Entity
 **/
@Entity
@Getter @Setter //**왠만하면 setter 사용금지(공부니 붙임)**
@NoArgsConstructor(access = AccessLevel.PROTECTED) //**JPA 규정상 기본생성자는 항상 PROTECTED**
@ToString(of = {"id", "name"})
public class Team {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

    /**
     * @Description 1:N
     **/
    @OneToMany(mappedBy = "team") //**항상 FK가 없는 곳에 mappedBy**
    private List<Member> members = new ArrayList<>();

    public Team(String name){
        this.name = name;
    }
}
