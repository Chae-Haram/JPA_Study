package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

	@Bean
	Hibernate5Module hibernate5Module() {
		Hibernate5Module hibernate5Module = new Hibernate5Module();
		//강제 지연 로딩 설정
//		hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
		return hibernate5Module;
	}
}

// 엔티티 설계시 주의점

// 엔티티에는 가급적 Setter를 사용하지 말자!
// Setter가 모두 열려있을 경우, 변경 포인트가 너무 많아서 유지보수가 어렵다

// 모든 연관관계는 지연로딩으로 설정!
// 즉시로딩(EAGER)은 예측이 어렵고, 어떤 SQL이 실행될지 추적하기 어렵다
// 특히 JPQL을 실행할 때 N+1 문제가 자주 발생한다
// 실무에서 모든 연관관계는 지연로딩( LAZY )으로 설정해야 한다
// @XToOne(OneToOne, ManyToOne) 관계는 기본이 즉시로딩이므로 직접 지연로딩으로 설정해야 한다

// 컬렉션은 필드에서 바로 초기화하자!
// null 문제에서 안전하다
// 하이버네이트는 엔티티를 영속화 할 때, 컬랙션을 감싸서 하이버네이트가 제공하는 내장 컬렉션으로 변경한다
// 만약 getOrders() 처럼 임의의 메서드에서 컬력션을 잘못 생성하면 하이버네이트 내부 메커니즘에 문제가 발생할 수 있다
// 따라서 필드레벨에서 생성하는 것이 가장 안전하고, 코드도 간결하다

// 테이블, 컬럼명 생성 전략
// 스프링 부트에서 하이버네이트 기본 매핑 전략을 변경해서 실제 테이블 필드명은 다름
// 하이버네이트 기존 구현: 엔티티의 필드명을 그대로 테이블의 컬럼명으로 사용 (SpringPhysicalNamingStrategy)
// 스프링 부트 신규 설정 (엔티티(필드) 테이블(컬럼))
// 1) 카멜 케이스 -> 언더스코어(memberPoint member_point)
// 2) .(점) -> _(언더스코어)
// 3) 대문자 -> 소문자

// ==============================================================================================================

// 변경 감지와 병합(merge)
// 참고: 정말 중요한 내용이니 꼭! 완벽하게 이해하셔야 합니다.

// 준영속 엔티티?
// 영속성 컨텍스트가 더는 관리하지 않는 엔티티를 말한다.
// (여기서는 itemService.saveItem(book) 에서 수정을 시도하는 Book 객체다. Book 객체는 이미 DB에 한번 저장되어서 식별자가 존재한다.
// 이렇게 임의로 만들어낸 엔티티도 기존 식별자를 가지고 있으면 준영속 엔티티로 볼 수 있다.)

// 준영속 엔티티를 수정하는 2가지 방법
// 변경 감지 기능 사용
// 병합( merge ) 사용

// 변경 감지 기능 사용
// 영속성 컨텍스트에서 엔티티를 다시 조회한 후에 데이터를 수정하는 방법
// 트랜잭션 안에서 엔티티를 다시 조회, 변경할 값 선택 트랜잭션 커밋 시점에 변경 감지(Dirty Checking)이 동작해서 데이터베이스에 UPDATE SQL 실행

// 병합( merge ) 사용
// 병합은 준영속 상태의 엔티티를 영속 상태로 변경할 때 사용하는 기능
// 병합 동작 방식
// 1. merge() 를 실행한다.
// 2. 파라미터로 넘어온 준영속 엔티티의 식별자 값으로 1차 캐시에서 엔티티를 조회한다.
// 2-1. 만약 1차 캐시에 엔티티가 없으면 데이터베이스에서 엔티티를 조회하고, 1차 캐시에 저장한다.
// 3. 조회한 영속 엔티티( mergeMember )에 member 엔티티의 값을 채워 넣는다.
//    (member 엔티티의 모든 값을 mergeMember에 밀어 넣는다. 이때 mergeMember의 “회원1”이라는 이름이 “회원명변경”으로 바뀐다.)
// 4. 영속 상태인 mergeMember를 반환한다.

// 병합시 동작 방식을 간단히 정리
// 1. 준영속 엔티티의 식별자 값으로 영속 엔티티를 조회한다.
// 2. 영속 엔티티의 값을 준영속 엔티티의 값으로 모두 교체한다.(병합한다.)
// 3. 트랜잭션 커밋 시점에 변경 감지 기능이 동작해서 데이터베이스에 UPDATE SQL이 실행

// 주의: 변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만, 병합을 사용하면 모든 속성이 변경된다.
//		 병합시 값이 없으면 null 로 업데이트 할 위험도 있다. (병합은 모든 필드를 교체한다.)