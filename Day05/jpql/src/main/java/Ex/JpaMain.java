package Ex;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

//            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
//            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//            Query query3 = em.createQuery("select m.username, m.age from Member m");

//            TypedQuery<Member> query4 = em.createQuery("select m from Member m", Member.class);
//            List<Member> resultList = query4.getResultList();   // 결과가 하나 이상이면 리스트, 없으면 빈 리스트

//            TypedQuery<Member> query5 = em.createQuery("select m from Member m where m.age = 10", Member.class);
//            Member result = query5.getSingleResult();   // 결과가 정확히 하나, 하나가 아닐 경우 exception
            // Spring Data JPA ->
//            System.out.println("result = " + result);

//            TypedQuery<Member> query6 = em.createQuery("select m from Member m where m.username = :username", Member.class);
//            query6.setParameter("username", "member1");
//            Member singleResult = query6.getSingleResult();
//            System.out.println("singleResult = " + singleResult.getUsername());

//            Member resultMember = em.createQuery("select m from Member m where m.username = :username", Member.class).
//                    setParameter("username", "member1").
//                    getSingleResult();

            em.flush();
            em.clear();

//            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();

//            Member findMember = result.get(0);
//            findMember.setAge(20);

//            List<Team> result = em.createQuery("select m.team from Member m", Team.class).getResultList();
            // 조인을 명시적으로 하는게 좋다
//            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();

//            em.createQuery("select o.address from Order o", Address.class).getResultList();
            // 값 타입의 한계, 어디 소속인지 명확히 표시해줘야 함

//            em.createQuery("select distinct m.username, m.age from Member m").getResultList();
            // distinct 중복 제거

//            List resultList = em.createQuery("select m.username, m.age from Member m").getResultList();

//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;

//            System.out.println("username = " + result[0]);
//            System.out.println("age = " + result[1]);

//            List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m").getResultList();

//            Object[] result = resultList.get(0);

            List<MemberDTO> DTOselect = em.createQuery("select new Ex.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();
            MemberDTO memberDTO = DTOselect.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());

            tx.commit();    // 현재 tx commit
        } catch (Exception e) {
            tx.rollback();
        } finally {
            // entityManger 닫기
            em.close();
        }
        // entityMangerFactory 닫기
        emf.close();
    }
}