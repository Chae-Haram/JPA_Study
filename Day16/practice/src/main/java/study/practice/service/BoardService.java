package study.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.practice.dto.BoardDto;
import study.practice.entity.Board;
import study.practice.repository.BoardRepository;
import study.practice.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    @Autowired
    private final BoardRepository boardRepository;

    @Autowired
    private final MemberRepository memberRepository;

    public List<BoardDto> findAll() {
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtos = new ArrayList<>();   // 반환할 빈 리스트 생성
        for (Board board : boards) {
            BoardDto dto = new BoardDto();
            dto.setBoardId(board.getId());
            dto.setTitle(board.getTitle());
            dto.setMemberId(board.getMember().getId());
            dto.setWriter(board.getMember());
            dto.setContent(board.getContent());
            dto.setCreatedDate(board.getCreatedDate());

            boardDtos.add(dto);
        }

        return boardDtos;
    }

    @Transactional
    public Board save(BoardDto dto) {
        Board board = new Board();
        board.setTitle(dto.getTitle());
        board.setMember(dto.getWriter());
        board.setContent(dto.getContent());

        Board saveBoard = boardRepository.save(board);

        return saveBoard;
    }

    public Board findById(Long id) {

        Board board = boardRepository.findById(id).get();

        return board;
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public Board update(Board board) {
        Board updateBoard = findById(board.getId());
        updateBoard.setTitle(board.getTitle());
        updateBoard.setContent(board.getContent());

        return updateBoard;
    }
}
