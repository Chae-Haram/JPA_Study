package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query(name = "Member.findByUsername") // 없어도 됨
    List<Member> findByUsername(@Param("username") String username);

    // 스프링 데이터 JPA는 선언한 "도메인 클래스 + .(점) + 메서드 이름"으로 Named 쿼리를 찾아서 실행
    // 만약 실행할 Named 쿼리가 없으면 메서드 이름으로 쿼리 생성 전략을 사용한다.
    // 필요하면 전략을 변경할 수 있지만 권장하지 않는다.

    // 참고: 스프링 데이터 JPA를 사용하면 실무에서 Named Query를 직접 등록해서 사용하는 일은 드물다
    // 대신  @Query 를 사용해서 리파지토리 메소드에 쿼리를 직접 정의한다

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    // @org.springframework.data.jpa.repository.Query 어노테이션을 사용
    // 실행할 메서드에 정적 쿼리를 직접 작성하므로 이름 없는 Named 쿼리라 할 수 있음
    // JPA Named 쿼리처럼 애플리케이션 실행 시점에 문법 오류를 발견할 수 있음(매우 큰 장점!)

    // 참고: 실무에서는 메소드 이름으로 쿼리 생성 기능은 파라미터가 증가하면 메서드 이름이 매우 지저분해진다. 따라서 @Query 기능을 자주 사용하게 된다.

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username);       // 컬렉션
    Member findMemberByUsername(String username);           // 단 건
    Optional<Member> findOptionalByUsername(String name);   // 단 건 Optional

    // 조회 결과가 많거나 없으면?
    //      컬렉션
    //      결과 없음: 빈 컬렉션 반환
    // 단건 조회
    //      결과 없음: null 반환
    //      결과가 2건 이상: javax.persistence.NonUniqueResultException 예외 발생

    // 참고: 단건으로 지정한 메서드를 호출하면 스프링 데이터 JPA는 내부에서 JPQL의 Query.getSingleResult() 메서드를 호출한다
    // 이 메서드를 호출했을 때 조회 결과가 없으면 javax.persistence.NoResultException 예외가 발생하는데
    // 개발자 입장에서 다루기가 상당히 불편하다
    // 스프링 데이터 JPA는 단건을 조회할 때 이 예외가 발생하면 예외를 무시하고 대신에 null 을 반환한다

    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    // Page
    // Slice (count X) 추가로 limit + 1을 조회한다. 그래서 다음 페이지 여부 확인(최근 모바일 리스트 생각해보면 됨)
    // List (count X)
    // 카운트 쿼리 분리(이건 복잡한 sql에서 사용, 데이터는 left join, 카운트는 left join 안해도 됨)
    // 실무에서 매우 중요!!!
    // 참고: 전체 count 쿼리는 매우 무겁다

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    // 벌크성 수정, 삭제 쿼리는 @Modifying 어노테이션을 사용
    //      사용하지 않으면 다음 예외 발생
    //      org.hibernate.hql.internal.QueryExecutionRequestException: Not supported for DML operations
    // 벌크성 쿼리를 실행하고 나서 영속성 컨텍스트 초기화: @Modifying(clearAutomatically = true) (이 옵션의 기본값은 false)
    //      이 옵션 없이 회원을 findById 로 다시 조회하면 영속성 컨텍스트에 과거 값이 남아서 문제가 될 수 있다
    //      만약 다시 조회해야 하면 꼭 영속성 컨텍스트를 초기화 하자

    // 참고: 벌크 연산은 영속성 컨텍스트를 무시하고 실행하기 때문에, 영속성 컨텍스트에 있는 엔티티의 상태와 DB에 엔티티 상태가 달라질 수 있다
    // 권장하는 방안
    //      1) 영속성 컨텍스트에 엔티티가 없는 상태에서 벌크 연산을 먼저 실행한다
    //      2) 부득이하게 영속성 컨텍스트에 엔티티가 있으면 벌크 연산 직후 영속성 컨텍스트를 초기화 한다

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    // 공통 메서드 오버라이드
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    // JPQL + 엔티티 그래프
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    // 메서드 이름으로. 쿼리에서 특히 편리하다.
    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    // EntityGraph 정리
    //      사실상 페치 조인(FETCH JOIN)의 간편 버전
    //      LEFT OUTER JOIN 사용

    // NamedEntityGraph
    @EntityGraph("Member.all")
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph2();

    // JPA 쿼리 힌트(SQL 힌트가 아니라 JPA 구현체에게 제공하는 힌트)
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String name);
}
