package AppSch.Controller;

import AppSch.DAO.CountryDAOImpl;
import AppSch.DAO.CustomerDAOImpl;
import AppSch.DAO.DivisionDAOImpl;
import AppSch.Model.Country;
import AppSch.Model.Customer;
import AppSch.Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static AppSch.DAO.CustomerQuery.insertCustomer;
import static AppSch.DAO.CustomerQuery.updateCustomer;
import static AppSch.DAO.DivisionDAOImpl.getAllDivisions;

/**
 * The type Update customer controller.
 */
public class UpdateCustomerController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @FXML    private ComboBox<Country> countryCombo;
    @FXML    private ComboBox<Division> divCombo;
    @FXML    private TextField phoneTxt;
    @FXML    private TextField postalTxt;
    @FXML    private TextField custTxt;
    @FXML    private TextField addressTxt;
    private int customer_id;


    /**
     * The Alldivs.
     */
    ObservableList<Division> alldivs = DivisionDAOImpl.getAllDivisions();

    /**
     * On country combo.
     *
     * @param event the event
     */
    @FXML
    void onCountryCombo(ActionEvent event) {
        String country = countryCombo.getSelectionModel().getSelectedItem().getCountry();
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        for (Division division : DivisionDAOImpl.getAllDivisions()) {
            if (country.equals(division.getCountry())) {
                divisions.add(division);
            }
        }
        divCombo.setItems(divisions);
    }

    /**
     * On action submit.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionSubmit(ActionEvent event) throws IOException {

        try {

            String customer_name = custTxt.getText();
            String address = addressTxt.getText();
            String postal_code = postalTxt.getText();
            String phone = phoneTxt.getText();
            String division = divCombo.getSelectionModel().getSelectedItem().getDivision();

            if (MainMenuController.confirmDialog("Submit?", "Submit this customer?"))
                if((custTxt.getText().trim().equals("") || addressTxt.getText().trim().equals("") || postalTxt.getText().trim().equals("") || phoneTxt.getText().trim().equals("")))
                    MainMenuController.errorDialog("Input Error", "Error in saving customer", "All fields must be entered");
                else if ((divCombo.getSelectionModel().getSelectedItem() == null || countryCombo.getSelectionModel().getSelectedItem() == null)) {
                    MainMenuController.errorDialog("Input Error", "Error in saving customer", "All boxes must be chosen");}
                else {
                    Customer customerToUpdate = new Customer(customer_id, customer_name, address, postal_code, phone, division);

                    updateCustomer(customerToUpdate);

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/SchedulerFX/CustomerMain.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }

        } catch (SQLException | IOException | NullPointerException ex) {
            MainMenuController.errorDialog("Input Error", "Error in saving customer", "Check fields for proper input");
            throw new RuntimeException(ex);
        }
    }

    /**
     * Send customer.
     *
     * @param customer the customer
     */
    public void sendCustomer(Customer customer) {

        // ObservableList<Customer> currentCustomer = FXCollections.observableArrayList();
        // currentCustomer.add(customer);
        customer_id = customer.getCustomer_id();
        custTxt.setText(customer.getCustomer_name());
        addressTxt.setText(customer.getAddress());
        postalTxt.setText(customer.getPostal_code());
        phoneTxt.setText(customer.getPhone());


        for (Division division : DivisionDAOImpl.getAllDivisions()) {

            if (customer.getDivision().equals(division.getDivision())) {
                divCombo.setItems(getAllDivisions(division.getCountry()));
                divCombo.setValue(division);

                for (Country country :CountryDAOImpl.getAllCountries()) {
                    if (division.getCountry().equals(country.getCountry())) {
                        countryCombo.setValue(country);

                    }
                }
            }
        }
        }


    /**
     * On action exit.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionExit(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/SchedulerFX/CustomerMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       // divCombo.setItems(getAllDivisions());
        countryCombo.setItems(CountryDAOImpl.getAllCountries());

         int customer_id;
    }
}
