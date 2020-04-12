package Tetris;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static Connection conn;

    public static void connect() {
        String databaseURL = "jdbc:mysql://localhost:3306/tetris?user=dev&password=ZAQ!2wsx";

        try {
            conn = DriverManager.getConnection(databaseURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
