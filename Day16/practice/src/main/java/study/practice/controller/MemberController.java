package study.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import study.practice.dto.MemberDto;
import study.practice.entity.Member;
import study.practice.service.MemberService;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @GetMapping("/login")
    public void login() {}

    @PostMapping("/login")
    public ModelAndView loginPro(MemberDto dto, HttpSession session) {
        ModelAndView mav = new ModelAndView("redirect:/home");
        Member login = memberService.login(dto);

        if (login == null) {
            mav.addObject("msg", "로그인 실패");
            mav.setViewName("/member/login");
        } else {
            session.setAttribute("login", login);
            mav.addObject("msg", "로그인 성공");
        }
        mav.addObject("");
        return mav;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }

    @GetMapping("/join")
    public void join() {}

    @PostMapping("/join")
    public String joinPro(MemberDto dto) {
        memberService.join(dto);
        return "redirect:/member/login";
    }

}
