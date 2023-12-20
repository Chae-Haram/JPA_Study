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
// 2. 영속 엔티티의 값을 준영속 엔티티의 값으로 모두 교체한다.(병합한다)
// 3. 트랜잭션 커밋 시점에 변경 감지 기능이 동작해서 데이터베이스에 UPDATE SQL이 실행

// 주의: 변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만, 병합을 사용하면 모든 속성이 변경된다.
//		 병합시 값이 없으면 null 로 업데이트 할 위험도 있다. (병합은 모든 필드를 교체한다.)

// ==============================================================================================================

// 엔티티를 DTO로 변환하거나, DTO로 바로 조회하는 두가지 방법은 각각 장단점이 있다. 둘중 상황에 따라서 더 나은 방법을 선택하면 된다
// 엔티티로 조회하면 리포지토리 재사용성도 좋고, 개발도 단순해진다. 따라서 권장하는 방법은 다음과 같다

// 쿼리 방식 선택 권장 순서
//		1. 우선 엔티티를 DTO로 변환하는 방법을 선택한다
//		2. 필요하면 페치 조인으로 성능을 최적화 한다. 대부분의 성능 이슈가 해결된다
//		3. 그래도 안되면 DTO로 직접 조회하는 방법을 사용한다
//		4. 최후의 방법은 JPA가 제공하는 네이티브 SQL이나 스프링 JDBC Template을 사용해서 SQL을 직접 사용한다

// ==============================================================================================================

// 주문 조회 V3.1: 엔티티를 DTO로 변환 - 페이징과 한계 돌파

// 페이징과 한계 돌파
//		컬렉션을 페치 조인하면 페이징이 불가능하다.
//			컬렉션을 페치 조인하면 일대다 조인이 발생하므로 데이터가 예측할 수 없이 증가한다.
//			일대다에서 일(1)을 기준으로 페이징을 하는 것이 목적이다. 그런데 데이터는 다(N)를 기준으로 row가 생성된다.
//			Order를 기준으로 페이징 하고 싶은데, 다(N)인 OrderItem을 조인하면 OrderItem이 기준이 되어버린다.
//			(더 자세한 내용은 자바 ORM 표준 JPA 프로그래밍 - 페치 조인 한계 참조)
//		이 경우 하이버네이트는 경고 로그를 남기고 모든 DB 데이터를 읽어서 메모리에서 페이징을 시도한다. 최악의 경우 장애로 이어질 수 있다.

// 한계 돌파
//	그러면 페이징 + 컬렉션 엔티티를 함께 조회하려면 어떻게 해야할까?
//	지금부터 코드도 단순하고, 성능 최적화도 보장하는 매우 강력한 방법을 소개하겠다.
//	대부분의 페이징 + 컬렉션 엔티티 조회 문제는 이 방법으로 해결할 수 있다.

//		먼저 ToOne(OneToOne, ManyToOne) 관계를 모두 페치조인 한다. ToOne 관계는 row수를 증가시키지 않으므로 페이징 쿼리에 영향을 주지 않는다.
//		컬렉션은 지연 로딩으로 조회한다.
//		지연 로딩 성능 최적화를 위해 hibernate.default_batch_fetch_size , @BatchSize 를 적용한다.
//			hibernate.default_batch_fetch_size: 글로벌 설정
//			@BatchSize: 개별 최적화
//			이 옵션을 사용하면 컬렉션이나, 프록시 객체를 한꺼번에 설정한 size 만큼 IN 쿼리로 조회한다.

// ==============================================================================================================

