package AppSch.DAO;

import AppSch.Model.Country;
import AppSch.Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains the methods for Country Data Access Object implementations
 */
public class CountryDAOImpl {

    /**
     * This method selects all countries from the DB and puts them in an observable list.
     *
     * @return the observable list
     */
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try {

            String sql = "SELECT Country, Country_ID FROM countries";

            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                String country = rs.getString("Country");
                int country_id = rs.getInt("Country_ID");

                Country c = new Country(country, country_id);

                countryList.add(c);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return countryList;
    }
}
