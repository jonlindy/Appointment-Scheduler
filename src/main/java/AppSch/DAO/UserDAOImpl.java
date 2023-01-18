package AppSch.DAO;

import AppSch.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type User dao.
 */
public class UserDAOImpl {

    /**
     * Gets all users.
     *
     * @return the all users
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {

            String sql = "SELECT User_ID, User_Name, Password FROM users";

            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int user_id = rs.getInt("User_ID");
                String user_Name = rs.getString("User_Name");
                String password = rs.getString("Password");

                User u = new User(user_id, user_Name, password);

                userList.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    /**
     * Gets all user i ds.
     *
     * @return the all user i ds
     */
    public static ObservableList<Integer> getAllUserIDs() {
        ObservableList<Integer> userIDlist = FXCollections.observableArrayList();
        for (User user : getAllUsers()) {
            userIDlist.add(user.getUser_id());
        }
        return userIDlist;
    }
}
