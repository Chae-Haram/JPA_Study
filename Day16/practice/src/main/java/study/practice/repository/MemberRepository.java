package study.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.practice.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> { // Member의 기본키가 Long

    Optional<Member> findUsernameById(Long id);

    Optional<Member> findByUserIdAndUserPw(String userId, String UserPw);
}
