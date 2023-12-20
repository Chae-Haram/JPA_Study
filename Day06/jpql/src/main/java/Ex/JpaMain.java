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

            Member member1 = new Member();
            member1.setUsername("member1");
//            member1.setAge(10);
//            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
//            member2.setAge(10);
//            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

//            String query = "select m.username, 'HELLO', true From Member m " +
//                            "where m.type = :type";
//
//            List<Object[]> result = em.createQuery(query).
//                    setParameter("type", Type.ADMIN).
//                    getResultList();
//
//            for (Object[] objects : result) {
//                System.out.println("objects = " + objects[0]);
//                System.out.println("objects = " + objects[1]);
//                System.out.println("objects = " + objects[2]);
//            }

//            String query = "select " +
//                                "case when m.age <= 10 then '학생요금'" +
//                                "     when m.age >= 60 then '경로요금'" +
//                                "     else '일반요금' " +
//                                "end " +
//                            "from Member m";
//
//            List<String> result = em.createQuery(query, String.class).getResultList();
//
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

//            String query = "select coalesce(m.username, '이름 없는 회원') as username from Member m";
//
//            List<String> result = em.createQuery(query, String.class).getResultList();
//
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

//            String query = "select nullif(m.username, '관리자') as username from Member m";
//
//            List<String> result = em.createQuery(query, String.class).getResultList();
//
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

//            String query = "select locate('de', 'abcdefg') From Member m";
//
//            List<Integer> result = em.createQuery(query, Integer.class).getResultList();
//
//            for (Integer s : result) {
//                System.out.println("s = " + s);
//            }

//            String query = "select function('group_concat', m.username) From Member m";
            String query = "select group_concat(m.username) From Member m";

            List<Integer> result = em.createQuery(query, Integer.class).getResultList();

            for (Integer s : result) {
                System.out.println("s = " + s);
            }

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