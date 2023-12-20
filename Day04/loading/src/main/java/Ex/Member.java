package Ex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    @ManyToOne(fetch = FetchType.LAZY)    // 지연로딩
//    @ManyToOne(fetch = FetchType.EAGER)     // 즉시로딩. 실무에서는 쓰면 안됨
    @JoinColumn(name = "TEAM_ID")
    private Team team;  // 연관관계의 주인
}
