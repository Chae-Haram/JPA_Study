package study.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.practice.dto.MemberDto;
import study.practice.entity.Member;
import study.practice.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void join(MemberDto dto) {
        Member member = new Member();
        member.setAddress(dto.getAddress());
        member.setAge(dto.getAge());
        member.setEmail(dto.getEmail());
        member.setPNum(dto.getPNum());
        member.setUsername(dto.getUsername());
        member.setUserId(dto.getUserId());
        member.setUserPw(dto.getUserPw());

        // 회원 가입 (저장)
        Member savedMember = memberRepository.save(member);
        System.err.println(member);
    }

    public Member login(MemberDto dto) {
        Optional<Member> member = memberRepository.findByUserIdAndUserPw(dto.getUserId(), dto.getUserPw());
        return member.get();
    }

    public Member findById(Long memberId) {
        Member member = memberRepository.findById(memberId).get();

        return member;
    }
}
