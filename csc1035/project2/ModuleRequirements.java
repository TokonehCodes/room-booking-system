package csc1035.project2;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * An Entity Class to represent the Module Requirements.
 * The class contains 6 fields - named as followed:
 * The Id of the module.
 * The week the module commences.
 * The amount of lectures per week.
 * The length of the lectures.
 * The number of practicals per week.
 * The practical length.
 *
 * @author Team 64
 */
@Entity(name = "MODULE_REQUIREMENTS")
public class ModuleRequirements {

    @Id
    private String id;

    @Column
    private LocalDate weekCommencing;

    @Column
    private int lecturesPerWeek;

    @Column
    private int lectureLength;

    @Column
    private int practicalsPerWeek;

    @Column
    private int practicalLength;

    public ModuleRequirements(String id, LocalDate weekCommencing, int lecturesPerWeek, int lectureLength,
                              int practicalsPerWeek, int practicalLength) {
        this.id = id;
        this.weekCommencing = weekCommencing;
        this.lecturesPerWeek = lecturesPerWeek;
        this.lectureLength = lectureLength;
        this.practicalsPerWeek = practicalsPerWeek;
        this.practicalLength = practicalLength;
    }

    public ModuleRequirements(){
    }

    /***
     * Getter method for id variable.
     * @return The id of the ModuleRequirements object.
     */
    public String getId() {
        return id;
    }

    /***
     * Setter method for id variable.
     * @param id The id of the ModuleRequirements object.
     */
    public void setId(String id) {
        this.id = id;
    }

    /***
     * Getter method for weekCommencing variable/
     * @return The weekCommencing of the ModuleRequirements object.
     */
    public LocalDate getWeekCommencing() {
        return weekCommencing;
    }

    /***
     * Setter method for variable weekCommencing.
     * @param weekCommencing The weekCommencing of the ModuleRequirements object.
     */
    public void setWeekCommencing(LocalDate weekCommencing) {
        this.weekCommencing = weekCommencing;
    }

    /***
     * Getter method for variable lecturesPerWeek.
     * @return The number of lectures per week of the ModuleRequirements object.
     */
    public int getLecturesPerWeek() {
        return lecturesPerWeek;
    }

    /***
     * Setter method for variable lecturesPerWeek.
     * @param lecturesPerWeek The number of lectures per week.
     */
    public void setLecturesPerWeek(int lecturesPerWeek) {
        this.lecturesPerWeek = lecturesPerWeek;
    }

    /***
     * Getter method for variable lectureLength.
     * @return The length of the lecture of the ModuleRequirements object.
     */
    public int getLectureLength() {
        return lectureLength;
    }

    /***
     * Setter method for variable lectureLength.
     * @param lectureLength The length of the lecture (duration).
     */
    public void setLectureLength(int lectureLength) {
        this.lectureLength = lectureLength;
    }

    /***
     * Getter method for variable practicalsPerWeek.
     * @return The number of practicals per week of the ModuleRequirements object.
     */
    public int getPracticalsPerWeek() {
        return practicalsPerWeek;
    }

    /***
     * Setter method for variable practicalsPerWeek.
     * @param practicalsPerWeek The number of practicals per week.
     */
    public void setPracticalsPerWeek(int practicalsPerWeek) {
        this.practicalsPerWeek = practicalsPerWeek;
    }

    /***
     * Getter method for variable practicalLength.
     * @return The duration of a practical of the ModuleRequirements object.
     */
    public int getPracticalLength() {
        return practicalLength;
    }

    /***
     * Setter method for variable practicalLength.
     * @param practicalLength The duration of a practical.
     */
    public void setPracticalLength(int practicalLength) {
        this.practicalLength = practicalLength;
    }

    /***
     * The method toString produces a user friendly representation of a ModuleRequirements object.
     */
    @Override
    public String toString() {
        return "ModuleRequirements{" +
                "id='" + id + " " +
                ", weekCommencing='" + weekCommencing + " " +
                ", lecturesPerWeek=" + lecturesPerWeek + " " +
                ", lectureLength=" + lectureLength + " " +
                ", practicalsPerWeek=" + practicalsPerWeek + " " +
                ", practicalLength=" + practicalLength + " " +
                "" +
                "}\n";
    }
}
