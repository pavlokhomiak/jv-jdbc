package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found", e);
        }
    }

    public static Connection getConnection() {
        Connection connection;
        try {
            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "password");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db");
        } catch (SQLException e) {
            throw new RuntimeException("Cant create connection", e);
        }
        return connection;
    }
}
