package csc1035.project2;

import org.hibernate.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * This is a class that contains methods relating to Timetable.
 *
 * @author Team 64
 */
public class TimetablingSystem implements Interfaces.TimetablingInterface {

    //All the reservations for a school timetable. Can be used to delete reservations from database later.
    public static List<Reservations> schoolTimetable = new ArrayList<>();

    /**
     * A method that creates a list of students that take a particular module (which is entered by the user).
     */
    public void createListStudentTakesMod() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a module id to see which students take this module");
        String givenStudentModId = sc.nextLine();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.getTransaction().commit();

        String query = "FROM STUDENTMOD where m_id = " + givenStudentModId;
        List<Students> StudentsTakeModule = session.createQuery(query).list();
        System.out.println(StudentsTakeModule);
        session.close();
    }

    /**
     * A method that creates a list of staff members that teach a particular module (which is entered by the user).
     */
    public void createListStaffTeachMod() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter module id to see which staff teach this module");
        String givenStaffModId = sc.nextLine();

        List<Staff> staffDoModule;

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.getTransaction().commit();

        String query = "FROM STAFFMOD where module_id =" + givenStaffModId;
        staffDoModule = session.createQuery(query).list();
        System.out.println(staffDoModule);
        session.close();
    }

    /**
     * A method that creates a list of all the rooms.
     */
    public void createListOfRooms() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.getTransaction().commit();


        String query = "FROM ROOMS";
        List<Rooms> listRooms = session.createQuery(query).list();
        System.out.println(listRooms);
        session.close();
    }

    /**
     * The method automatically creates a timetable for the school for an academic year.
     *
     * @param type The type of Timetable; either Normal or under Socially Distant conditions.
     */
    public static void createTimetable(String type) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<StudentMod> studentsTakeModule;
        List<StaffMod> staffTeachModule;
        List<ModuleRequirements> moduleRequirements;
        List<Modules> modules;
        List<Rooms> listRooms;
        studentsTakeModule = session.createQuery("FROM STUDENTMOD ").list();
        staffTeachModule = session.createQuery("FROM STAFFMOD ").list();
        moduleRequirements = session.createQuery("FROM MODULE_REQUIREMENTS").list();
        modules = session.createQuery("FROM MODULES").list();
        listRooms = session.createQuery("FROM ROOMS").list();
        List<Students> listStudents;
        List<Staff> listStaff;
        listStudents = session.createQuery("FROM STUDENTS").list();
        listStaff = session.createQuery("FROM STAFF").list();
        //Modules
        for (int i = 0; i < modules.size(); i++) {
            List<String> studentMod = new ArrayList<>();
            List<String> staffMod = new ArrayList<>();
            ModuleRequirements modReq = new ModuleRequirements();
            for (int j = 0; j < moduleRequirements.size(); j++) {
                //Populating studentMod and staffMod
                if (modules.get(i).getId().equals(moduleRequirements.get(j).getId())) {
                    modReq = moduleRequirements.get(j);
                    for (StudentMod mod : studentsTakeModule) {
                        if (mod.getM_id().equals(modules.get(i).getId())) {
                            if (!studentMod.contains(mod.getS_id()))
                                studentMod.add(mod.getS_id());
                        }
                    }
                    for (StaffMod mod : staffTeachModule) {
                        if (mod.getModule_id().equals(modules.get(i).getId())) {
                            if (!studentMod.contains(mod.getStaff_id()))
                                staffMod.add(mod.getStaff_id());
                        }
                    }
                }
            }
                //Iterating through weeks
                for (int w = 0; w < modules.get(i).getWeeks(); w++) {
                    LocalDate startWeek = modReq.getWeekCommencing().plusWeeks(w);

                    //Lectures
                    //Iterating though lecture days
                    for (int d = 0; d < modReq.getLecturesPerWeek(); d++) {

                        //Lecture day is calculated by adding the number of lectures to the first day of the week
                        LocalDate lectureDay = startWeek.plusDays(d);

                        //Duration of lecture
                        int durationLecture = modReq.getLectureLength();

                        //To calculate lectureTime we call the method findAvailableTime
                        LocalTime lectureTime = findAvailableTime(lectureDay,durationLecture);
                        int k=0;
                        while(lectureTime==null && k<5){
                            lectureTime = findAvailableTime(startWeek.plusDays(k),durationLecture);
                            if(lectureTime!=null){
                                lectureDay = lectureDay.plusDays(k);
                            }
                            k++;
                        }

                        //List of lecture rooms (we check for capacity and the room type)
                        List<Rooms> lectureRooms = new ArrayList<>();
                        for (Rooms listRoom : listRooms) {
                            if (listRoom.getType().equals("Lecture Room")) {
                                if (type.equals("Normal") && listRoom.getMax_capacity() > studentMod.size()) {
                                    lectureRooms.add(listRoom);
                                } else if (type.equals("Socially Distant") && listRoom.getSocial_distancing_capacity() > studentMod.size()) {
                                    lectureRooms.add(listRoom);
                                }
                            }
                        }

                        //Choosing a random room
                        Random rand = new Random();
                        Rooms randomRoom = lectureRooms.get(rand.nextInt(lectureRooms.size()));
                        double roomNumber = randomRoom.getRoom_number();

                        //Make a new Reservation
                        Reservations lecture = new Reservations(roomNumber,lectureDay,lectureTime,durationLecture);
                        session.save(lecture);

                        //Add reservation to schoolTimetable list
                        schoolTimetable.add(lecture);

                        //Adding reservation to each student's timetable
                        for(String student : studentMod){
                            for (Students listStudent : listStudents) {
                                if (listStudent.getId().equals(student)) {
                                    listStudent.addReservation(lecture);
                                    break;
                                }
                            }
                        }

                        //Adding reservation to each staff's timetable
                        for(String staff : staffMod){
                            for(Staff listStaffMember : listStaff){
                                if(listStaffMember.getId().equals(staff)){
                                    listStaffMember.addReservation(lecture);
                                    break;
                                }
                            }
                        }
                    }
                    //Practicals
                    for (int p = 0; p < modReq.getPracticalsPerWeek(); p++) {

                        //Practical day is calculated by adding the number of lectures to the first day of the week
                        LocalDate practicalDay = startWeek.plusDays(p);

                        //Duration of practical
                        int durationPractical = modReq.getPracticalLength();

                        //To calculate practicalTime we call the method findAvailableTime
                        LocalTime practicalTime = findAvailableTime(practicalDay,durationPractical);
                        int k=0;
                        while(practicalTime==null && k<5){
                            practicalTime = findAvailableTime(startWeek.plusDays(k),durationPractical);
                            if(practicalTime!=null){
                                practicalDay = practicalDay.plusDays(k);
                            }
                            k++;
                        }

                        //List of practical rooms (we check for capacity and the room type)
                        List<Rooms> practicalRooms = new ArrayList<>();
                        for (Rooms listRoom : listRooms) {
                            if (listRoom.getType().equals("PC Cluster")) {
                                if (type.equals("Normal") && listRoom.getMax_capacity() > studentMod.size()) {
                                    practicalRooms.add(listRoom);
                                } else if (type.equals("Socially Distant") && listRoom.getSocial_distancing_capacity() > studentMod.size()) {
                                    practicalRooms.add(listRoom);
                                }
                            }
                        }

                        //Choosing a random room
                        Random rand = new Random();
                        Rooms randomRoom = practicalRooms.get(rand.nextInt(practicalRooms.size()));
                        double roomNumber = randomRoom.getRoom_number();

                        //Make a new Reservation
                        Reservations practical = new Reservations(roomNumber,practicalDay,practicalTime,durationPractical);
                        session.save(practical);

                        //Add reservation to schoolTimetable list
                        schoolTimetable.add(practical);

                        //Adding reservation to each student's timetable
                        for(String student : studentMod){
                            for (Students listStudent : listStudents) {
                                if (listStudent.getId().equals(student)) {
                                    listStudent.addReservation(practical);
                                    break;
                                }
                            }
                        }

                        //Adding reservation to each staff's timetable
                        for(String staff : staffMod){
                            for(Staff listStaffMember : listStaff){
                                if(listStaffMember.getId().equals(staff)){
                                    listStaffMember.addReservation(practical);
                                    break;
                                }
                            }
                        }
                    }
                }
        }
        session.getTransaction().commit();
        session.close();
    }

    /**
     * A method to produce a timetable for a given student/staff member.
     * @param id represents the id of a given student/ staff member to get a timetable for.
     */
        public static void produceTimetable(String id) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.getTransaction().commit();
            List<Students> listStudents;
            List<Staff> listStaff;
            listStudents = session.createQuery("FROM STUDENTS ").list();
            listStaff = session.createQuery("FROM STAFF ").list();
            session.close();
            Scanner s = new Scanner(System.in);
            int choice = -1;
            while(choice!=3){
                System.out.println("1. Print Timetable for specific period of time\n" +
                        "2. Print Timetable for entire school year\n" +
                        "3. Back\n");
                choice = s.nextInt();
                if(choice == 1){
                    System.out.println("Enter start date (yyyy-mm-dd):");
                    LocalDate startDate = LocalDate.parse(s.next());
                    System.out.println("Enter end date (yyyy-mm-dd):");
                    LocalDate endDate = LocalDate.parse(s.next());
                    if(!(startDate.isAfter(endDate)||endDate.isBefore(startDate))){
                        for (int i = 0; i < listStudents.size(); i++) {
                            if (listStudents.get(i).getId().equals(id)) {
                                List<Reservations> studentTimetable = listStudents.get(i).getReservations();
                                System.out.println("Timetable for "+listStudents.get(i).getFirst_name()+" "+listStudents.get(i).getLast_name());
                                for(int t=0; t<studentTimetable.size(); t++){
                                    if((studentTimetable.get(t).getResDate().isAfter(startDate)&&studentTimetable.get(t).getResDate().isBefore(endDate))||
                                            (studentTimetable.get(t).getResDate().equals(startDate))){
                                        System.out.println(studentTimetable.get(t));
                                    }
                                }
                            }
                        }

                        for(int j=0; j<listStaff.size(); j++){
                            if(listStaff.get(j).getId().equals(id)){
                                List<Reservations> staffTimetable = listStaff.get(j).getReservations();
                                System.out.println("Timetable for "+listStaff.get(j).getFirst_name()+" "+listStaff.get(j).getLast_name());
                                for(int t=0; t<staffTimetable.size(); t++){
                                    if((staffTimetable.get(t).getResDate().isAfter(startDate)&&staffTimetable.get(t).getResDate().isBefore(endDate))||
                                            (staffTimetable.get(t).getResDate().equals(startDate))){
                                        System.out.println(staffTimetable.get(t));
                                    }
                                }
                            }
                        }
                    }else{
                        System.out.println("Please enter a valid start and end date");
                    }

                }else if(choice ==2){
                    for (int i = 0; i < listStudents.size(); i++) {
                        if (listStudents.get(i).getId().equals(id)) {
                            System.out.println("Timetable for "+listStudents.get(i).getFirst_name()+" "+listStudents.get(i).getLast_name());
                            System.out.println(listStudents.get(i).getReservations());
                        }
                    }

                    for(int j=0; j<listStaff.size(); j++){
                        if(listStaff.get(j).getId().equals(id)){
                            System.out.println("Timetable for "+listStaff.get(j).getFirst_name()+" "+listStaff.get(j).getLast_name());
                            System.out.println(listStaff.get(j).getReservations());
                        }
                    }
                }else{
                    System.out.println("Please enter a valid value...");
                }
            }
        }

    /**
     * The method deletes all Reservations created by the createTimetable method both on the
     * database and each Student's or Staff member's individual timetable.
     */
    public static void deleteTimetable(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.getTransaction().commit();
        List<Students> listStudents;
        List<Staff> listStaff;
        listStudents = session.createQuery("FROM STUDENTS ").list();
        listStaff = session.createQuery("FROM STAFF ").list();
        session.close();
            if(schoolTimetable.isEmpty()){
                System.out.println("Timetable is empty");
            }else{
                for(int i=0; i<schoolTimetable.size(); i++){

                    //Remove reservation from database and list of reservations.
                    RoomBookingSystem.cancelRoom(schoolTimetable.get(i));

                    //Remove from all Students' timetables.
                    for(int s=0; s<listStudents.size(); s++){
                        RoomBookingSystem.removeReservationToStaffStudent(listStudents.get(s).getId(), schoolTimetable.get(i), listStudents, listStaff);
                    }

                    //Remove from all Staffs' timetables.
                    for(int sm=0; sm<listStaff.size(); sm++){
                        RoomBookingSystem.removeReservationToStaffStudent(listStaff.get(sm).getId(), schoolTimetable.get(i), listStudents, listStaff);
                    }
                }
            }
        }

    /**
     * A method to find an available time for the reservations.
     * @param day represents the day that we want to find an available time.
     * @param duration represents the duration required.
     * @return An available time to make a reservation for the day specified.
     */
        public static LocalTime findAvailableTime(LocalDate day, int duration){
            LocalTime[] availableTime;
            availableTime = new LocalTime[24];
            List<Reservations> dailyTimetable = new ArrayList<>();
            for (int tt = 0; tt < schoolTimetable.size(); tt++) {
                if (schoolTimetable.get(tt).getResDate().equals(day)) {
                    dailyTimetable.add(schoolTimetable.get(tt));
                }
            }

            for (int dt = 0; dt < dailyTimetable.size(); dt++) {
                for (int du = 0; du < dailyTimetable.get(dt).getDuration()+1; du++) {
                    int hour = dailyTimetable.get(dt).getResTime().getHour();
                    availableTime[hour + du] = LocalTime.of(hour, 0);
                }
            }

            //Find hour slot to book.
            LocalTime startTime = null;
            int h;
            for (h = 8; h < 18; h++) {
                int k = 0;
                while (availableTime[h + k] == null && k < (duration + 1)) {
                    k++;
                }
                if (k== duration+1) {
                    startTime = LocalTime.of(h, 0);
                    break;
                }
            }
            return startTime;
        }
}
