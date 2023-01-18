package AppSch.Model;

/**
 * The type Division.
 */
public class Division {

    private String division;
    private String country;
    private int division_id;

    /**
     * Instantiates a new Division.
     *
     * @param division    the division
     * @param country     the country
     * @param division_id the division id
     */
    public Division(String division, String country, int division_id) {
        this.division = division;
        this.country = country;
        this.division_id = division_id;
    }

    @Override
    public String toString() {
        return division;
    }

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public int getDivision_id() {
        return division_id;
    }

    /**
     * Sets division id.
     *
     * @param division_id the division id
     */
    public void setDivision_id(int division_id) {
        this.division_id = division_id;
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

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
