package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // persistenceUnitName은 persistence.xml에서 <persistence-unit>에 넣었던 name
        // 얘를 만든 순간 db 연결이 완료된다

        // entityManger 생성
        // 엔티티 매니저는 쓰레드간 공유 X (사용하고 버려야 한다)
        EntityManager em = emf.createEntityManager();
        // 이 사이에 코드 작성

        // EntityManger에서 트랜잭션을 받아오는 구문
        // DB에서 작업하기 위해서는 트랜잭션이 반드시 필요하다
        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 트랜잭션을 시작한다

        try {
            // 저장
//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);

//            Member member = new Member();
//            member.setUsername("member1");
//            member.setTeamId(team.getId());
//            member.setTeam(team);
//            em.persist(member);

//            em.flush(); //
//            em.clear(); //

            // 조회
//            Member findMember = em.find(Member.class, member.getId());
//            Long findTeamId = findMember.getTeamId();
//            Team findTeam = em.find(Team.class, team.getId());
//            Team findTeam = findMember.getTeam();

//            System.out.println("findTeam = " + findTeam.getName());

            // 수정
//            Team newTeam = em.find(Team.class, 100L);
//            findMember.setTeam(newTeam);

//            List<Member> members = findMember.getTeam().getMembers();

//            for (Member m : members) {
//                System.out.println("m.getUsername() = " + m.getUsername());
//            }

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);   // **
//            member.changeTeam(team);
            em.persist(member);

//            team.getMembers().add(member);  // **
            team.addMember(member);

            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();

            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

            tx.commit();    // 현재 트랜잭션을 commit
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