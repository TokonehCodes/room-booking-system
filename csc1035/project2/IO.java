package csc1035.project2;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.hibernate.*;

import static csc1035.project2.RoomBookingSystem.*;

/***
 * The IO class is the command line interface for the user
 * to interact with the program.
 * @author Team 64
 */
public class IO {

    public static void main(String[] args) throws IOException {
        int choice = -1;

        Scanner s = new Scanner(System.in);
        while(choice != 3){

            System.out.println("...Welcome! Please choose one of the following options..." +"\n"+
                                "1. Room Booking System\n" + "2. Timetabling System\n" + "3. Exit");

            choice = inputCheckInt();

            if(choice == 1){
                // Room Booking System
                int i = -1;
                while(i != 4){
                 System.out.println("...Welcome to the Room Booking System...\n" +
                                    "1. Room Reservations and Cancellation\n" +
                                    "2. Update Room details\n" + "3. Produce Timetable for a Room\n" +
                                    "4. Back");
                 i = inputCheckInt();

                 if(i == 1){
                     // Reservations and cancellation
                     // Show available rooms
                     // Produce booking confirmation
                     int k = -1;
                     while(k != 3){
                         System.out.println("...Booking and Cancellation Menu...\n" +
                                 "1. Book a Room\n" + "2. Cancel a Reservation\n" +
                                 "3. Back");
                         k = inputCheckInt();
                         if(k == 1){
                             // Book a room
                             List<Rooms> listRooms;
                             Session session = HibernateUtil.getSessionFactory().openSession();
                             session.beginTransaction();
                             listRooms = session.createQuery(" FROM ROOMS ").list();
                             session.getTransaction().commit();
                             System.out.println("Enter your Student ID or Staff ID: ");
                             String id;
                             boolean r = checkUserId(id = s.next());
                             if(r){
                                 // Print all rooms with an index on the front so the user can choose
                                 for(int m=0; m<listRooms.size();m++){
                                     System.out.println((m+1) +"."+ listRooms.get(m));
                                 }
                                 System.out.println("Choose a room to book from the list above");
                                 // Capture the room chosen
                                 int room_choice = inputCheckInt() - 1;
                                 Rooms room = listRooms.get(room_choice);
                                 System.out.println("For which date would u like to book the room? (yyyy-mm-dd)");
                                 LocalDate date = inputCheckDate();
                                 System.out.println("For what time would u like to book the room? (hh:mm)");
                                 LocalTime time = inputCheckTime();
                                 System.out.println("For how many hours is the booking for?");
                                 int duration = inputCheckInt();
                                 List<Reservations> returnedlist = bookRoom(room.getRoom_number(), date, time, duration);
                                 session.close();
                                 // If the list is empty it means that the reservation
                                 if(!returnedlist.isEmpty()){
                                     //Add to reservations' list
                                     RoomBookingSystem.listReservations.add(returnedlist.get(0));
                                     //Add reservation to student's or staff's timetable
                                     RoomBookingSystem.addReservationToStaffStudent(id, returnedlist.get(0));
                                 }
                             }
                             else{
                                 System.out.println("The id given was invalid, Please try again");
                             }


                             System.out.println("Press Enter to Continue...");
                             System.in.read();

                         }
                         else if(k == 2){
                             // Cancel a reservation
                             // Opening Session
                             Session ses = HibernateUtil.getSessionFactory().openSession();
                             ses.beginTransaction();
                             List<Reservations> reservedRooms = ses.createQuery(" FROM RESERVATIONS ").list();
                             List<Students> listStudents = ses.createQuery(" FROM STUDENTS ").list();
                             List<Staff> listStaff = ses.createQuery(" FROM STAFF ").list();
                             ses.getTransaction().commit();
                             // Choose which room to cancel
                             for(int p=0; p<reservedRooms.size();p++){
                                 System.out.println((p+1) +"."+ reservedRooms.get(p));
                             }
                             int c = inputCheckInt() - 1;
                             System.out.println("Enter your Student ID or Staff ID: ");
                             String id = s.next();
                             // Input from user
                             System.out.println("Please choose the reservation you want to cancel: ");
                             int ch = inputCheckInt();
                             Reservations cancel = reservedRooms.get(ch);
                             // Canceling reservation
                             cancelRoom(cancel);
                             // Delete from list Reservations
                             RoomBookingSystem.listReservations.remove(cancel);
                             // Delete from timetable for the specific student or staff
                             RoomBookingSystem.removeReservationToStaffStudent(id, cancel,listStudents,listStaff);
                             ses.close();
                             System.out.println("Press Enter to Continue...");
                             System.in.read();
                         }
                         else if(k == 3){
                             System.out.println("Going Back...");
                         }
                         else {
                             System.out.println("Please Enter a valid value");
                         }

                     }

                 }
                 else if(i == 2){
                     // Update room details
                     List<Rooms> listRooms;
                     Session session = HibernateUtil.getSessionFactory().openSession();
                     session.beginTransaction();
                     listRooms = session.createQuery(" FROM ROOMS ").list();
                     session.getTransaction().commit();

                     // Print all rooms with an index on the front so the user can choose
                     for (int i1 = 0; i1 < listRooms.size(); i1++) {
                         System.out.println((i1 + 1) + "." + listRooms.get(i1));
                     }
                     // Capture the room chosen

                     System.out.println("Choose a room to change details from the list above");

                     int roomChoice = inputCheckInt() - 1;
                     Rooms room = listRooms.get(roomChoice);

                     int nr = -1;
                     while (nr != 5) {
                         System.out.println("What details would you like to change from the room?");
                         System.out.println("1. Room number\n" + "2. Room type\n" +
                                 "3. Maximum capacity\n" + "4. Social distance capacity\n" + "5. Exit");

                         nr = inputCheckInt();

                         if (nr == 1) {
                             System.out.println("Enter new Room number:");
                             room.setRoom_number(inputCheckDouble());

                             updateRoomDetails(room);
                             session.close();
                         }
                         if (nr == 2) {
                             System.out.println("Enter new Room type:");
                             room.setType(s.next());

                             updateRoomDetails(room);
                             session.close();
                         }
                         if (nr == 3) {
                             System.out.println("Enter new maximum capacity:");
                             room.setMax_capacity(inputCheckInt());

                             updateRoomDetails(room);
                             session.close();
                         }
                         if (nr == 4) {
                             System.out.println("Enter new socially distant capacity:");
                             room.setSocial_distancing_capacity(inputCheckInt());

                             updateRoomDetails(room);
                             session.close();
                         }
                         else if (nr == 5){
                             System.out.println("Going back...");
                         }
                     }

                 }
                 else if(i == 3){
                     // Produce timetable for a room
                     RoomBookingSystem tt = new RoomBookingSystem();
                     tt.createTimetableForRoom();
                 }
                 else if(i == 4){
                     System.out.println("Going Back...");
                 }
                }
                System.out.println("Press Enter to Continue...");
                System.in.read();
            }

            else if(choice == 2){
                // Timetabling System
                int j = -1;
                while(j != 4){

                    System.out.println("...Welcome to Timetabling System... \n" +
                                        "1. Produce a Timetable for a Student or Staff Member\n" +
                                        "2. (ADMIN) Create a timetable for the School\n" +
                                        "3. (ADMIN) Delete the timetable\n" +
                                        "4. Back");
                    j = inputCheckInt();

                    if(j == 1){
                        //Producing a timetable for student or staff
                        System.out.println("Please enter the ID of the Student or Staff member: ");
                        String id = s.next();
                        while(!checkUserId(id)){
                            System.out.println("Please enter a valid Student or Staff ID");
                            id = s.next();
                        }
                        TimetablingSystem.produceTimetable(id);
                    }

                    else if(j == 2){
                        //Create School Timetable
                        System.out.println("Careful! By choosing this option you will create room reservations for all" +
                                " students for all modules. " +
                                "If you want to revert your changes click Delete Timetable before exiting the program\n");
                        System.out.println("Do you want to continue?\n" +
                                "1. Yes\n" + "2. No\n");
                        int userChoice = inputCheckInt();
                        if(userChoice==1){
                            System.out.println("Choose the type of Timetable you want to create:");
                            int c=-1;
                            while(c!=3){
                                System.out.println("1. Normal Timetable\n" +
                                        "2. Socially Distant Timetable (Recommended)\n" +
                                        "3. Back\n");
                                c = inputCheckInt();
                                if(c==1){
                                    TimetablingSystem.createTimetable("Normal");
                                }
                                else if(c==2){
                                    TimetablingSystem.createTimetable("Socially Distant");
                                }

                            }
                        }

                    }
                    else if(j == 3){
                        //Delete timetable.
                        System.out.println("Careful! This will delete all reservations from the database");
                        System.out.println("Do you want to continue?\n" +
                                "1. Yes\n" + "2. No\n");
                        int userChoice = inputCheckInt();
                        if(userChoice==1){
                            System.out.println("This process will take some time so grab some coffee");
                            TimetablingSystem.deleteTimetable();
                        }else if(userChoice==2){
                            System.out.println("Going Back...");
                        }else{
                            System.out.println("Please enter a valid value...");
                        }
                    }
                    else if(j == 4){
                        System.out.println("Going Back...");
                    }
                }
                System.out.println("Press Enter to Continue...");
                System.in.read();
            }
            else if(choice == 3){
                System.out.println("Press Enter to Exit...");
                System.in.read();
            }


        }

    }

