package AppSch.DAO;

import AppSch.Model.Country;
import AppSch.Model.Customer;
import AppSch.Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Division dao.
 */
public class DivisionDAOImpl {

    /**
     * Get all divisions observable list.
     *
     * @return the observable list
     */
    public static ObservableList<Division> getAllDivisions(){
        ObservableList<Division> divList = FXCollections.observableArrayList();

        try {

            String sql = "SELECT Division, countries.Country, Division_ID FROM first_level_divisions, countries WHERE first_level_divisions.Country_ID = countries.Country_ID";

            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                String division = rs.getString("Division");
                String country = rs.getString("countries.Country");
                int division_id = rs.getInt("Division_ID");


                Division d = new Division(division, country, division_id);

                divList.add(d);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return divList;
    }

    /**
     * Get all divisions observable list.
     *
     * @param country_name the country name
     * @return the observable list
     */
    public static ObservableList<Division> getAllDivisions(String country_name){
        int country_id = 0;
        for (Country country :CountryDAOImpl.getAllCountries()) {
            if (country_name.equals(country.getCountry())) {
                country_id = country.getCountry_id();

            }
        }
        ObservableList<Division> divList = FXCollections.observableArrayList();

        try {

            String sql = "SELECT Division, countries.Country, Division_ID FROM first_level_divisions, countries WHERE first_level_divisions.Country_ID = countries.Country_ID AND first_level_divisions.Country_ID = ?";

            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, country_id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                String division = rs.getString("Division");
                String country = rs.getString("countries.Country");
                int division_id = rs.getInt("Division_ID");


                Division d = new Division(division, country, division_id);

                divList.add(d);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return divList;
    }
}
