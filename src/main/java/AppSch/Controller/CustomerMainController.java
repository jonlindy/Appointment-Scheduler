package AppSch.Controller;

import AppSch.DAO.CustomerDAOImpl;
import AppSch.Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class contains the controller functions for the Customer Main screen
 */
public class CustomerMainController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @FXML    private TableColumn<Customer, String> addCol;
    @FXML    private TableColumn<Customer, String> custCol;
    @FXML    private TableView<Customer> custTableView;
    @FXML    private TableColumn<Customer, String> divCol;
    @FXML    private TableColumn<Customer, String> phoCol;
    @FXML    private TableColumn<Customer, String> postCol;

    /**
     * This method opens the Add customer screen
     *
     * @param event the add button is clicked
     * @throws IOException the io exception
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/SchedulerFX/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method deletes the selected customer from the TableView and MySQL DB
     * Checks if customer has existing appointments before deleting
     *
     * @param event the delete button clicked
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

        try {
            int customer_id = custTableView.getSelectionModel().getSelectedItem().getCustomer_id();
            String customer_name = custTableView.getSelectionModel().getSelectedItem().getCustomer_name();
            if (Customer.getCustomersAppt(customer_id).isEmpty()) {

                if (confirmDialog("Deleting Customer", "Are you sure? Delete?")) {
                    CustomerDAOImpl.deleteCustomer(customer_id);
                    custTableView.setItems(CustomerDAOImpl.getAllCustomers());
                    confirmDialog("Customer record deleted", customer_name + "'s record has been deleted.");
                }
            }
            else {
                CustomerMainController.errorDialog("Deletion Error", "Customer has existing appointments", "Please delete " + custTableView.getSelectionModel().getSelectedItem().getCustomer_name() + "'s appointments");
            }
        }
        catch (Exception e) {
            CustomerMainController.errorDialog("Selection Error", "No Customer Selected", "Select Customer to delete.");
        }
    }


    /**
     * This method opens the Update Customer screen and sends the selected customers data to next screen.
     *
     * @param event the update button is clicked
     * @throws IOException the io exception
     */
    @FXML
    void onActionUpdateCustomer(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/SchedulerFX/UpdateCustomer.fxml"));
        loader.load();

        UpdateCustomerController UcController = loader.getController();
        UcController.sendCustomer(custTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * this method opens the Main Menu screen
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
     * This method creates an Error dialog with a custom title, header, message
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
     * This method creates an confirm dialog with a custom title, content
     *
     * @param title   the title
     * @param content the content
     * @return the boolean
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
     * This method initializes the table columns and sets items to tables.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        custTableView.setItems(CustomerDAOImpl.getAllCustomers());
        custCol.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        addCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postCol.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
        phoCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divCol.setCellValueFactory(new PropertyValueFactory<>("division"));

    }
}
