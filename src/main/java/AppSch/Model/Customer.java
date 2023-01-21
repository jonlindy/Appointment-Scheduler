package AppSch.Model;

import AppSch.DAO.AppointmentDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Customer class model
 */
public class Customer {

    private int customer_id;
    private String customer_name;
    private String address;
    private String postal_code;
    private String phone;
    private String division;

    /**
     * Instantiates a new Customer.
     *
     * @param customer_id   the customer id
     * @param customer_name the customer name
     * @param address       the address
     * @param postal_code   the postal code
     * @param phone         the phone
     * @param division      the division
     */
    public Customer(int customer_id, String customer_name, String address, String postal_code, String phone, String division) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.division = division;
    }

    /**
     * Gets customers appt.
     *
     * @param customer_id the customer id
     * @return the customers appt
     */
    public static ObservableList<Appointment> getCustomersAppt(int customer_id) {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        for (Appointment appointment : AppointmentDAOImpl.getAllAppointments())
            if (customer_id == appointment.getCustomer_id())
                apptList.add(appointment);

        return apptList;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * Sets customer id.
     *
     * @param customer_id the customer id
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * Gets customer name.
     *
     * @return the customer name
     */
    public String getCustomer_name() {
        return customer_name;
    }

    /**
     * Sets customer name.
     *
     * @param customer_name the customer name
     */
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostal_code() {
        return postal_code;
    }

    /**
     * Sets postal code.
     *
     * @param postal_code the postal code
     */
    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets division.
     *
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets division.
     *
     * @param division the division
     */
    public void setDivision(String division) {
        this.division = division;
    }
}
