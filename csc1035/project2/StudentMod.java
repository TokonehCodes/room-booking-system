package csc1035.project2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This is an Entity Class to represent the relationship between a student and a module.
 * More specifically, the relationship that the student takes a module.
 *
 * @author Team 64
 */
@Entity(name = "STUDENTMOD")

public class StudentMod {

    @Id
    @Column
    private String s_id;

    @Column
    private String m_id;

    public StudentMod(String s_id, String m_id){
        this.m_id = m_id;
        this.s_id = s_id;
    }

    public StudentMod(){
    }

    /***
     * Getter method for variable s_id.
     * @return The id of a student.
     */
    public String getS_id() {
        return s_id;
    }

    /***
     * Setter method for variable s_id.
     * @param s_id The id of a student.
     */
    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    /***
     * Getter method for variable m_id.
     * @return The id of a module.
     */
    public String getM_id() {
        return m_id;
    }

    /***
     * Setter method for variable m_id.
     * @param m_id The id of a module.
     */
    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    /***
     * The toString method produces a user friendly representation of a StudentMod object.
     */
    @Override
    public String toString() {
        return "StudentMod{" +
                "s_id=" + s_id +
                ", m_id='" + m_id + '\'' +
                '}';
    }
}