// API 개발 고급 정리
// 정리
//		엔티티 조회
//			엔티티를 조회해서 그대로 반환: V1
//			엔티티 조회 후 DTO로 변환: V2
//			페치 조인으로 쿼리 수 최적화: V3
//			컬렉션 페이징과 한계 돌파: V3.1
//				컬렉션은 페치 조인시 페이징이 불가능
//				ToOne 관계는 페치 조인으로 쿼리 수 최적화
//				컬렉션은 페치 조인 대신에 지연 로딩을 유지하고, hibernate.default_batch_fetch_size, @BatchSize로 최적화
//
//		DTO 직접 조회
//			JPA에서 DTO를 직접 조회: V4
//			컬렉션 조회 최적화 - 일대다 관계인 컬렉션은 IN 절을 활용해서 메모리에 미리 조회해서 최적화: V5
//			플랫 데이터 최적화 - JOIN 결과를 그대로 조회 후 애플리케이션에서 원하는 모양으로 직접 변환: V6

// 권장 순서
//		1. 엔티티 조회 방식으로 우선 접근
//			1) 페치조인으로 쿼리 수를 최적화
//			2) 컬렉션 최적화
//				1 - 페이징 필요 hibernate.default_batch_fetch_size, @BatchSize로 최적화
//				2 - 페이징 필요X 페치 조인 사용
//		2. 엔티티 조회 방식으로 해결이 안되면 DTO 조회 방식 사용
//		3. DTO 조회 방식으로 해결이 안되면 NativeSQL or 스프링 JdbcTemplate

// 참고: 엔티티 조회 방식은 페치 조인이나, hibernate.default_batch_fetch_size, @BatchSize 같이 코드를 거의 수정하지 않고,
//  	옵션만 약간 변경해서, 다양한 성능 최적화를 시도할 수 있다. 반면에 DTO를 직접 조회하는 방식은 성능을 최적화 하거나
//		성능 최적화 방식을 변경할 때 많은 코드를 변경해야 한다

// 참고: 개발자는 성능 최적화와 코드 복잡도 사이에서 줄타기를 해야 한다. 항상 그런 것은 아니지만, 보통 성능 최적화는 단순한 코드를 복잡한 코드로 몰고간다
//		엔티티 조회 방식은 JPA가 많은 부분을 최적화 해주기 때문에, 단순한 코드를 유지하면서, 성능을 최적화 할 수 있다
//		반면에 DTO 조회 방식은 SQL을 직접 다루는 것과 유사하기 때문에, 둘 사이에 줄타기를 해야 한다

// DTO 조회 방식의 선택지
//		- DTO로 조회하는 방법도 각각 장단이 있다. V4, V5, V6에서 단순하게 쿼리가 1번 실행된다고 V6이 항상 좋은 방법인 것은 아니다
//		- V4는 코드가 단순하다. 특정 주문 한건만 조회하면 이 방식을 사용해도 성능이 잘 나온다. 예를 들어서 조회한 Order 데이터가 1건이면
// 		OrderItem을 찾기 위한 쿼리도 1번만 실행하면 된다
//		- V5는 코드가 복잡하다. 여러 주문을 한꺼번에 조회하는 경우에는 V4 대신에 이것을 최적화한 V5 방식을 사용해야 한다
// 		예를 들어서 조회한 Order 데이터가 1000건인데, V4 방식을 그대로 사용하면, 쿼리가 총 1 + 1000번 실행된다
// 		여기서 1은 Order 를 조회한 쿼리고, 1000은 조회된 Order의 row 수다. V5 방식으로 최적화 하면 쿼리가 총 1 + 1번만 실행된다
// 		상황에 따라 다르겠지만 운영 환경에서 100배 이상의 성능 차이가 날 수 있다
//		- V6는 완전히 다른 접근방식이다. 쿼리 한번으로 최적화 되어서 상당히 좋아보이지만, Order를 기준으로 페이징이 불가능하다
// 		실무에서는 이정도 데이터면 수백이나, 수천건 단위로 페이징 처리가 꼭 필요하므로, 이 경우 선택하기 어려운 방법이다
// 		그리고 데이터가 많으면 중복 전송이 증가해서 V5와 비교해서 성능 차이도 미비하다