package Ex;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

//            동적 쿼리 어려움
//            List<Member> result = em.createQuery(
//                    "select m from Member m where m.name like '%kim%'",
//                    Member.class
//            ).getResultList();
//
//            for (Member member : result) {
//                System.out.println("member = " + member);
//            }

            // Criteria 너무 복잡하고 실용성이 없음
            // Criteria 사용 준비
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            // 루트 클래스 (조회를 시작할 클래스)
//            Root<Member> m = query.from(Member.class);

            // 쿼리 생성
//            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("name"), "kim"));
//            CriteriaQuery<Member> cq = query.select(m);

//            String name = "Jude";
//            if (name != null) {
//                cq = cq.where(cb.equal(m.get("name"), "kim"));
//            }

//            List<Member> resultList = em.createQuery(cq).getResultList();

            Member member = new Member();
            member.setName("member1");
            em.persist(member);

            // flush -> commit or query

            em.flush();

            List<Member> resultList = em.createNativeQuery("select MEMBER_ID, city, street, zipcode, name from MEMBER",
                    Member.class).getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
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