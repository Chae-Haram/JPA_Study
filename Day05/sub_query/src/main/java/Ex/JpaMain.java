package Ex;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select (select avg(m1.age) from Member m1) from Member m";
            "select mm.age, mm.username " + "from (select m.age, m.username from Member m)"
            // from 절의 서브 쿼리는 불가능
            // 조인으로 해결하는게 1번
            // 쿼리 두번 날리는게 2번
            // native 사용하는게 3번 (비추)
            List<Member> result = em.createQuery(query, Member.class).getResultList();

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