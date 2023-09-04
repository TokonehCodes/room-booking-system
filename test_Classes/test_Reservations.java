package test_Classes;

import csc1035.project2.HibernateUtil;
import csc1035.project2.ModuleRequirements;
import csc1035.project2.Reservations;
import org.hibernate.Session;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/***
 * Test class for Reservations class.
 *
 * @author Team 64
 */
public class test_Reservations {

    public static void main(String[] args) {

        //Testing creation of Reservations objects
        Reservations res1 = new Reservations(0.131, LocalDate.of(2021, 5, 9), LocalTime.of(15, 10),2);

        // Opening session
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Adding the test objects to the database
        session.saveOrUpdate(res1);
        session.getTransaction().commit();

        // Listing all objects in the table MODULE_REQUIREMENTS
        List<ModuleRequirements> moduleRequirements = session.createQuery(" from RESERVATIONS ").list();
        System.out.println(moduleRequirements);

        // Deleting the objects from the database
        session.beginTransaction();
        session.delete(res1);
        session.getTransaction().commit();
        session.close();

    }
}
