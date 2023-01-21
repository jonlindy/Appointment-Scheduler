package AppSch.Controller;

import AppSch.DAO.AppointmentDAOImpl;
import AppSch.Model.Appointment;
import AppSch.Utility.TimeWork;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This method contains the controller functions for the Main Menu window.
 */
public class MainMenuController implements Initializable {


    Stage stage;

    Parent scene;

    /**
     * Opens the Appointment Main screen
     *
     * @param event the Appointment button is clicked
     * @throws IOException the io exception
     */
    @FXML
    void onActionApptMain(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/SchedulerFX/ApptMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Opens the customer main screen
     *
     * @param event the customer button clicked
     * @throws IOException the io exception
     */
    @FXML
    void onActionCustomerMain(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/SchedulerFX/CustomerMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Opens the reports screen
     *
     * @param event the reports button clicked
     * @throws IOException the io exception
     */
    @FXML
    void onActionReports(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/SchedulerFX/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Creates an error dialog with custom title, header, message
     *
     * @param title   the title
     * @param header  the header
     * @param message the message
     */
    static void errorDialog(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This method opens a confirm window with the passed in title and content.
     *
     * @param title   the alert title
     * @param content the alert content
     * @return returns true if OK button is pressed. or else it returns false.
     */
    static boolean confirmDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * this method opens an information dialog with custom title, header, content
     *
     * @param title   the title
     * @param header  the header
     * @param content the content
     */
    static void upcomingApptDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * this method checks if any appointments are scheduled within 15 minutes of current time and sends alert notifying
     * If no appointments within 15 min, sends info alert notifying
     */
    static void apptAlert() {
        boolean upcoming = false;
        for (Appointment appointment : AppointmentDAOImpl.getAllAppointments()) {
            System.out.println(TimeWork.getTimeDifference(appointment.getStart()));
            if ((TimeWork.getTimeDifference(appointment.getStart()) <= 15) && (TimeWork.getTimeDifference(appointment.getStart()) > 0)) {
                upcoming = true;
                MainMenuController.upcomingApptDialog("Schedule Alert", "Upcoming appointment", "Appointment with ID: " + appointment.getAppointment_ID() + ", Date: " + appointment.getStart().toLocalDate() + ", and Time: " + appointment.getStart().toLocalTime() + " is starting in " + TimeWork.getTimeDifference(appointment.getStart()) + " minute(s)!");            }
        }
        if (upcoming == false)
            MainMenuController.upcomingApptDialog("Schedule Alert", "No upcoming appointments", "There are no appointments starting in the next 15 minutes.");


    }




    /**
     * initially calls the appointment alert method

     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptAlert();

    }
}
