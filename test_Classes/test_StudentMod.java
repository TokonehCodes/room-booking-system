package test_Classes;

import csc1035.project2.HibernateUtil;
import csc1035.project2.StudentMod;
import csc1035.project2.Students;
import org.hibernate.Session;
import java.util.List;

/***
 * Test class for StudentMod class.
 *
 * @author Team 64
 */
public class test_StudentMod {

    public static void main(String[] args) {

        // Testing creation of a StudentMod object
        StudentMod sm1 = new StudentMod("212455073", "BBU5808");

        // Opening connection to database and saving the test object
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(sm1);
        session.getTransaction().commit();

        // Printing the contents of the table
        List<Students> listStudentMod = session.createQuery(" FROM STUDENTMOD ").list();
        System.out.println(listStudentMod);

        // Deleting the test object from the database
        session.beginTransaction();
        session.delete(sm1);
        session.getTransaction().commit();

        // Closing connection
        session.close();

    }
}
