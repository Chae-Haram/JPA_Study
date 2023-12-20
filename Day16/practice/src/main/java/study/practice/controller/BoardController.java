package study.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import study.practice.dto.BoardDto;
import study.practice.entity.Board;
import study.practice.entity.Member;
import study.practice.service.BoardService;
import study.practice.service.MemberService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private final BoardService boardService;

    @Autowired
    private final MemberService memberService;

    @GetMapping("/list")
    public ModelAndView findAll() {
        ModelAndView mav = new ModelAndView();
        List<BoardDto> boardDtos = boardService.findAll();
        mav.addObject("list", boardDtos);
        return mav;
    }

    @GetMapping("/write")
    public String write(HttpSession session) {
        Member member = (Member) session.getAttribute("login");
        if (member == null) {
            return "redirect:/member/login";
        } else {
            return "/board/write";
        }
    }

    @PostMapping("/write")
    public String writePro(BoardDto dto) {

        Member member = memberService.findById(dto.getMemberId());
        dto.setWriter(member);

        // 추가된 게시글
        Board saveBoard = boardService.save(dto);

        // 게시글 추가에 실패했을 때
        if (saveBoard == null) {
            return "redirect:/board/write";
        }

        return "redirect:/board/list";
    }

    @GetMapping("/view/{id}")
    public ModelAndView view(@PathVariable("id") Long id) {

        ModelAndView mav = new ModelAndView("/board/view");

        Board board = boardService.findById(id);
        mav.addObject("board", board);

        return mav;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {

        boardService.deleteById(id);

        return "redirect:/board/list";
    }

    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable("id") Long id) {

        ModelAndView mav = new ModelAndView("/board/update");

        Board board = boardService.findById(id);

        mav.addObject("board", board);

        return mav;
    }

    @PostMapping("/update/{id}")
    public String updatePro(@PathVariable("id") Long id, Board board) {

        Board updateBoard = boardService.update(board);

        return "redirect:/board/view/" + id;
    }
}
