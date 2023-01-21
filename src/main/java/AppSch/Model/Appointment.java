package AppSch.Model;

import AppSch.DAO.AppointmentDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * The Appointment class model
 */
public class Appointment {

    private int appointment_id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customer_id;
    private int user_id;
    private int contact_id;
    private String contact_name;


    /**
     * Instantiates a new Appointment.
     *
     * @param appointment_id the appointment id
     * @param title          the title
     * @param description    the description
     * @param location       the location
     * @param type           the type
     * @param start          the start
     * @param end            the end
     * @param customer_id    the customer id
     * @param user_id        the user id
     * @param contact_id     the contact id
     * @param contact_name   the contact name
     */
    public Appointment(int appointment_id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customer_id, int user_id, int contact_id, String contact_name) {
        this.appointment_id = appointment_id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_id = customer_id;
        this.user_id = user_id;
        this.contact_id = contact_id;
        this.contact_name = contact_name;
    }

    /**
     * This method creates a list for Appointment Types.
     *
     * @return the type list
     */
    public static ObservableList<String> getTypeList() {
        ObservableList<String> typeList = FXCollections.observableArrayList();

        typeList.add("Planning Session");
        typeList.add("De-Briefing");
        typeList.add("Strategy Discussion");

        return typeList;
    }

    /**
     * This method creates a list for all appointment times
     *
     * @return the time list
     */
    public static ObservableList<LocalTime> getTimeList() {
        ObservableList<LocalTime> timeList = FXCollections.observableArrayList(
                LocalTime.of(0, 00), LocalTime.of(0, 30), LocalTime.of(1, 00), LocalTime.of(1, 30),
                LocalTime.of(2, 00), LocalTime.of(2, 30), LocalTime.of(3, 00), LocalTime.of(3, 30),
                LocalTime.of(4, 00), LocalTime.of(4, 30), LocalTime.of(5, 00), LocalTime.of(5, 30),
                LocalTime.of(6, 00), LocalTime.of(6, 30), LocalTime.of(7, 00), LocalTime.of(7, 30),
                LocalTime.of(8, 00), LocalTime.of(8, 30), LocalTime.of(9, 00), LocalTime.of(9, 30),
                LocalTime.of(10, 00), LocalTime.of(10, 30), LocalTime.of(11, 00), LocalTime.of(11, 30),
                LocalTime.of(12, 00), LocalTime.of(12, 30), LocalTime.of(13, 00), LocalTime.of(13, 30),
                LocalTime.of(14, 00), LocalTime.of(14, 30), LocalTime.of(15, 00), LocalTime.of(15, 30),
                LocalTime.of(16, 00), LocalTime.of(16, 30), LocalTime.of(17, 00), LocalTime.of(17, 30),
                LocalTime.of(18, 00), LocalTime.of(18, 30), LocalTime.of(19, 00), LocalTime.of(19, 30),
                LocalTime.of(20, 00), LocalTime.of(20, 30), LocalTime.of(21, 00), LocalTime.of(21, 30),
                LocalTime.of(22, 00), LocalTime.of(22, 30), LocalTime.of(23, 00), LocalTime.of(23, 30)
        );

        return timeList;
    }

    /**
     * This method gets all appointments for the current month.
     * LAMBDA: This method implements a lambda expression. A filter method is called on the appointment list to find appointments in the current Month.
     * I believe this lambda implementation improves the readability of the code.
     *
     * @return the month's appointments
     */
    public static ObservableList<Appointment> getMonthAppointments() {
        List<Appointment> monthApptList;
        LocalDateTime currentTime = LocalDateTime.now();

        monthApptList = AppointmentDAOImpl.getAllAppointments().stream()
                                                                .filter(appointment -> appointment.getStart().getMonth().equals(currentTime.getMonth()))
                                                                .collect(Collectors.toList());
        return FXCollections.observableArrayList(monthApptList);
    }

    /**
     * This method gets all appointments for the current week.
     *
     * @return the week appointments
     */
    public static ObservableList<Appointment> getWeekAppointments() {
        ObservableList<Appointment> weekApptList = FXCollections.observableArrayList();
        LocalDateTime currentTime = LocalDateTime.now();
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int weekNumber = currentTime.toLocalDate().get(woy);
        int apptWeekNumber = 0;
        for (Appointment appointment : AppointmentDAOImpl.getAllAppointments()) {
            apptWeekNumber = appointment.getStart().toLocalDate().get(woy);
            if (apptWeekNumber == weekNumber) {
                weekApptList.add(appointment);
            }
        }
        return weekApptList;

    }


    /**
     * Gets appointment id.
     *
     * @return the appointment id
     */
    public int getAppointment_ID() {
        return appointment_id;
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContact_id() {
        return contact_id;
    }

    /**
     * Sets contact id.
     *
     * @param contact_id the contact id
     */
    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    /**
     * Sets appointment id.
     *
     * @param appointment_ID the appointment id
     */
    public void setAppointment_ID(int appointment_ID) {
        this.appointment_id = appointment_ID;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets end.
     *
     * @return the end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets end.
     *
     * @param end the end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * Sets customer id.
     *
     * @param customer_id the customer id
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * Sets user id.
     *
     * @param user_id the user id
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Gets contact name.
     *
     * @return the contact name
     */
    public String getContact_name() {
        return contact_name;
    }

    /**
     * Sets contact name.
     *
     * @param contact_name the contact name
     */
    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }
}
