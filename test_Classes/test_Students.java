package test_Classes;

import csc1035.project2.HibernateUtil;
import csc1035.project2.Students;
import org.hibernate.Session;
import java.util.List;

/***
 * Test class for Students class
 *
 * @author Team 64
 */
public class test_Students {

    public static void main(String[] args) {

        // Testing creation of Students objects
        Students stud1 = new Students("683948036", "James", "White");

        // Opening connection to database
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(stud1);
        session.getTransaction().commit();

        // Printing all StudentMod objects
        List<Students> listStaffMod = session.createQuery(" FROM STUDENTS ").list();
        for (Students students : listStaffMod) {
            System.out.println(students);
        }

        // Delete test object from the database
        session.beginTransaction();
        session.delete(stud1);
        session.getTransaction().commit();

        // Closing connection
        session.close();
    }
}
