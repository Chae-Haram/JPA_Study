package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
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
}
