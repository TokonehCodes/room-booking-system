package test_Classes;

import csc1035.project2.TimetablingSystem;

/***
 * Test class for Timetabling System
 *
 * @author Team 64
 */
public class test_TimetablingSystem {
    public static void main(String[] args) {

        //create list of students who take a module
        TimetablingSystem tbs1 = new TimetablingSystem();
        tbs1.createListStudentTakesMod();

        // create list of staff take specific module
        TimetablingSystem tbs2 = new TimetablingSystem();
        tbs2.createListStaffTeachMod();

        //create list of rooms
        TimetablingSystem tbs3 = new TimetablingSystem();
        tbs3.createListOfRooms();

        //create timetable
        TimetablingSystem.createTimetable("Normal");

        //Produce Timetable
        System.out.println(TimetablingSystem.schoolTimetable.toString());

        //Delete timetable
        TimetablingSystem.deleteTimetable();

        //Check a student's timetable
        TimetablingSystem.produceTimetable("212815250");

    }

}
