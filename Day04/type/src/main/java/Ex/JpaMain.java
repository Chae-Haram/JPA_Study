package Ex;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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

            Address address = new Address("city", "street", "100000");

            Member member1 = new Member();
            member1.setName("member1");
            member1.setHomeAddress(address);
            em.persist(member1);

//            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());
//
//            Member member2 = new Member();
//            member2.setName("member2");
//            member2.setHomeAddress(copyAddress);
//            em.persist(member2);
//
//            member1.getHomeAddress().setCity("newCity");

            Address newAddress = new Address("newCity", address.getStreet(), address.getZipcode());
            member1.setHomeAddress(newAddress);

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