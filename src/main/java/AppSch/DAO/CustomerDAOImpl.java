package AppSch.DAO;

import AppSch.Model.Customer;
import AppSch.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The type Customer dao.
 */
public class CustomerDAOImpl {

    /**
     * Get all customers observable list.
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
     * Add customer.
     *
     * @param Customer_Name the customer name
     * @param Address       the address
     * @param Postal_Code   the postal code
     * @param Phone         the phone
     * @param Division_ID   the division id
     */
    public static void addCustomer(String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID) {

        try {

            String sql = "INSERT INTO customers VALUES(NULL,?,?,?,?,?)";

            PreparedStatement ps = DBConnection.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, Customer_Name);
            ps.setString(1, Address);
            ps.setString(1, Postal_Code);
            ps.setString(1, Phone);
            ps.setInt(1, Division_ID);

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int Customer_ID = rs.getInt(1);


        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets all customer i ds.
     *
     * @return the all customer i ds
     */
    public static ObservableList<Integer> getAllCustomerIDs() {
        ObservableList<Integer> customerIDlist = FXCollections.observableArrayList();
        for (Customer customer : getAllCustomers()) {
            customerIDlist.add(customer.getCustomer_id());
        }
        return customerIDlist;
    }
}
