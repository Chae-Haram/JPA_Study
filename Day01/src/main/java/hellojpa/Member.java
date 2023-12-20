package hellojpa;

import lombok.*;

import javax.persistence.*;

// @Entity : JPA를 사용하는 클래스라는 것을 명시
// -> @Entity가 붙어 있어야 JPA가 관리한다

// 관례적으로 현재 DTO와 같은 이름의 테이블에 작업을 시행한다
// 만약 DTO와 테이블의 이름이 다를 경우 @Table을 사용하여 테이블 이름을 매핑해준다
@Entity
// @Table(name = "MBR")
// @Table(uniqueConstraints = )
@SequenceGenerator(name = "member_seq_generator",
        sequenceName = "member_seq",
        initialValue = 1, allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Member {

   /* // @Id : JPA에게 어떤 필드가 primary key인지 알려 주는 어노테이션
    @Id
    private Long id;

    // 테이블 뿐 아니라 컬럼의 이름이 다를 경우에도 @Column을 사용하여 컬럼 이름을 매핑해준다
//    @Column(name = "username")

    @Column(unique = true, length = 10)
    private String name;

    private int age;

    public Member() {}

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }*/

//    @Id
//    private Long id;
//    @Column(name = "name")
//    // updatable = false -> 수정 불가
//    // nullable = false -> not null
//    private String username;
//    @Column
//    private Integer age;
//    @Enumerated(EnumType.STRING)
//    private RoleType roleType;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//
//    private LocalDate testLocalDate;
//    private LocalDateTime testLocalDateTime;
//    @Lob
//    private String description;
//
//    @Transient
//    private int temp;
//
//    public Member() {}
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUserName(String username) {
//        this.username = username;
//    }
//
//    public RoleType getRoleType() {
//        return roleType;
//    }
//
//    public void setRoleType(RoleType roleType) {
//        this.roleType = roleType;
//    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "member_seq_generator")
    private Long id;
    @Column(name = "name", nullable = false)
    private String username;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
}
