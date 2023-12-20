package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

    // 스프링의 SpEL 문법도 지원
    // 단! 이렇게 SpEL문법을 사용하면, DB에서 엔티티 필드를 다 조회해온 다음에 계산한다! 따라서 JPQL SELECT 절 최적화가 안된다
    @Value("#{target.username + ' ' + target.age}")
    String getUsername();

    // 엔티티 대신에 DTO를 편리하게 조회할 때 사용
    // 전체 엔티티가 아니라 만약 회원 이름만 딱 조회하고 싶으면?

    // 조회할 엔티티의 필드를 getter 형식으로 지정하면 해당 필드만 선택해서 조회(Projection)

    // 주의
    //      프로젝션 대상이 root 엔티티면, JPQL SELECT 절 최적화 가능
    //      프로젝션 대상이 ROOT가 아니면
    //          LEFT OUTER JOIN 처리
    //          모든 필드를 SELECT해서 엔티티로 조회한 다음에 계산

    // 정리
    //      프로젝션 대상이 root 엔티티면 유용하다.
    //      프로젝션 대상이 root 엔티티를 넘어가면 JPQL SELECT 최적화가 안된다!
    //      실무의 복잡한 쿼리를 해결하기에는 한계가 있다.
    //      실무에서는 단순할 때만 사용하고, 조금만 복잡해지면 QueryDSL을 사용하자
}
