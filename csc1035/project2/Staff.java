package csc1035.project2;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an Entity Class that represents a Staff Member.
 * It has the following fields:
 * ID of Staff
 * First name of Staff
 * Surname of Staff
 *
 * @author Team 64
 */
@Entity(name = "STAFF")
public class Staff {

    public static List<Reservations> resStaff = new ArrayList<>();

    @Id
    @Column
    private String id;

    @Column
    private String first_name;

    @Column
    private String last_name;

    public Staff(String id, String first_name, String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Staff(){
    }

    /***
     * Getter method for variable id.
     * @return The id of the Staff object.
     */
    public String getId() {
        return id;
    }

    /***
     * Setter method for variable id.
     * @param id The id of a Staff member.
     */
    public void setId(String id) {
        this.id = id;
    }

    /***
     * Getter method for variable first_name.
     * @return The first name of the Staff object.
     */
    public String getFirst_name() {
        return first_name;
    }

    /***
     * Setter method for variable first_name.
     * @param first_name The first name of a Staff member.
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /***
     * Getter method for variable last_name.
     * @return The last name of the Staff object.
     */
    public String getLast_name() {
        return last_name;
    }

    /***
     * Setter method for variable last_name.
     * @param last_name The last name of a Staff member.
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /***
     * Method for adding a reservation to the resStaff list (Timetable of Staff member).
     * @param r The reservation to be added.
     */
    public void addReservation(Reservations r){
        if(!resStaff.contains(r)){
            resStaff.add(r);
        }
    }

    /**
     * Method for removing a reservation from the resStaff list (Timetable of Staff member).
     * @param r The reservation to remove.
     */
    public void removeReservation(Reservations r){
        resStaff.remove(r);
    }

    /**
     * A method to get all the reservations for a particular Staff Member.
     * @return A list of all the reservations for that particular Staff object.
     */
    public List<Reservations> getReservations(){
        return resStaff;
    }

    @Override
    public String toString() {
        return "[Staff ID='" + id + '\'' +
                ", First Name='" + first_name + '\'' +
                ", Last Name='" + last_name + '\'' +
                ']';
    }
}
