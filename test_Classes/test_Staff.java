package test_Classes;

import csc1035.project2.HibernateUtil;
import csc1035.project2.Staff;
import org.hibernate.Session;
import java.util.List;

/***
 * Test class for Staff class.
 *
 * @author Team 64
 */
public class test_Staff {

    public static void main(String[] args) {

        //Testing creation of Staff objects
        Staff staf1 = new Staff("NUC7463946", "John", "Smith");

        // Opening connection to database
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(staf1);
        session.getTransaction().commit();

        // Printing all StudentMod objects
        List<Staff> listStaffMod = session.createQuery(" FROM STAFF ").list();
        for (Staff staff : listStaffMod) {
            System.out.println(staff);
        }

        // Delete test object from the database
        session.beginTransaction();
        session.delete(staf1);
        session.getTransaction().commit();

        // Closing connection
        session.close();

    }
}
