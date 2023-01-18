package AppSch.Model;

import AppSch.DAO.AppointmentDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Contact.
 */
public class Contact {

    private int contact_id;
    private String contact_name;
    private String email;

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContact_id() {
        return contact_id;
    }

    /**
     * Gets contact name.
     *
     * @return the contact name
     */
    public String getContact_name() {
        return contact_name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Instantiates a new Contact.
     *
     * @param contact_id   the contact id
     * @param contact_name the contact name
     * @param email        the email
     */
    public Contact(int contact_id, String contact_name, String email) {
        this.contact_id = contact_id;
        this.contact_name = contact_name;
        this.email = email;
    }

    /**
     * Gets contacts appt.
     *
     * @param contact the contact
     * @return the contacts appt
     */
    public static ObservableList<Appointment> getContactsAppt(Contact contact) {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        for (Appointment appointment : AppointmentDAOImpl.getAllAppointments())
            if (contact.getContact_id() == appointment.getContact_id())
                apptList.add(appointment);
        return apptList;
    }
    @Override
    public String toString() {
        return contact_name;
    }
}
