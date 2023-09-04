package test_Classes;

import csc1035.project2.HibernateUtil;
import csc1035.project2.ModuleRequirements;
import org.hibernate.Session;
import java.time.LocalDate;
import java.util.List;

/***
 * Test class for the Module Requirements class.
 * 
 * @author Team 64
 */
public class test_ModuleRequirements {
    public static void main(String[] args) {

        //Testing creation of Module Requirements objects
        ModuleRequirements moduleRequirements1 = new ModuleRequirements("BBU5808", LocalDate.of(2020, 5, 2),
                4, 5, 3, 2);
        
        // Opening connection with the database
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        // Updating the test objects to the database
        session.update(moduleRequirements1);
        
        // Reverting the change in the object
        session.getTransaction().rollback();
        
        // Listing all objects in the table MODULE_REQUIREMENTS
        List<ModuleRequirements> moduleRequirements = session.createQuery(" from MODULE_REQUIREMENTS ").list();
        System.out.println(moduleRequirements);

        // Closing connection
        session.close();
        
    }
}
