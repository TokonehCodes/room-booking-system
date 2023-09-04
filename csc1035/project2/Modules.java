package csc1035.project2;
import javax.persistence.*;

/**
 * This is an Entity Class representing the modules for the university.
 * It contains 4 fields:
 * ID of the module
 * Name of the module
 * Number of credits
 * Number of weeks the module is running
 *
 * @author Team 64
 */
@Entity(name = "MODULES")
public class Modules {
    @Id
    @Column
    private String id;

    @Column
    private String name;

    @Column
    private int credits;

    @Column
    private int weeks;


    public Modules(String id, String name, int credits, int weeks){
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.weeks = weeks;
    }

    public Modules(){
    }

    /***
     * Getter method for variable id.
     * @return The id of the Modules object.
     */
    public String getId() {
        return id;
    }

    /***
     * Setter method for variable id.
     * @param id The id of the module.
     */
    public void setId(String id) {
        this.id = id;
    }

    /***
     * Getter method for variable name.
     * @return The name of the Modules object.
     */
    public String getName() {
        return name;
    }

    /***
     * Setter method for variable name.
     * @param name The name of the module.
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * Getter method for variable credits.
     * @return The credits of the Modules object.
     */
    public int getCredits() {
        return credits;
    }

    /***
     * Setter method for variable credits.
     * @param credits The credits of the module.
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /***
     * Getter method for variable weeks.
     * @return The number of weeks the Modules object is running.
     */
    public int getWeeks() {
        return weeks;
    }

    /***
     * Setter method for variable weeks.
     * @param weeks The number of weeks a module is running.
     */
    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }
}
