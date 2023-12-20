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

//            String query = "select m from Member m join m.team t";
//            String query = "select m from Member m inner join m.team t";
//            String query = "select m from Member m left join m.team t";
//            String query = "select m from Member m left outer join m.team t";
//            String query = "select m from Member m, Team t where m.username = t.name";
//            List<Member> result = em.createQuery(query, Member.class).getResultList();

//            String query = "select m from Member m left join m.team t on t.name = 'teamA'";
//            List<Member> result = em.createQuery(query, Member.class).getResultList();

            String query = "select m from Member m left join Team t on m.username = t.name";
            List<Member> result = em.createQuery(query, Member.class).getResultList();

            System.out.println("result = " + result.size());

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