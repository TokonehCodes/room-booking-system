package test_Classes;

import csc1035.project2.Reservations;
import csc1035.project2.RoomBookingSystem;
import csc1035.project2.Rooms;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static csc1035.project2.RoomBookingSystem.bookRoom;
import static csc1035.project2.RoomBookingSystem.cancelRoom;

/***
 * Test class for RoomBookingSystem class.
 *
 * @author Team 64
 */
public class test_RoomBookingSystem {
    public static void main(String[] args) {

        //finding available rooms
        RoomBookingSystem rbs1 = new RoomBookingSystem();
        rbs1.findAvailableRooms(LocalDate.of(2020, 05, 02), LocalTime.of(12, 00), 2, 25);

        //change room details
        RoomBookingSystem rbs2 = new RoomBookingSystem();
        rbs2.updateRoomDetails(new Rooms(0.365, "PC CLUSTER", 25, 50));

        //make timetable for specific room
        RoomBookingSystem rbs3 = new RoomBookingSystem();
        rbs3.createTimetableForRoom();

        // Booking and canceling the reservation
        List<Reservations> r = bookRoom(0.365, LocalDate.of(2021,05,15), LocalTime.of(12,00),2);
        cancelRoom(r.get(0));

    }
}