    /***
     * Method for checking user id.
     * @param id Staff or Student id.
     * @return True if the id matches to a student or staff member.
     * False if the id doesn't match to a student or staff.
     */
    public static boolean checkUserId(String id){
        boolean x = false;
        List<Students> lstudents = session.createQuery(" from STUDENTS ").list();
        List<Staff> lstaff = session.createQuery(" from STAFF ").list();
        for(int i=0; i<lstudents.size(); i++){
            if(id.equals(lstudents.get(i).getId())){
                x = true;
                System.out.println("Welcome Student " + lstaff.get(i).getFirst_name() +
                        " " + lstaff.get(i).getLast_name());
                break;
            }
            else
                continue;
        }
        for(int j=0; j<lstaff.size(); j++){
            if(id.equals(lstaff.get(j).getId())){
                x = true;
                System.out.println("Welcome Staff " + lstaff.get(j).getFirst_name() +
                        " " + lstaff.get(j).getLast_name());
                break;
            }
            else{
                continue;
            }
        }

        return x;
    }

    /***
     * A method for checking the user input for a variable of type double
     * @return A user input with correct variable type
     */
    private static Double inputCheckDouble() {
        double input;
        Scanner scan = new Scanner(System.in);
        do {
            try {
                input = scan.nextDouble();
                break;
            } catch (InputMismatchException ime) {
                System.out.print("Enter as a number \n");
            }
            scan.nextLine();
        } while (true);
        return input;
    }

