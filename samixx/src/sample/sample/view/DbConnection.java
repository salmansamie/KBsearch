package sample.sample.view;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * samixx project
 *
 * @author Salman Samie
 *         21 Aug, 2016
 */

public class DbConnection {
    @SuppressWarnings("ThrowablePrintedToSystemOut")
    public static Connection LoginConnector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:Web Data");
            return connect;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
