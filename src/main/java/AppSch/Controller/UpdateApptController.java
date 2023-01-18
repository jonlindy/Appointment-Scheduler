package AppSch.Controller;

import AppSch.DAO.ContactDAOImpl;
import AppSch.DAO.CustomerDAOImpl;
import AppSch.DAO.UserDAOImpl;
import AppSch.Model.Appointment;
import AppSch.Model.Contact;
import AppSch.Utility.TimeWork;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static AppSch.DAO.AppointmentDAOImpl.insertAppointment;
import static AppSch.DAO.AppointmentDAOImpl.updateAppointment;

/**
 * The class for the control functions of the Update Appointment screen
 */
public class UpdateApptController implements Initializable {


    Stage stage;

    Parent scene;

    @FXML    private TextField apptIDTxt;
    @FXML    private ComboBox<Contact> contactCombo;
    @FXML    private ComboBox<Integer> custIDcombo;
    @FXML    private TextField descTxt;
    @FXML    private DatePicker endDatePicker;
    @FXML    private ComboBox<LocalTime> endTimeCombo;
    @FXML    private TextField locationTxt;
    @FXML    private DatePicker startDatePicker;
    @FXML    private ComboBox<LocalTime> startTimeCombo;
    @FXML    private TextField titleTxt;
    @FXML    private ComboBox<String> typeCombo;
    @FXML    private ComboBox<Integer> userIDcombo;

    /**
     * This method exits to the Appointment Main screen
     *
     * @param event the exit button clicked
     * @throws IOException the io exception
     */
    @FXML
    void onActionExit(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/SchedulerFX/ApptMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method submits the appointment, checking for completed fields, overlapping appointments, appointment time within business hours
     *If okay, updates appointment in the DB.
     * @param event the submit button clicked
     */
    @FXML
    void onActionSaveAppt(ActionEvent event) {

        try {

            int appointment_id = Integer.parseInt(apptIDTxt.getText());
            String title = titleTxt.getText();
            String description = descTxt.getText();
            String location = locationTxt.getText();
            String type = typeCombo.getSelectionModel().getSelectedItem();
            int customer_id = custIDcombo.getSelectionModel().getSelectedItem();
            int user_id = userIDcombo.getSelectionModel().getSelectedItem();
            int contact_id = contactCombo.getSelectionModel().getSelectedItem().getContact_id();
            String contact_name = contactCombo.getSelectionModel().getSelectedItem().getContact_name();

            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            LocalDateTime start = LocalDateTime.of(startDate, startTimeCombo.getValue());
            LocalDateTime end = LocalDateTime.of(endDate, endTimeCombo.getValue());
            LocalDateTime startEST = TimeWork.convertToEST(start);
            LocalDateTime endEST = TimeWork.convertToEST(end);

            if (MainMenuController.confirmDialog("Submit?", "Submit this appointment?"))
                if (start.isAfter(end))
                    MainMenuController.errorDialog("Time Error", "Error in selecting date or time", "Appointment start time must be before end time");
                else if((titleTxt.getText().trim().equals("") || descTxt.getText().trim().equals("") || locationTxt.getText().trim().equals("")))
                    MainMenuController.errorDialog("Input Error", "Error in saving appointment", "All fields must be entered");
                else if ((typeCombo.getSelectionModel().getSelectedItem().isEmpty() || custIDcombo.getSelectionModel().getSelectedItem() == null || userIDcombo.getSelectionModel().getSelectedItem() == null ||
                            contactCombo.getSelectionModel().getSelectedItem() == null )) {
                            MainMenuController.errorDialog("Input Error", "Error in saving appointment", "All boxes must be chosen");}
                else if (TimeWork.apptOverlapCheck(appointment_id, start, end))
                    MainMenuController.errorDialog("Input Error", "Error in saving appointment", "Overlapping appointments. Please choose another time");
                else if(!TimeWork.inBusinessHours(startEST, endEST))
                    MainMenuController.errorDialog("Input Error", "Error in saving appointment", "Appointment time must fall within business hours of 8am - 10pm EST");

                else {
                    Appointment apptToUpdate = new Appointment(appointment_id,title, description, location, type, start, end, customer_id, user_id, contact_id, contact_name);

                    updateAppointment(apptToUpdate);
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/SchedulerFX/ApptMain.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }



        } catch (SQLException | IOException | NullPointerException ex) {
            MainMenuController.errorDialog("Input Error", "Error in saving appointment", "Check fields for proper input");
            throw new RuntimeException(ex);

        }
    }

    /**
     * This method sends/receives the selected appointment object from the previous screen and sets the fields with the existing data.
     *
     * @param appointment the appointment
     */
    public void sendAppointment(Appointment appointment) {

        Contact contactToSet = null;
        for (Contact contact : ContactDAOImpl.getAllContacts()) {
            if (contact.getContact_id() == (appointment.getContact_id())) {
                contactToSet = contact;
                break;
            }
        }

        apptIDTxt.setText(String.valueOf(appointment.getAppointment_ID()));
        titleTxt.setText(appointment.getTitle());
        descTxt.setText(appointment.getDescription());
        locationTxt.setText(appointment.getLocation());
        typeCombo.setValue(appointment.getType());
        custIDcombo.setValue(appointment.getCustomer_id());
        userIDcombo.setValue(appointment.getUser_id());
        contactCombo.setValue(contactToSet);
        startDatePicker.setValue(appointment.getStart().toLocalDate());
        endDatePicker.setValue(appointment.getEnd().toLocalDate());
        startTimeCombo.setValue(appointment.getStart().toLocalTime());
        endTimeCombo.setValue(appointment.getEnd().toLocalTime());

        System.out.println("Converted to local: "+TimeWork.convertToLocal(appointment.getStart()));
        System.out.println("Straight from DB: "+appointment.getStart());
    }

    /**
     * Populates the combo boxes
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userIDcombo.setItems(UserDAOImpl.getAllUserIDs());
        contactCombo.setItems(ContactDAOImpl.getAllContacts());
        custIDcombo.setItems(CustomerDAOImpl.getAllCustomerIDs());
        typeCombo.setItems(Appointment.getTypeList());
        startTimeCombo.setItems(Appointment.getTimeList());
        endTimeCombo.setItems(Appointment.getTimeList());
    }
}


