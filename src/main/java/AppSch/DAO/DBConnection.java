package AppSch.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class contains methods to establish a connection to the MySql Database.
 */
public abstract class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost:3306/client_schedule?connectionTimeZone=SERVER";

    private static final String databaseName="client_schedule";
    private static final String DB_URL="jdbc:mysql://localhost:3306/client_schedule?connectionTimeZone=SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String username="sqlUser";
    private static final String password="Passw0rd!";

    public static Connection connection;

    /**
     * This method opens the database connection.
     */
    public static void openConnection(){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(DB_URL, username, password);
            System.out.println("Connection Successful");
        }
        catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        }


    /**
     * This method closes the database connection.
     */
    public static void closeConnection() {
        try {
            connection.close();
        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


    }
}
