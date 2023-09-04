package csc1035.project2;

import javax.persistence.*;

/***
 * Entity class for Rooms.
 * A room object contains:
 * the room number (room_number)
 * the type of the room (type)
 * the maximum capacity of the room (max_capacity)
 * the capacity under socially distant conditions (social_distancing_capacity)
 *
 * @author Team 64
 */
@Entity(name = "ROOMS")
public class Rooms {

    @Id
    @Column
    private double room_number;

    @Column
    private String type;

    @Column
    private int max_capacity;

    @Column
    private int social_distancing_capacity;


    public Rooms(double room_number, String type, int max_capacity, int social_distancing_capacity) {
        this.room_number = room_number;
        this.type = type;
        this.max_capacity = max_capacity;
        this.social_distancing_capacity = social_distancing_capacity;
    }

    public Rooms() {

    }

    /***
     * Getter method for room_number variable
     * @return the room_number of the Rooms object
     */
    public double getRoom_number() {
        return room_number;
    }

    /***
     * Setter method for setting the room_number variable
     * @param room_number the number of the room
     */
    public void setRoom_number(double room_number) {
        this.room_number = room_number;
    }

    /***
     * Getter method for type variable
     * @return the type of the Rooms object
     */
    public String getType() {
        return type;
    }

    /***
     * Setter method for setting the type variable
     * @param type the type of the Rooms object
     */
    public void setType(String type) {
        this.type = type;
    }

    /***
     * Getter method for max_capacity variable
     * @return the max_capacity of a Rooms object
     */
    public int getMax_capacity() {
        return max_capacity;
    }

    /***
     * Setter method for setting max_capacity
     * @param max_capacity the maximum capacity of a room
     */
    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }

    /***
     * Getter method for social_distancing_capacity
     * @return social_distancing_capacity of a Rooms object
     */
    public int getSocial_distancing_capacity() {
        return social_distancing_capacity;
    }

    /***
     * Setter method for setting social_distancing_capacity
     * @param social_distancing_capacity the capacity of a room under socially distant conditions
     */
    public void setSocial_distancing_capacity(int social_distancing_capacity) {
        this.social_distancing_capacity = social_distancing_capacity;
    }

    /***
     * A toString method to produce a user friendly representation of a Rooms object
     */
    @Override
    public String toString() {
        return "[Room Number: " + room_number +" "+
                "Type: " + type +" "+
                "Max Capacity: " + max_capacity +" "+
                "Social Distancing Capacity: " + social_distancing_capacity+"]";
    }




}


