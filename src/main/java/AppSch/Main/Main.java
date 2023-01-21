package AppSch.Main;

import AppSch.DAO.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * This class creates a GUI-based appointment scheduling application.
 */
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

        DBConnection.openConnection();
        launch(args);
        DBConnection.closeConnection();


    }
}
