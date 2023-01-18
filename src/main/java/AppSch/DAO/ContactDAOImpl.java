package AppSch.DAO;

import AppSch.Model.Contact;
import AppSch.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Contact dao.
 */
public class ContactDAOImpl {

    /**
     * Gets all contacts.
     *
     * @return the all contacts
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        try {

            String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts";

            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contact_id = rs.getInt("Contact_ID");
                String contact_name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact c = new Contact(contact_id, contact_name, email);

                contactList.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contactList;
    }
}
