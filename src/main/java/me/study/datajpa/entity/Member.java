package me.study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @FileName Member.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-22
 * @Description Member Entity
 **/
@Entity
@Getter @Setter //**왠만하면 setter 사용금지(공부니 붙임)**
@NoArgsConstructor(access = AccessLevel.PROTECTED) //**JPA 규정상 기본생성자는 항상 PROTECTED**
@ToString(of = {"id", "username", "age"})
@NamedQuery( // 2. 메소드 이름으로 JPA NamedQuery 호출
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)
@NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team")) //@EntityGraph Entity에서
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    /**
     * @Description N:1
     **/
    @ManyToOne(fetch = FetchType.LAZY) //**항상 FetchType.LAZY 유의**
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username){
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}
