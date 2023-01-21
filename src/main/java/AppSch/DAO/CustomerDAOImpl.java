package AppSch.DAO;

import AppSch.Model.Customer;
import AppSch.Model.Division;
import AppSch.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class contains the methods for Customer Data Access Object implementations
 */
public class CustomerDAOImpl {

    /**
     * This method selects all customers from the DB and puts them in an observable list.
     *
     * @return the observable list
     */
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> custList = FXCollections.observableArrayList();

        try {

            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, first_level_divisions.Division FROM customers, first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID";

            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int customer_id = rs.getInt("Customer_ID");
                String customer_name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal_code = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                String division = rs.getString("Division");

                Customer c = new Customer(customer_id, customer_name, address, postal_code, phone, division);

                custList.add(c);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return custList;
    }

    /**
     * This method inserts a new customer into the DB.
     *
     * @param customer_name the customer name
     * @param address       the address
     * @param postal_code   the postal code
     * @param phone         the phone
     * @param division_id   the division id
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int insertCustomer(String customer_name, String address, String postal_code, String phone, int division_id ) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS VALUES(NULL,?,?,?,?,NULL,NULL,NULL,NULL,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, customer_name);
        ps.setString(2, postal_code);
        ps.setString(3, postal_code);
        ps.setString(4, phone);
        ps.setInt(5, division_id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This method updates an existing customer in the DB.
     *
     * @param customer the customer
     * @return the int
     * @throws SQLException the sql exception
     */
// Customer(int customer_id, String customer_name, String address, String postal_code, String phone, int division_id)
    public static int updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET " +
                "Customer_Name = ?, " +
                "Address = ?, " +
                "Postal_Code = ?, " +
                "Phone = ?, " +
                "Division_ID = ? " +
                "WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, customer.getCustomer_name());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostal_code());
        ps.setString(4, customer.getPhone());
        for (Division division : DivisionDAOImpl.getAllDivisions()) {
            if (customer.getDivision().equals(division.getDivision())) {
                ps.setInt(5, division.getDivision_id());
                break;
            }
        }
        ps.setInt(6, customer.getCustomer_id());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This method deletes a customer in the DB by customer id.
     *
     * @param customer_id the customer id
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int deleteCustomer(int customer_id) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, customer_id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**
     * This method puts all customer id's into an observable list. Used for combo box
     *
     * @return customer id list
     */
    public static ObservableList<Integer> getAllCustomerIDs() {
        ObservableList<Integer> customerIDlist = FXCollections.observableArrayList();
        for (Customer customer : getAllCustomers()) {
            customerIDlist.add(customer.getCustomer_id());
        }
        return customerIDlist;
    }
}
