package Ex;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
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

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

//            String query = "select m.team From Member m";

//            List<Team> result = em.createQuery(query, Team.class).getResultList();
//
//            for (Team s : result) {
//                System.out.println("s = " + s);
//            }

//            String query = "select t.members From Team t";
//
//            Collection result = em.createQuery(query, Collection.class).getResultList();
//
//            for (Object o : result) {
//                System.out.println("o = " + o);
//            }

//            String query = "select t.members.size From Team t";
//
//            Integer result = em.createQuery(query, Integer.class).getSingleResult();
//
//            System.out.println("result = " + result);

            String query = "select m From Team t join t.members m";

            List<Collection> result = em.createQuery(query, Collection.class).getResultList();

            System.out.println("result = " + result);

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

// 문자는 '' 사이에 넣어준다 ''사이에 '쓸 일이 있으면 "로 표기한다

// CONCAT, SUBSTRING, TRIM 등의 표준 함수

// 표준 함수로 안될 때 사용자 정의 함수. 미리 추가해놔야 함