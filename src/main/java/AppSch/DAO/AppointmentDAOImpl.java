package AppSch.DAO;

import AppSch.Model.Appointment;
import AppSch.Model.Customer;
import AppSch.Model.Division;
import AppSch.Utility.TimeWork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class contains the methods for Appointment Data Access Object implementations
 */
public class AppointmentDAOImpl {

    /**
     * This method selects all appointments from the DB and puts them in an observable list.
     *
     * @return the observable list (apptList)
     */
    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();

        try {

            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, contacts.Contact_ID, contacts.Contact_Name FROM appointments, contacts WHERE appointments.Contact_ID = contacts.Contact_ID";

            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointment_id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startUTC = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime start = TimeWork.convertToLocal(startUTC);
                LocalDateTime endUTC = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime end = TimeWork.convertToLocal(endUTC);
                int customer_id = rs.getInt("Customer_ID");
                int user_id = rs.getInt("User_ID");
                int contact_id = rs.getInt("contacts.Contact_ID");
                String contact_name = rs.getString("contacts.Contact_Name");



                Appointment a = new Appointment(appointment_id, title, description, location, type, start, end, customer_id, user_id, contact_id, contact_name);

                apptList.add(a);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return apptList;
    }

    /**
     * This method inserts a new appointment into the DB
     *
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     * @param start       the start
     * @param end         the end
     * @param customer_id the customer id
     * @param user_id     the user id
     * @param contact_id  the contact id
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int insertAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customer_id, int user_id, int contact_id) throws SQLException {
        Timestamp startTs = Timestamp.valueOf(TimeWork.convertToUtc(start));
        Timestamp endTs = Timestamp.valueOf(TimeWork.convertToUtc(end));

        System.out.println("Appt Time Local: " + Timestamp.valueOf(start));
        System.out.println("Appt Time UTC: " + startTs);

        String sql = "INSERT INTO APPOINTMENTS VALUES(NULL,?,?,?,?,?,?,NULL,NULL,NULL,NULL,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startTs);
        ps.setTimestamp(6, endTs);
        ps.setInt(7, customer_id);
        ps.setInt(8, user_id);
        ps.setInt(9, contact_id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This method deletes an appointment from the DB given an appointment id.
     *
     * @param appointment_id the appointment id
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int deleteAppointment(int appointment_id) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, appointment_id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This method updates an existing appointment in the DB.
     *
     * @param appointment the appointment to update
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int updateAppointment(Appointment appointment) throws SQLException {
        Timestamp startTs = Timestamp.valueOf(TimeWork.convertToUtc(appointment.getStart()));
        Timestamp endTs = Timestamp.valueOf(TimeWork.convertToUtc(appointment.getEnd()));
        //System.out.println("Appt Start in UTC: "+startTs);

        String sql = "UPDATE appointments SET " +
                "Title = ?, " +
                "Description = ?, " +
                "Location = ?, " +
                "Type = ?, " +
                "Start = ?, " +
                "End = ?, " +
                "Customer_ID = ?, " +
                "User_ID = ?, " +
                "Contact_ID = ? " +
                "WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, startTs);
        ps.setTimestamp(6, endTs);
        ps.setInt(7, appointment.getCustomer_id());
        ps.setInt(8, appointment.getUser_id());
        ps.setInt(9, appointment.getContact_id());
        ps.setInt(10, appointment.getAppointment_ID());

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
