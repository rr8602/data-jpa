package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id", "username", "age"})
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)
@NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team"))
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id") // name : JPA에서 사용할 이름 정의
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null){
            changeTeam(team);
        }
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public void changeUsername(String username){
        this.username = username;
    }

    // 연관관계 메서드 (FK가 있는 쪽에서 만듦)
    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}
