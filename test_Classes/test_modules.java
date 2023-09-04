package test_Classes;

import csc1035.project2.HibernateUtil;
import csc1035.project2.ModuleRequirements;
import csc1035.project2.Modules;
import org.hibernate.Session;
import java.util.List;

/***
 * Test class for Modules class.
 *
 * @author Team 64
 */
public class test_modules {

    public static void main(String[] args) {

        //Testing creation of Modules objects
        Modules mod1 = new Modules("HHE1234", "Computer Science", 10, 6);

        // Opening session
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Adding the test objects to the database
        session.saveOrUpdate(mod1);
        session.getTransaction().commit();

        // Listing all objects in the table MODULE_REQUIREMENTS
        List<ModuleRequirements> moduleRequirements = session.createQuery(" from MODULES ").list();
        System.out.println(moduleRequirements);

        // Deleting the objects from the database
        session.beginTransaction();
        session.delete(mod1);
        session.getTransaction().commit();
        session.close();

    }
}
