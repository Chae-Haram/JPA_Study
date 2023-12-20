package Ex;

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
public class Parent {

    @Id
    @GeneratedValue
    @Column()
    private Long id;

    private String name;

//    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)  // 영속성 전이. 연관관계를 매핑하는 것과 아무 관련이 없음
                                                            // 엔티티를 영속화할 때 연관된 엔티티도 함께 영속화하는 편리함을 제공
    @OneToMany(mappedBy = "parent", orphanRemoval = true)   // 참조하는 곳이 하나일 때 사용
    List<Child> childList = new ArrayList<>();

    public void addChild(Child child) {
        childList.add(child);
        child.setParent(this);
    }

}
