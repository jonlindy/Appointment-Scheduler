module com.example.scheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports AppSch.Main;
    opens AppSch.Main to javafx.fxml;
    exports AppSch.Controller;
    opens AppSch.Controller to javafx.fxml;
    exports AppSch.Model;
    opens AppSch.Model to javafx.fxml;

}