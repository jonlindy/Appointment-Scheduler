package AppSch.Main;

import AppSch.DAO.CustomerQuery;
import AppSch.DAO.DBConnection;
import AppSch.Model.Customer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/SchedulerFX/Login.fxml"));
        Scene scene = new Scene(root, 600, 400 );
        primaryStage.setTitle("Appointment Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) throws SQLException {

        //ResourceBundle rb = ResourceBundle.getBundle("AppSch/Utility/Lang", Locale.getDefault());
        // ZoneId.getAvailableZoneIds().stream().filter(c -> c.contains("America")).forEach(System.out::println);

        LocalTime now = LocalTime.now();
        System.out.println(now);
        ZoneId userZone = ZoneId.systemDefault();
        //if(Locale.getDefault().getLanguage().equals("fr"))
            //.out.println(rb.getString("hello"));
        // Customer Jon = new Customer(4, "Jonny B", "244 W Cool Ave", "87299", "480-444-2921", 103);
        DBConnection.openConnection();
        launch(args);

        //int rowsAffected = CustomerQuery.deleteCustomer(2);
        CustomerQuery.selectCustomer();
        /* if (rowsAffected > 0)
            System.out.println("Delete Success");
        else
            System.out.println("Insert Failed");
        */



        DBConnection.closeConnection();


    }
}
