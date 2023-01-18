package AppSch.Model;

/**
 * The type User.
 */
public class User {

    private int user_id;
    private String user_Name;
    private String password;

    /**
     * Instantiates a new User.
     *
     * @param user_id   the user id
     * @param user_Name the user name
     * @param password  the password
     */
    public User(int user_id, String user_Name, String password) {
        this.user_id = user_id;
        this.user_Name = user_Name;
        this.password = password;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * Sets user id.
     *
     * @param user_id the user id
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUser_Name() {
        return user_Name;
    }

    /**
     * Sets user name.
     *
     * @param user_Name the user name
     */
    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
