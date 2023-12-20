package OTM;

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

    // 단방향 매핑
    // 일대다 양방향은 공식적으로 존재 X
    @OneToMany
    @JoinColumn(name = "TEAM_ID")   // 일대다 단방향에서 JoinColiumn은 필수
    private List<Member> members = new ArrayList<>();

}
