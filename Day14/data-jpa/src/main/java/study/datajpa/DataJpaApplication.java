package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@EnableJpaAuditing
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	// 주의: DataJpaApplication 에 @EnableJpaAuditing 도 함께 등록해야 합니다
	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of(UUID.randomUUID().toString());
	}
}

// 사용자 정의 리포지토리 구현
//		스프링 데이터 JPA 리포지토리는 인터페이스만 정의하고 구현체는 스프링이 자동 생성
//		스프링 데이터 JPA가 제공하는 인터페이스를 직접 구현하면 구현해야 하는 기능이 너무 많음
//		다양한 이유로 인터페이스의 메서드를 직접 구현하고 싶다면?
//			JPA 직접 사용( EntityManager )
//			스프링 JDBC Template 사용
//			MyBatis 사용
//			데이터베이스 커넥션 직접 사용 등등...
//			Querydsl 사용

// ============================================================================================================

// Auditing
//		엔티티를 생성, 변경할 때 변경한 사람과 시간을 추적하고 싶으면?
//			등록일
//			수정일
//			등록자
//			수정자

// ============================================================================================================

// @EntityListeners(AuditingEntityListener.class) 를 생략하고 스프링 데이터 JPA 가 제공하는 이벤트를
// 엔티티 전체에 적용하려면 orm.xml에 다음과 같이 등록하면 된다

// META-INF/orm.xml
//```xml
//	<?xml version="1.0" encoding="UTF-8"?>
//	<entity-mappings xmlns="http://xmlns.jcp.org/xml/ns/persistence/orm"
// 					 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
// 					 xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence/orm
//										 http://xmlns.jcp.org/xml/ns/persistence/orm_2_2.xsd"
// 					 version="2.2">
// <persistence-unit-metadata>
// <persistence-unit-defaults>
// <entity-listeners>
// <entity-listener
//class="org.springframework.data.jpa.domain.support.AuditingEntityListener"/>
// </entity-listeners>
// </persistence-unit-defaults>
// </persistence-unit-metadata>
//
//</entity-mappings>

// ============================================================================================================

// Page를 1부터 시작하기
//		스프링 데이터는 Page를 0부터 시작한다
//		만약 1부터 시작하려면?
//			1) Pageable, Page를 파리미터와 응답 값으로 사용히지 않고, 직접 클래스를 만들어서 처리한다
// 			그리고 직접 PageRequest(Pageable 구현체)를 생성해서 리포지토리에 넘긴다
//			물론 응답값도 Page 대신에 직접 만들어서 제공해야 한다
//			2) spring.data.web.pageable.one-indexed-parameters 를 true 로 설정한다
//			그런데 이 방법은 web에서 page 파라미터를 -1 처리 할 뿐이다.
// 			따라서 응답값인 Page 에 모두 0 페이지 인덱스를 사용하는 한계가 있다

// ============================================================================================================

// 스프링 데이터 JPA 구현체 분석
//		스프링 데이터 JPA가 제공하는 공통 인터페이스의 구현체
//		org.springframework.data.jpa.repository.support.SimpleJpaRepository
// 		@Repository 적용: JPA 예외를 스프링이 추상화한 예외로 변환
//		@Transactional 트랜잭션 적용
//			JPA의 모든 변경은 트랜잭션 안에서 동작
//			스프링 데이터 JPA는 변경(등록, 수정, 삭제) 메서드를 트랜잭션 처리
//			서비스 계층에서 트랜잭션을 시작하지 않으면 리파지토리에서 트랜잭션 시작
//			서비스 계층에서 트랜잭션을 시작하면 리파지토리는 해당 트랜잭션을 전파 받아서 사용
//			그래서 스프링 데이터 JPA를 사용할 때 트랜잭션이 없어도 데이터 등록, 변경이 가능했음
//			(사실은 트랜잭션이 리포지토리 계층에 걸려있는 것임)
//		@Transactional(readOnly = true)
//			데이터를 단순히 조회만 하고 변경하지 않는 트랜잭션에서 readOnly = true 옵션을 사용하면 플러시를 생략해서 약간의 성능 향상을 얻을 수 있음
//			자세한 내용은 JPA 책 15.4.2 읽기 전용 쿼리의 성능 최적화 참고

// 매우 중요!!!
//		save() 메서드
//			새로운 엔티티면 저장 (persist)
//			새로운 엔티티가 아니면 병합 (merge)

// 새로운 엔티티를 구별하는 방법
//	매우 중요!!!
//		save() 메서드
//			새로운 엔티티면 저장( persist )
//			새로운 엔티티가 아니면 병합( merge )
//		새로운 엔티티를 판단하는 기본 전략
//			식별자가 객체일 때 null 로 판단
//			식별자가 자바 기본 타입일 때 0 으로 판단
//			Persistable 인터페이스를 구현해서 판단 로직 변경 가능

// ============================================================================================================

// 네이티브 쿼리
//		가급적 네이티브 쿼리는 사용하지 않는게 좋음, 정말 어쩔 수 없을 때 사용
//		최근에 나온 궁극의 방법 스프링 데이터 Projections 활용

//	스프링 데이터 JPA 기반 네이티브 쿼리
//		페이징 지원
//		반환 타입
//			Object[]
//			Tuple
//			DTO(스프링 데이터 인터페이스 Projections 지원)
//		제약
//			Sort 파라미터를 통한 정렬이 정상 동작하지 않을 수 있음(믿지 말고 직접 처리)
//			JPQL처럼 애플리케이션 로딩 시점에 문법 확인 불가
//			동적 쿼리 불가