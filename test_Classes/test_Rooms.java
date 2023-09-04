package test_Classes;

import csc1035.project2.HibernateUtil;
import csc1035.project2.Rooms;
import org.hibernate.Session;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/***
 * Test class for Rooms class.
 *
 * @author Team 64
 */
public class test_Rooms {

    public static void main(String [] args) throws FileNotFoundException, UnsupportedEncodingException {

        // Testing creation of a room object
        Rooms r1 = new Rooms(0.111, "test room", 20, 10);
        List<Rooms> listRooms;

        // Opening connection to database
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(r1);
        session.getTransaction().commit();

        // Printing all the Rooms in the console
        listRooms = session.createQuery(" FROM ROOMS ").list();
        for (Rooms listRoom : listRooms) {
            System.out.println(listRoom);
        }

        // Deleting the test room from the database
        session.beginTransaction();
        session.delete(r1);
        session.getTransaction().commit();

        // Closing connection
        session.close();

    }
}
