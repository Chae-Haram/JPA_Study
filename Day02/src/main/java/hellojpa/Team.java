package hellojpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    // 양방향 매핑
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<Member>();

    private void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }

//    public void changeTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    }
}
