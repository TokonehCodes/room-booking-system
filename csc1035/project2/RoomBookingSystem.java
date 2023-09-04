package csc1035.project2;

import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.Query;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/***
 * A class for a Room Booking System.
 * The class contains that have to do with the finding available rooms,
 * booking of rooms, cancellation of rooms and updating room details.
 *
 * @author Team 64
 */
public class RoomBookingSystem implements Interfaces.RoomBookingInterface{

    static Session session = HibernateUtil.getSessionFactory().openSession();
    static List<Reservations> listReservations = new ArrayList<>();
    static List<Rooms> listRooms = new ArrayList<>();

    public static void main(String[] args) {
        listRooms = session.createQuery("FROM ROOMS").list();
        listReservations = session.createQuery("FROM RESERVATIONS ").list();
    }

    /***
     * A method to find available rooms.
     * @param date The date we want to find a room.
     * @param time The time we want to find a room.
     * @param duration The duration we want to book the room for.
     * @param capacity The required capacity.
     * @return A list of available rooms that meet all our requirements.
     */
    public static List<Rooms> findAvailableRooms(LocalDate date, LocalTime time, int duration, int capacity) {
        List<Reservations> duplicate;
        session.beginTransaction();
        session.getTransaction().commit();
        duplicate = session.createQuery(" from RESERVATIONS  where  resDate = " + "'" + date + "'" + "and cast(resTime as time) between " + "'" +
                time + "'" + " and " + "'" + time.plusHours(duration) + "'").list();
        session.close();
        List<Rooms> availableRooms = new ArrayList<>();

        if(duplicate.isEmpty()){
            for (int r = 0; r < listRooms.size(); r++) {
                if (listRooms.get(r).getMax_capacity() > capacity) {
                    availableRooms.add(listRooms.get(r));
                }
            }
        }else{
            for(int res=0; res<duplicate.size(); res++){
                int durationRes = duplicate.get(res).getDuration();
                LocalTime startTime = duplicate.get(res).getResTime();
                LocalTime endTime = startTime.plusHours(durationRes);

                if (!((time.isBefore(endTime)) && (time.isAfter(startTime) || (startTime.equals(time) ||
                        (time.plusHours(duration).isBefore(endTime)) && (time.plusHours(duration).isAfter(startTime)))))) {
                    for(int r=0; r<listRooms.size(); r++){
                        if(duplicate.get(res).getRoom_number()==listRooms.get(r).getRoom_number()){
                            if(listRooms.get(r).getMax_capacity()>capacity){
                                availableRooms.add(listRooms.get(r));
                            }
                        }
                    }
                }
            }
        }
        return availableRooms;
    }

    /***
     * A method for updating a room's details.
     * @param r A Rooms object.
     */
    public static void updateRoomDetails(Rooms r) {
        Transaction tx = session.beginTransaction();
        session.update(r);
        tx.commit();
    }

    /***
     * A method for creating a Timetable for a specific room given by the user.
     */
    public void createTimetableForRoom() {
        //make timetable for a specific room (room number inputted by user)

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the room number that you would like to see the timetable for");
        String givenRoomNumber = sc.nextLine();

        List<Reservations> bookedRooms;

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.getTransaction().commit();

        String query = "FROM RESERVATIONS where room_number =" + givenRoomNumber;
        bookedRooms = session.createQuery(query).list();
        System.out.println(bookedRooms);
        session.close();

    }

    /***
     * A method that adds a reservation to a Student's or Staff's timetable.
     * @param id Student or Staff id.
     * @param r Reservations object.
     */
    public static void addReservationToStaffStudent(String id, Reservations r){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.getTransaction().commit();
        List<Students> listStudents;
        List<Staff> listStaff;
        listStudents = session.createQuery("FROM STUDENTS ").list();
        listStaff = session.createQuery("FROM STAFF ").list();
        session.close();
        for(int i=0; i<listStudents.size(); i++){
            if(listStudents.get(i).getId().equals(id)){
                if(!listStudents.get(i).getReservations().contains(r)){
                    listStudents.get(i).addReservation(r);
                }
            }
        }

        for(int i=0; i<listStaff.size(); i++){
            if(listStaff.get(i).getId().equals(id)){
                if(!listStaff.get(i).getReservations().contains(r)){
                    listStaff.get(i).addReservation(r);
                }
            }
        }
    }

    /***
     * A method that removes a reservation to a Student's or Staff's timetable.
     * @param r Reservations object.
     */
    public static void removeReservationToStaffStudent(String id, Reservations r, List<Students> listStudents, List<Staff> listStaff){
        for(int i=0; i<listStudents.size(); i++){
            if(listStudents.get(i).getId().equals(id)){
                List<Reservations> studentTimetable = listStudents.get(i).getReservations();
                if(studentTimetable.contains(r)){
                    listStudents.get(i).removeReservation(r);
                }
                break;
            }
        }

        for(int i=0; i<listStaff.size(); i++){
            if(listStaff.get(i).getId().equals(id)){
                List<Reservations> staffTimetable = listStaff.get(i).getReservations();
                if(staffTimetable.contains(r)){
                    listStaff.get(i).removeReservation(r);
                }
            }
        }
    }

