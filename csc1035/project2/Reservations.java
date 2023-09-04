package csc1035.project2;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/***
 * Entity class representing Reservations.
 * Contains 4 fields:
 * The number of the room (room_number)
 * The reservation date (res_date)
 * The reservation time (res_time)
 * The duration of the reservation (duration)
 *
 * @author Team 64
 */
@Entity(name= "RESERVATIONS")
public class Reservations {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private int reservation_number;

    @Column
    private double room_number;

    @Column
    private LocalDate resDate;

    @Column
    private LocalTime resTime;

    @Column
    private int duration;

    public Reservations(double room_number, LocalDate resDate, LocalTime resTime, int duration) {
        this.room_number = room_number;
        this.resDate = resDate;
        this.resTime = resTime;
        this.duration = duration;
    }

    public Reservations(){
    }

    /***
     * Getter method for variable room_number.
     * @return The room number of the Reservations object.
     */
    public double getRoom_number() {
        return room_number;
    }

    /***
     * Setter method for variable room_number.
     * @param room_number The room number of a reservation.
     */
    public void setRoom_number(double room_number) {
        this.room_number = room_number;
    }

    /***
     * Getter method for variable resDate.
     * @return The reservation date of the Reservations object.
     */
    public LocalDate getResDate() {
        return resDate;
    }

    /***
     * Setter method for variable resDate.
     * @param resDate The reservation date.
     */
    public void setResDate(LocalDate resDate) {
        this.resDate = resDate;
    }

    /***
     * Getter method for variable resTime.
     * @return The reservation time of the Reservations object.
     */
    public LocalTime getResTime() {
        return resTime;
    }

    /***
     * Setter method for variable resTime.
     * @param resTime The reservation time.
     */
    public void setResTime(LocalTime resTime) {
        this.resTime = resTime;
    }

    /***
     * Getter method for variable duration.
     * @return The duration of the Reservations object.
     */
    public int getDuration() {
        return duration;
    }

    /***
     * Setter method for variable duration.
     * @param duration The duration of a reservation.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /***
     * A method to create a human readable representation of a Reservations object.
     */
    @Override
    public String toString() {
        return "Reservation [Room Number: " + room_number +
                " | Date: " + resDate +
                " | Time: " + resTime +
                " | Duration: " + duration +
                "]" + "\n";
    }


}

