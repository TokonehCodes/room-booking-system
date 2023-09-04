package test_Classes;

import csc1035.project2.HibernateUtil;
import csc1035.project2.StaffMod;
import org.hibernate.Session;
import java.util.List;

/***
 * Test class for StaffMod Class
 *
 * @author Team 64
 */
public class test_StaffMod {

    public static void main(String[] args) {

        // Testing creation of StaffMod objects
        StaffMod stm1 = new StaffMod("NE028403", "BBU5808");

        // Opening connection to database
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(stm1);
        session.getTransaction().commit();

        // Printing all StudentMod objects
        List<StaffMod> listStaffMod;
        listStaffMod = session.createQuery(" FROM STAFFMOD ").list();
        for (StaffMod staffMod : listStaffMod) {
            System.out.println(staffMod);
        }

        // Delete test object from the database
        session.beginTransaction();
        session.delete(stm1);
        session.getTransaction().commit();

        // Closing connection
        session.close();

    }
}
