package csc1035.project2;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an Entity Class that represents a Student.
 * It contains 3 fields, namely:
 * Students' ID
 * First name of Student
 * Last name of Student
 *
 * @author Team 64
 */
@Entity(name = "STUDENTS")
public class Students {

    public static List<Reservations> studentRes = new ArrayList<>();

    @Id
    @Column
    private String id;

    @Column
    private String first_name;

    @Column
    private String last_name;

    public Students(String id, String first_name, String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Students() {
    }

    /***
     * Getter method for variable id.
     * @return The student id of a Students object.
     */
    public String getId() {
        return id;
    }

    /***
     * Setter method for variable id.
     * @param id An id of a student.
     */
    public void setId(String id) {
        this.id = id;
    }

    /***
     * Getter method for variable first_name.
     * @return The first name of a Students object.
     */
    public String getFirst_name() {
        return first_name;
    }

    /***
     * Setter method for variable first_name.
     * @param first_name The first name of a Student.
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /***
     * Getter method for variable last_name.
     * @return The last name of a Students object.
     */
    public String getLast_name() {
        return last_name;
    }

    /***
     * Setter method for variable last_name.
     * @param last_name The last name of a Student.
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /***
     * A method to add a reservation to the studentRes list. (Timetable for a Student)
     * @param r The reservation to be added.
     */
    public void addReservation(Reservations r){
        if(!studentRes.contains(r)){
            studentRes.add(r);
        }
    }

    /**
     * A method to remove a reservation from the studentRes list.
     * @param r The reservation to remove.
     */
    public void removeReservation(Reservations r){
        studentRes.remove(r);
    }

    /**
     * A method to list all the reservations for a Student.
     * @return The list of all the reservations of a particular Student.
     */
    public List<Reservations> getReservations(){
        return studentRes;
    }

    @Override
    public String toString() {
        return "[Student ID='" + id + '\'' +
                ", First Name='" + first_name + '\'' +
                ", Last Name='" + last_name + '\'' +
                ']';
    }
}
