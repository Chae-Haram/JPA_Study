package study.practice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.practice.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {



}
