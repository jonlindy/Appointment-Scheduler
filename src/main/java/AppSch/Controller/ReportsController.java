package AppSch.Controller;

import AppSch.DAO.AppointmentDAOImpl;
import AppSch.DAO.ContactDAOImpl;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * The class containing the control functions for the reports screen
 */
public class ReportsController implements Initializable {

    @FXML    private TableColumn<Appointment, Integer> apptIDCol;
    @FXML    private TableColumn<Appointment, Integer> custIDCol;
    @FXML    private TableColumn<Appointment, String> descCol;
    @FXML    private TableColumn<Appointment, LocalDateTime> endCol;
    @FXML    private TableColumn<Appointment, String> locCol;
    @FXML    private TableView<Appointment> scheduleTableView;
    @FXML    private TableColumn<Appointment, LocalDateTime> startCol;
    @FXML    private TableColumn<Appointment, String> titleCol;
    @FXML    private TableColumn<Appointment, String> typeCol;
    @FXML    private ComboBox<Contact> contactCombo;
    @FXML    private ComboBox<String> apptMonthCombo;
    @FXML    private ComboBox<String> apptTypeCombo;
    @FXML    private Text apptTxt;
    @FXML    private Text avgTxt;


    Stage stage;

    Parent scene;

    /**
     * This method opens to the Main Menu screen
     *
     * @param event the exit button clicked
     * @throws IOException the io exception
     */
    @FXML
    void onActionExit(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/SchedulerFX/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method populates the tableview based on the contact combo selection. displaying appointments for selected contact
     *
     * @param event the event
     */
    @FXML
    void onActionSelectContact(ActionEvent event) {
        scheduleTableView.setItems(Contact.getContactsAppt(contactCombo.getSelectionModel().getSelectedItem()));
    }

    /**
     * This method displays the number of appointments in a selected month and appointment type.
     *
     * @param event the event
     */
    @FXML
    void onActionFindAppts(ActionEvent event) {
        int apptCount = 0;
        for (Appointment appointment : AppointmentDAOImpl.getAllAppointments()) {

            if ((appointment.getType().equals(apptTypeCombo.getSelectionModel().getSelectedItem()))
                && (appointment.getStart().getMonth().toString().equals(apptMonthCombo.getSelectionModel().getSelectedItem()))) {
                ++apptCount;
            }
        }
        apptTxt.setText(String.valueOf(apptCount));
    }

    /**
     * This method calculates the average number of appointments per contact
     *
     * @param event the event
     */
    @FXML
    void onActionCalculateAvg(ActionEvent event) {
        double average;

        average = (
                AppointmentDAOImpl.getAllAppointments().size() / ContactDAOImpl.getAllContacts().size()
                );
        avgTxt.setText(String.valueOf(average));
    }

    /**
     * Initializes the columns of the tableview and populates the combo boxes
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactCombo.setItems(ContactDAOImpl.getAllContacts());
        apptTypeCombo.setItems(Appointment.getTypeList());
        apptMonthCombo.setItems(TimeWork.getMonthList());


    }
}
