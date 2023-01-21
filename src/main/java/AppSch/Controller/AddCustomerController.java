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


import static AppSch.DAO.DivisionDAOImpl.*;

/**
 * This class contains the controller functions for the Add Customer window.
 */
public class AddCustomerController implements Initializable {


    Stage stage;

    Parent scene;

    @FXML    private ComboBox<Country> countryCombo;
    @FXML    private ComboBox<Division> divCombo;
    @FXML    private TextField phoneTxt;
    @FXML    private TextField postalTxt;
    @FXML    private TextField custTxt;
    @FXML    private TextField addressTxt;
    @FXML    private TextField custIDTxt;



    ObservableList<Division> alldivs = DivisionDAOImpl.getAllDivisions();

    /**
     * This method sets the Division combo box depending on the country selected.
     *
     * @param event Making a selection in the country combo box
     */
    @FXML    void onCountryCombo(ActionEvent event) {
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
     * This method inserts the customer to the "customers" table in the MySql DB. And returns to Customer Main screen.
     * Checks for correct fields and combo boxes selected. Then returns to Customer Main screen
     *
     * @param event the submit button clicked
     * @throws IOException the io exception
     */
    @FXML
    void onActionSubmit(ActionEvent event) throws IOException {

        try {

            String customer_name = custTxt.getText();
            String address = addressTxt.getText();
            String postal_code = postalTxt.getText();
            String phone = phoneTxt.getText();
            int division_id = divCombo.getSelectionModel().getSelectedItem().getDivision_id();

            if (MainMenuController.confirmDialog("Submit?", "Submit this customer?"))
                if((custTxt.getText().trim().equals("") || addressTxt.getText().trim().equals("") || postalTxt.getText().trim().equals("") || phoneTxt.getText().trim().equals("")))
                    MainMenuController.errorDialog("Input Error", "Error in saving customer", "All fields must be entered");
                else if ((divCombo.getSelectionModel().getSelectedItem() == null || countryCombo.getSelectionModel().getSelectedItem() == null)) {
                    MainMenuController.errorDialog("Input Error", "Error in saving customer", "All boxes must be chosen");}
                else {
                    CustomerDAOImpl.insertCustomer(customer_name, address, postal_code, phone, division_id);

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
     * This method returns to the Customer Main screen
     *
     * @param event the exit button is clicked
     * @throws IOException the io exception
     */
    @FXML
    void onActionExit(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/SchedulerFX/CustomerMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method sets the country combo box with item list and sets the country combo Prompt text
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //divCombo.setItems(getAllDivisions());
        //divCombo.setPromptText("Select division");
        countryCombo.setItems(CountryDAOImpl.getAllCountries());
        countryCombo.setPromptText("Select country");
        System.out.println("Git please commit");

        //countryCombo.getSelectionModel().select(1);
        //divCombo.getSelectionModel().select(2);


    }
}
