package jpabook.jpashop.Service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest // 없으면 Autowired 다 실패함
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepositoryOld memberRepository;
//    @Autowired EntityManager em;    // DB에 쿼리 나가는 거 보려면


    @Test
//    @Rollback(false)    // rollback을 안하고 눈으로 직접 확인하는 법
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long saveId = memberService.join(member);

        // then
//        em.flush();                 // flush를 해준다
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member2);    // 예외가 발생해야 한다
//        try {
//            memberService.join(member2);    // 예외가 발생해야 한다
//        } catch (IllegalStateException e) {
//            return;
//        }

        // then
        fail("예외가 발생해야 한다.");

    }

}