package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

}

// 쿼리 메소드 기능 3가지
// 		1) 메소드 이름으로 쿼리 생성
//		2) 메소드 이름으로 JPA NamedQuery 호출
//		3) @Query 어노테이션을 사용해서 리파지토리 인터페이스에 쿼리 직접 정의

// 스프링 데이터 JPA가 제공하는 쿼리 메소드 기능
//		조회: find…By ,read…By ,query…By get…By,
// 			예:) findHelloBy 처럼 ...에 식별하기 위한 내용(설명)이 들어가도 된다
// 		COUNT: count…By 반환타입 long
//		EXISTS: exists…By 반환타입 boolean
//		삭제: delete…By, remove…By 반환타입 long
//		DISTINCT: findDistinct, findMemberDistinctBy
//		LIMIT: findFirst3, findFirst, findTop, findTop3

// 참고: 이 기능은 엔티티의 필드명이 변경되면 인터페이스에 정의한 메서드 이름도 꼭 함께 변경해야 한다.
// 		그렇지 않으면 애플리케이션을 시작하는 시점에 오류가 발생한다.
// 		이렇게 애플리케이션 로딩 시점에 오류를 인지할 수 있는 것이 스프링 데이터 JPA의 매우 큰 장점이다.

// ===============================================================================================

// 페이징과 정렬 파라미터
//		org.springframework.data.domain.Sort : 정렬 기능
//		org.springframework.data.domain.Pageable : 페이징 기능 (내부에 Sort 포함)

// 특별한 반환 타입
//		org.springframework.data.domain.Page : 추가 count 쿼리 결과를 포함하는 페이징
//		org.springframework.data.domain.Slice : 추가 count 쿼리 없이 다음 페이지만 확인 가능(내부적으로 limit + 1조회)
//		List (자바 컬렉션): 추가 count 쿼리 없이 결과만 반환