package gr.aueb.cf;


import gr.aueb.cf.core.enums.GenderType;
import gr.aueb.cf.model.Region;
import gr.aueb.cf.model.Teacher;
import gr.aueb.cf.model.TeacherMoreInfo;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.List;

public class App {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("school7PU");
    private final static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

        em.getTransaction().begin();

//        Teacher teacher = Teacher.builder()
//                .firstname("Κώστας")
//                .lastname("Γιαννούτσος")
//                .build();
//
//        TeacherMoreInfo teacherMoreInfo = TeacherMoreInfo.builder()
//                .dateOfBirth(LocalDateTime.of(2000, 2, 1, 10, 12, 58))
//                .gender(GenderType.MALE)
//                .build();
//
//        teacher.setTeacherMoreInfo(teacherMoreInfo);
//        Region region = em.find(Region.class, 1);
//        teacher.addRegion(region);
//
//        em.persist(teacher);

        // merge
//        String sql = "SELECT t FROM Teacher t WHERE t.lastname = :lastname";
//        TypedQuery<Teacher> query = em.createQuery(sql, Teacher.class);
//        query.setParameter("lastname", "Ανδρούτσος");
//        Teacher teacher = query.getSingleResult();
//        teacher.setIsActive(false);
//        em.merge(teacher);

//        String sql = "SELECT t FROM Teacher t WHERE t.isActive = true AND t.region.title = :regionTitle";
//        TypedQuery<Teacher> query = em.createQuery(sql, Teacher.class);
//        query.setParameter("regionTitle", "Θεσσαλονίκη");
//        List<Teacher> teachers = query.getResultList();
//        teachers.forEach(System.out::println);

        // Count των teachers ανά περιοχή
//        String sql = "SELECT r.title, COUNT(t) FROM Region r LEFT JOIN t.teachers t GROUP BY r.title";
//        TypedQuery<Object[]> query = em.createQuery(sql, Object[].class);
//        List<Object[]> teachersPerRegion = query.getResultList();
//        for (Object[] row : teachersPerRegion) {
//            for (Object item : row) {
//                System.out.print(item + "");
//            }
//            System.out.println();
//        }

        // Find all active teachers in a region
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Teacher> query = cb.createQuery(Teacher.class);
        Root<Teacher> teacher = query.from(Teacher.class);
        Join<Teacher, Region> region = teacher.join("region");

        query.select(teacher).where(
                cb.and(
                        cb.isTrue(teacher.get("isActive")),
                        cb.equal(region.get("title"),"Θεσσαλονίκη")
                        )
                );

        List<Teacher> teachers = em.createQuery(query).getResultList();
        teachers.forEach(System.out::println);

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
