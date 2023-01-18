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
 * The type Reports controller.
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

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    /**
     * On action exit.
     *
     * @param event the event
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
     * On action select contact.
     *
     * @param event the event
     */
    @FXML
    void onActionSelectContact(ActionEvent event) {
        scheduleTableView.setItems(Contact.getContactsAppt(contactCombo.getSelectionModel().getSelectedItem()));
    }

    /**
     * On action find appts.
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
     * On action calculate avg.
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
