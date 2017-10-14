package sample.sample.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * samixx project
 *
 * @author Salman Samie
 *         21 Aug, 2016
 */

public class LoginModel {

    Connection connection;

    public LoginModel() {

        connection = DbConnection.LoginConnector();

        if (connection == null) {
            System.out.println("Database connection error");
            System.exit(1);
        }
    }


    public boolean isLoginValid(String username, String password) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM user_info WHERE username=? and password=?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (Exception e) {
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }


    }

}
