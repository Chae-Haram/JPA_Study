package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    // Web 확장 - 도메인 클래스 컨버터
    //      HTTP 파라미터로 넘어온 엔티티의 아이디로 엔티티 객체를 찾아서 바인딩

    private final MemberRepository memberRepository;

    // 도메인 클래스 컨버터 사용 전
    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    // 도메인 클래스 컨버터 사용 후
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    // HTTP 요청은 회원 id 를 받지만 도메인 클래스 컨버터가 중간에 동작해서 회원 엔티티 객체를 반환
    // 도메인 클래스 컨버터도 리파지토리를 사용해서 엔티티를 찾음

    // 주의: 도메인 클래스 컨버터로 엔티티를 파라미터로 받으면, 이 엔티티는 단순 조회용으로만 사용해야 한다
    // (트랜잭션이 없는 범위에서 엔티티를 조회했으므로, 엔티티를 변경해도 DB에 반영되지 않는다.)


    // Web 확장 - 페이징과 정렬
    //      스프링 데이터가 제공하는 페이징과 정렬 기능을 스프링 MVC에서 편리하게 사용할 수 있다.

    @GetMapping("/members")
    public Page<Member> list(@PageableDefault(size = 10, sort = "username") Pageable pageable) {
//        Page<Member> page = memberRepository.findAll(pageable);
//        return page;

        return memberRepository.findAll(pageable);
    }

    @GetMapping("/memberDtos")
    public Page<MemberDto> listDto(Pageable pageable) {
        return memberRepository.findAll(pageable).map(MemberDto::new);
    }

    @PostConstruct
    public void init() {
//        memberRepository.save(new Member("userA"));
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }
    }
}
