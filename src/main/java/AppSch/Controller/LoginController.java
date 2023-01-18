package AppSch.Controller;

import AppSch.DAO.UserDAOImpl;
import AppSch.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This method contains the controller functions for the Login window.
 */
public class LoginController implements Initializable {


    Stage stage;

    Parent scene;

    @FXML    private TextField passwordTxt;
    @FXML    private TextField usernameTxt;
    @FXML    private Text timeZoneTxt;
    @FXML    private Button enterTxt;
    @FXML    private Label pwTxt;
    @FXML    private Text titleTxt;
    @FXML    private Text tzTxt;
    @FXML    private Label userTxt;

    /**
     * This method checks if the username and password are a match in the DB
     * If match, Main menu screen opens. This method also switches between English and French depending on the users Language setting.
     *
     * @param event the enter button is clicked
     * @throws IOException the io exception
     */
    @FXML
    void onActionEnter(ActionEvent event) throws IOException {

        FileWriter fWriter = new FileWriter("login_activity.txt", true);
        PrintWriter outputFile = new PrintWriter(fWriter);

        ResourceBundle rb = ResourceBundle.getBundle("Language/Lang", Locale.getDefault());



        for (User user : UserDAOImpl.getAllUsers()) {
            if (user.getUser_Name().equals(usernameTxt.getText()) && user.getPassword().equals(passwordTxt.getText())) {

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/SchedulerFX/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

                outputFile.print("User: " + user.getUser_Name() + " logged in successfully at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");

                break;
            }
            else {
                CustomerMainController.errorDialog(rb.getString("error"), rb.getString("invalid"), rb.getString("please_enter"));
                outputFile.print("User: " + usernameTxt.getText() + " failed to login at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
                break;
            }
        }
        outputFile.close();



    }
    /**
     * This method sets the ZoneId and locale to systemdefault and sets GUI text to users respective language setting.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ZoneId userZone = ZoneId.systemDefault();
        Locale locale = Locale.getDefault();
        Locale.setDefault(locale);

        rb = ResourceBundle.getBundle("Language/Lang", Locale.getDefault());
        userTxt.setText(rb.getString("username"));
        pwTxt.setText(rb.getString("password"));
        titleTxt.setText(rb.getString("title"));
        enterTxt.setText(rb.getString("enter"));
        tzTxt.setText(rb.getString("timezone"));

        timeZoneTxt.setText(String.valueOf(userZone));
    }
}
