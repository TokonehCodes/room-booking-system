package csc1035.project2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This is an Entity Class to represent the relationship between a staff member and a module.
 * More specifically, the relationship that the staff member teaches that module.
 *
 * @author Team 64.
 */
@Entity(name = "STAFFMOD")
public class StaffMod {

    @Id
    @Column
    private String staff_id;

    @Column
    private String module_id;

    public StaffMod(String staff_id, String module_id) {

        this.staff_id = staff_id;
        this.module_id = module_id;
    }

    public StaffMod(){
    }

    /***
     * Getter method for variable staff_id.
     * @return The id of Staff member.
     */
    public String getStaff_id() {
        return staff_id;
    }

    /***
     * Setter method for variable staff_id.
     * @param staff_id The id of Staff member.
     */
    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    /***
     * Getter method for variable module_id.
     * @return The id of the module.
     */
    public String getModule_id() {
        return module_id;
    }

    /***
     * Setter method for variable module_id.
     * @param module_id The id of the module.
     */
    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }

    /***
     * The toString method produces a user friendly representation of a StaffMod object.
     */
    @Override
    public String toString() {
        return "StaffMod{" +
                "staff_id='" + staff_id + '\'' +
                ", module_id='" + module_id + '\'' +
                '}';
    }
}