    /***
     * A method for checking the user input for a variable of type integer
     * @return A user input with correct variable type
     */
    private static int inputCheckInt() {
        int input;
        Scanner scan = new Scanner(System.in);
        do {
            try {
                input = scan.nextInt();
                break;
            } catch (InputMismatchException ime) {
                System.out.print("Please enter a valid number\n");
            }
            scan.nextLine();
        } while (true);
        return input;
    }

    /***
     * A method for checking the user input for a variable of type LocalDate
     * @return A user input with correct variable type
     */
    private static LocalDate inputCheckDate() {
        LocalDate input;
        Scanner scan = new Scanner(System.in);
        do {
            try {
                input = LocalDate.parse(scan.next());
                break;
            } catch (DateTimeParseException e) {
                System.out.print("Enter a date in the correct format yyyy-mm-dd\n");
            }
            scan.nextLine();
        } while (true);
        return input;
    }

    /***
     * A method for checking the user input for a variable of type LocalTime
     * @return A user input with correct variable type
     */
    private static LocalTime inputCheckTime() {
        LocalTime input;
        Scanner scan = new Scanner(System.in);
        do {
            try {
                input = LocalTime.parse(scan.next());
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Enter a time in the correct 24 hour format from 00:00 to 23:59\n");
            }
            scan.nextLine();
        } while (true);
        return input;
    }
}
