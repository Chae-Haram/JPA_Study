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

            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

//            String query = "select m From Member m";
//
//            List<Member> result = em.createQuery(query, Member.class).getResultList();
//
//            for (Member m : result) {
//                System.out.println("member = " + m.getUsername() + ", " + m.getTeam().getName());
                // member1, teamA (SQL)
                // member2, teamA (1차 캐시)
                // member3, teamB (SQL)
                // 쿼리 셋

                // 회원 100명 -> 1 + N 쿼리수 (최악의 경우)
                // 1은 회원을 불러오기 위함. N은 회원수
//            }

//            String query = "select m From Member m join fetch m.team";
            // fetch join으로 회원과 팀을 함께 조회해서 지연 로딩 X
            // fetch join 실무에서 많이 사용

//            List<Member> result = em.createQuery(query, Member.class).getResultList();

//            for (Member m : result) {
//                System.out.println("member = " + m.getUsername() + ", " + m.getTeam().getName());
                // 쿼리 하나
//            }

//            String query = "select t From Team t join fetch t.members";

//            List<Team> result = em.createQuery(query, Team.class).getResultList();

//            for (Team t : result) {
//                System.out.println("team = " + t.getName() + " | members = " + t.getMembers().size());
                // 중복된 결과로 인한 결과값 오류 (뻥튀기됨)
//            }

//            String query = "select distinct t From Team t join fetch t.members";
            // distinct -> 중복값 제거
//            String query = "select distinct t From Team t join t.members";
            // 일반 조인 실행시 연관된 엔티티를 함께 조회하지 않음. 단지 select 절에 지정한 엔티티만 조회함.
            // fetch join 은 연관된 엔티티를 함께 조회함. 객체 그래프를 sql 한번에 조회하는 개념
            // fetch join 은 별칭 사용X
            
//            List<Team> result = em.createQuery(query, Team.class).getResultList();

//            for (Team t : result) {
//                System.out.println("team = " + t.getName() + " | members = " + t.getMembers().size());
//            }

            String query = "select t From Team t";

            List<Team> result = em.createQuery(query, Team.class).
                    setFirstResult(0).
                    setMaxResults(2).
                    getResultList();
            // 둘 이상의 컬렉션은 fetch join 할 수 없음. @BatchSize(size = 1000 이하의 정수)
            
            System.out.println("result = " + result.size());

            for (Team t : result) {
                System.out.println("team = " + t.getName() + " | members = " + t.getMembers().size());
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