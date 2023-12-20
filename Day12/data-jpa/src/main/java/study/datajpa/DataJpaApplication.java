package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

}

// 제네릭 타입
//		T : 엔티티
//		ID : 엔티티의 식별자 타입
//		S : 엔티티와 그 자식 타입

// 주요 메서드
//		save(S) : 새로운 엔티티는 저장하고 이미 있는 엔티티는 병합한다.
//		delete(T) : 엔티티 하나를 삭제한다. 내부에서 EntityManager.remove() 호출
//		findById(ID) : 엔티티 하나를 조회한다. 내부에서 EntityManager.find() 호출
//		getOne(ID) : 엔티티를 프록시로 조회한다. 내부에서 EntityManager.getReference() 호출
//		findAll(…) : 모든 엔티티를 조회한다. 정렬( Sort )이나 페이징( Pageable ) 조건을 파라미터로 제공할 수 있다.

// 참고: JpaRepository 는 대부분의 공통 메서드를 제공한다.