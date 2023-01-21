package AppSch.Model;

/**
 * The Country class model.
 */
public class Country {

    private String country;
    private int country_id;

    /**
     * Instantiates a new Country.
     *
     * @param country    the country
     * @param country_id the country id
     */
    public Country(String country, int country_id) {
        this.country = country;
        this.country_id = country_id;
    }

    @Override
    public String toString() {
        return country;
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

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public int getCountry_id() {
        return country_id;
    }

    /**
     * Sets country id.
     *
     * @param country_id the country id
     */
    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }
}
