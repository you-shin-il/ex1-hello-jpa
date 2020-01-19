package hellpjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        try{
            //Member findMember = entityManager.find(Member.class, 1L);
            List<Member> result = entityManager.createQuery("select m from Member as m", Member.class)
                .getResultList();

            result.forEach(x -> {
                System.out.println("id : " + x.getId());
                System.out.println("name : " + x.getName());
            });
            tx.commit();
        }catch(Exception e) {
            tx.rollback();
        }finally{
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}