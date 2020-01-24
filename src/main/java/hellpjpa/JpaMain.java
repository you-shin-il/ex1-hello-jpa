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
            Team team = new Team();
            team.setName("TeamA");
            entityManager.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            Member findMember = entityManager.find(Member.class, member.getId());

            List<Member> members = findMember.getTeam().getMembers();

            members.stream().forEach(x -> {
                System.out.println("x = " + x.getUsername());
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