    /***
     * A method for booking a room.
     * @param room_number The number of the room.
     * @param date The date we want to find a room.
     * @param time The time we want to find a room.
     * @param duration The duration we want to book the room for.
     * @return The list of Reservation.
     */
    public static List<Reservations> bookRoom(double room_number, LocalDate date, LocalTime time, int duration) {
        // Creating an object with the details of the new reservation
        Reservations newReservation = new Reservations(room_number, date, time, duration);
        List<Reservations> rlist = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        // List that checks all reservations for a given date
        List<Reservations> daily = session.createQuery(" from RESERVATIONS  where room_number = "
                + room_number + " and resDate = " + "'" + date + "'" ).list();
        session.getTransaction().commit();

        // If the list is empty it means that there are no reservations at that day
        if (daily.isEmpty()) {
            session.beginTransaction();
            session.save(newReservation);
            session.getTransaction().commit();
            System.out.println("Room " + room_number + " was successfully booked on " + date + "\n " +
                    "The booking receipt has been created in the project directory");
            // Printing a text file with a booking confirmation
            try{
                PrintWriter writer = new PrintWriter("Booking_Receipt.txt", "UTF-8");
                writer.println("...Booking Receipt...");
                writer.println("---------------------");
                writer.println("You booked the room: " + room_number + "\n" +
                        "Date: " + date + "\n" +
                        "Time: " + time);
                writer.close();
            }
            catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        // Integer that checks if any of the reservation that the user is
        // trying to book is within the boundary of another reservation
        int cc = 0;
        if (!daily.isEmpty()) {
            for (Reservations res : daily) {

                LocalTime starttime = res.getResTime();
                int dur = res.getDuration();
                // Get dates from database
                LocalTime endtime = starttime.plusHours(dur);
                // If there is a conflict between the reservation that the user inputs,
                // then it doesnt iterate the cc value
                if ((time.isBefore(endtime)) && (time.isAfter(starttime) || (starttime.equals(time) ||
                        (time.plusHours(duration).isBefore(endtime)) && (time.plusHours(duration).isAfter(starttime))))) {
                    cc = cc;
                }
                // If the new reservation doesnt have any conflicts with the current reservation
                // from the list, it iterates the cc value
                else {
                    cc+=1;
                }
            }

            // If the cc value is equal to the size of the list,
            // it means that there were no conflicts at all with any reservations that are already booked
            if(cc == daily.size()){
                // Adding the reservations to the database
                session.beginTransaction();
                session.save(newReservation);
                session.getTransaction().commit();
                // Adding the new reservation to the list that will be returned
                rlist.add(newReservation);
                System.out.println("Room " + room_number + " was successfully booked on " + date + "\n " +
                        "The booking receipt has been created in the project directory");
                // Printing a text file with a booking confirmation
                try {
                    PrintWriter writer = new PrintWriter("Booking_Receipt.txt", "UTF-8");
                    writer.println("...Booking Receipt...");
                    writer.println("---------------------");
                    writer.println("You booked the room: " + room_number + "\n" +
                            "Date: " + date + "\n" +
                            "Time: " + time);
                    writer.close();
                } catch (FileNotFoundException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }
            // If the cc value is not the same as the size of the list it means that
            // one or more reservations had a conflict with the new reservations and the room isn't booked.
            else if(cc != daily.size()){
                //print room cant be booked
                System.out.println("This room is already booked at that time");
            }
        }
        session.close();
        return rlist;
    }

    /***
     * A method for cancelling a reservation.
     * @param cancel The Reservations object to be cancelled.
     */
    public static void cancelRoom(Reservations cancel){
        Session session = HibernateUtil.getSessionFactory().openSession();
        // Deleting the choice from the table
        session.beginTransaction();
        Query q = session.createQuery(" DELETE FROM RESERVATIONS WHERE room_number = " + cancel.getRoom_number() +
                " and resDate = " +"'"+ cancel.getResDate() + "'" + " and cast(resTime as time) = "+"'"+ cancel.getResTime() +"'");
        q.executeUpdate();
        session.getTransaction().commit();
        session.close();
        //Remove reservation from listReservations
        for(int i=0; i<RoomBookingSystem.listReservations.size(); i++){
            if(RoomBookingSystem.listReservations.get(i)==cancel){
                RoomBookingSystem.listReservations.remove(i);
            }
        }
        System.out.println("The booking was successfully canceled!");
    }
}
