package AppSch.Controller;

import AppSch.DAO.AppointmentDAOImpl;
import AppSch.Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

import static AppSch.Controller.MainMenuController.confirmDialog;

/**
 * This class contains the controller functions for the Appointment Main screen
 */
public class ApptMainController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @FXML    private TableColumn<Appointment, Integer> apptIDCol;
    @FXML    private TableColumn<Appointment, String> conCol;
    @FXML    private TableColumn<Appointment, Integer> custIDCol;
    @FXML    private TableColumn<Appointment, String> descCol;
    @FXML    private TableColumn<Appointment, LocalDateTime> endCol;
    @FXML    private TableColumn<Appointment, String> locCol;
    @FXML    private TableColumn<Appointment, LocalDateTime> startCol;
    @FXML    private TableColumn<Appointment, String> titleCol;
    @FXML    private TableColumn<Appointment, String> typeCol;
    @FXML    private TableColumn<Appointment, Integer> userIDCol;
    @FXML    private TableView<Appointment> apptTableView;
    @FXML    private RadioButton monthApptRB;
    @FXML    private RadioButton allApptRB;
    @FXML    private RadioButton weekApptRB;
    @FXML    private Text apptTxt = new Text(10, 50, "This is apptText");

    /**
     * This method opens the Add Appointment screen
     *
     * @param event Add button clicked
     * @throws IOException the io exception
     */
    @FXML
    void onActionAddAppt(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/SchedulerFX/AddAppt.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method deletes the selected appointment from the Tableview and MySQL DB.
     *
     * @param event the delete button is clicked
     */
    @FXML
    void onActionDeleteAppt(ActionEvent event) {

        try {
            int appointment_id = apptTableView.getSelectionModel().getSelectedItem().getAppointment_ID();
            String type = apptTableView.getSelectionModel().getSelectedItem().getType();


            //MainScreenController.errorDialog("Error deleting Product", "Product has associated Parts", "Remove parts from Product before deleting.");

            if (confirmDialog("Deleting Appointment", "Are you sure? Delete?")) {
                AppointmentDAOImpl.deleteAppointment(appointment_id);
                apptTableView.setItems(AppointmentDAOImpl.getAllAppointments());
                confirmDialog("Appointment deleted", "Appointment type "+ type +" with Appointment ID of " + appointment_id + " has been deleted.");
            }

        }
        catch (Exception e) {
            CustomerMainController.errorDialog("Selection Error", "No Appointment Selected", "Select Appointment to delete.");
        }
    }

    /**
     * This method opens the Update Appointment screen and sends the selected appointments data to next screen.
     *
     * @param event the update button is clicked
     * @throws IOException the io exception
     */
    @FXML
    void onActionUpdateAppt(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/SchedulerFX/UpdateAppt.fxml"));
        loader.load();

        UpdateApptController UaController = loader.getController();
        UaController.sendAppointment(apptTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();


    }

    /**
     * This method sets the TableView items depending on the radio button selected
     *
     * @param event radio button clicked
     */
    @FXML
    void onActionApptRadio(ActionEvent event) {
        if (allApptRB.isSelected()) {
            apptTableView.setItems(AppointmentDAOImpl.getAllAppointments());
        }
        else if (monthApptRB.isSelected()) {
            apptTableView.setItems(Appointment.getMonthAppointments());
        }
        else if (weekApptRB.isSelected()) {
            apptTableView.setItems(Appointment.getWeekAppointments());
        }
    }

    /**
     * This method returns to the Main Menu
     *
     * @param event the exit button is clicked
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
     * This method initializes the table columns, sets items to tables.
     * LAMBDA: This initialize method implements a lambda expression using an event listener method to execute code when a selection is made.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        apptTableView.setItems(AppointmentDAOImpl.getAllAppointments());
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        conCol.setCellValueFactory(new PropertyValueFactory<>("contact_name"));
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        apptTxt.setText("No Appointment Selected");
        apptTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        apptTxt.setText("Appointment selected with a Start time of: " + newSelection.getStart().toLocalTime() + '\n' + " in Timezone: " + newSelection.getStart().atZone(ZoneId.systemDefault()).getZone());
                    }
                }
        );

    }
}
