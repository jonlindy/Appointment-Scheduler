package AppSch.Utility;

import AppSch.DAO.AppointmentDAOImpl;
import AppSch.Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * The class contains methods that deal with Time classes and functions.
 */
public class TimeWork {

    /**
     * This method gives the time difference between now and a given time.
     *
     * @param start the start
     * @return the time difference
     */
    public static int getTimeDifference(LocalDateTime start) {

        LocalTime startTime = start.toLocalTime();
        LocalTime currentTime = LocalTime.now();
        long timeDifference = ChronoUnit.MINUTES.between(currentTime, startTime);
        int timeDiff = (int) timeDifference;
        return timeDiff;
    }

    /**
     * This method checks if a given appointment's time overlaps with any existing appointment's time
     *
     * @param appointment_id the appointment id
     * @param A_st           the a st
     * @param A_end          the a end
     * @return the boolean
     */
    public static boolean apptOverlapCheck(int appointment_id, LocalDateTime A_st, LocalDateTime A_end) {

        boolean overlap = false;
        for (Appointment appointment : AppointmentDAOImpl.getAllAppointments()) {
            LocalDateTime B_st = appointment.getStart();
            LocalDateTime B_end = appointment.getEnd();

            if (appointment_id == appointment.getAppointment_ID())

                continue;

            else if (
                    ((A_st.isBefore(B_st) || A_st.isEqual(B_st)) && (A_end.isAfter(B_st) || A_end.isEqual(B_end)))
                            || (((A_st.isBefore(B_st) && (A_end.isAfter(B_end)))))
                            || (A_st.isAfter(B_st) && (A_end.isBefore(B_end) || A_end.isEqual(B_end)))
                            || (A_st.isAfter(B_st) && A_st.isBefore(B_end) && (A_end.isAfter(B_end)))
            ) {
                overlap = true;
                System.out.println("Overlap");
                System.out.println(overlap);
                break;
            }


        }


        return overlap;

    }

    /**
     * This method checks if a new appointment's time overlaps with any existing appointment's time
     *
     * @param A_st  the appointment start time
     * @param A_end the appointment end time
     * @return the boolean
     */
    public static boolean newApptOverlapCheck(LocalDateTime A_st, LocalDateTime A_end) {

        boolean overlap = false;
        for (Appointment appointment : AppointmentDAOImpl.getAllAppointments()) {
            LocalDateTime B_st = appointment.getStart();
            LocalDateTime B_end = appointment.getEnd();

            if (
                    ((A_st.isBefore(B_st) || A_st.isEqual(B_st)) && (A_end.isAfter(B_st) || A_end.isEqual(B_end)))
                            || (((A_st.isBefore(B_st) && (A_end.isAfter(B_end)))))
                            || (A_st.isAfter(B_st) && (A_end.isBefore(B_end) || A_end.isEqual(B_end)))
                            || (A_st.isAfter(B_st) && A_st.isBefore(B_end) && (A_end.isAfter(B_end)))
            ) {
                overlap = true;
                System.out.println("Overlap");
                System.out.println(overlap);
                break;
            }
        }
        return overlap;
    }

    /**
     * This method creates a list of the calender months.
     *
     * @return the month list
     */
    public static ObservableList<String> getMonthList() {
        ObservableList<String> monthList = FXCollections.observableArrayList();

        monthList.add("JANUARY");
        monthList.add("FEBRUARY");
        monthList.add("MARCH");
        monthList.add("APRIL");
        monthList.add("MAY");
        monthList.add("JUNE");
        monthList.add("JULY");
        monthList.add("AUGUST");
        monthList.add("SEPTEMBER");
        monthList.add("OCTOBER");
        monthList.add("NOVEMBER");
        monthList.add("DECEMBER");

        return monthList;
    }


    /**
     * Gets est zdt.
     *
     * @return the est zdt
     */
//    public static ZonedDateTime getEST_ZDT() {
//        LocalDate estDate = LocalDate.now();
//        LocalTime estTime = LocalTime.now();
//        ZoneId estZoneId = ZoneId.of("America/New_York");
//        ZonedDateTime est_ZDT = ZonedDateTime.of(estDate, estTime, estZoneId);
//        return est_ZDT;
//    }

    /**
     * This method converts a given LocalDateTime to UTC LocalDateTime
     *
     * @param time the time
     * @return the local date time
     */
    public static LocalDateTime convertToUtc(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

    /**
     * This method converts a given LocalDateTime to local LocalDateTime
     *
     * @param time the time
     * @return the local date time
     */
    public static LocalDateTime convertToLocal(LocalDateTime time) {
        return time.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * This method converts a given LocalDateTime to EST LocalDateTime
     *
     * @param time the time
     * @return the local date time
     */
    public static LocalDateTime convertToEST(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
    }

    /**
     * This method checks if a given start and end time is in business hours.
     *
     * @param start the start
     * @param end   the end
     * @return the boolean
     */
    public static boolean inBusinessHours(LocalDateTime start, LocalDateTime end) {


        LocalTime s_T = start.toLocalTime();
        LocalTime e_T = end.toLocalTime();


        if (s_T.isAfter(LocalTime.parse("07:59:59")) && s_T.isBefore(LocalTime.parse("22:01:00"))
                && e_T.isAfter(LocalTime.parse("07:59:59")) && e_T.isBefore(LocalTime.parse("22:01:00"))) {
            System.out.println("Appt is in business hours");
            return true;

        } else {
            return false;
        }
    }
}




