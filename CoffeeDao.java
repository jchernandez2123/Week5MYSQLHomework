package dao;

import entity.Coffee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;




public class CoffeeDao {


    private Connection connection;
    private final String GET_ALL_COFFEE_QUERY = "SELECT * FROM coffee";
    private final String GET_COFFEE_BY_ID_QUERY = "SELECT * FROM coffee WHERE id = ?";
    private final String CREATE_NEW_COFFEE_QUERY = "INSERT INTO coffee (name, quantity, price) VALUES (?, ?, ?)";
    private final String UPDATE_COFFEE_QUERY = "UPDATE coffee SET name = ?, quantity = ?, price = ? WHERE id = ?";
    private final String DELETE_COFFEE_QUERY = "DELETE FROM coffee WHERE id = ?";

    public CoffeeDao() {
        connection = DBConnection.getConnection();
    }

    public List<Coffee> getAllCoffee() throws SQLException {
        ResultSet resultSet = connection.prepareStatement(GET_ALL_COFFEE_QUERY).executeQuery();
        List<Coffee> coffeeList = new ArrayList<Coffee>();

        while (resultSet.next()) {
            coffeeList.add(populateCoffeeItem(resultSet));
        }

        return coffeeList;
    }

    public Coffee getCoffeeById(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(GET_COFFEE_BY_ID_QUERY);
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {
            return populateCoffeeItem(resultSet);
        }

        return new Coffee(0, "", "", 0.0);
    }

    public void createNewCoffee(String name, String quantity, Double price) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(CREATE_NEW_COFFEE_QUERY);
        ps.setString(1, name);
        ps.setString(2, quantity);
        ps.setDouble(3, price);
        ps.executeUpdate();
    }

    public void updateCoffee(Coffee CoffeeItem) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(UPDATE_COFFEE_QUERY);

        ps.setString(1, CoffeeItem.getName());
        ps.setString(2, CoffeeItem.getQuantity());
        ps.setDouble(3, CoffeeItem.getPrice());
        ps.setInt(4, CoffeeItem.getId());

        ps.executeUpdate();
    }

    public void deleteCoffee(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(DELETE_COFFEE_QUERY);
        ps.setInt(1, id);

        ps.executeUpdate();
    }

    private Coffee populateCoffeeItem(ResultSet resultSet) throws SQLException {

        return new Coffee(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getDouble(4)
        );
    }

}











