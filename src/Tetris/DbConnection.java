package Tetris;

import java.sql.*;

public class DbConnection {
    public static Connection conn;

    public static void connect() {
        String databaseURL = "jdbc:mysql://remotemysql.com/e6hO9brYMl?useSSL=false&user=e6hO9brYMl&password=35RRBBUkAH";

        try {
            conn = DriverManager.getConnection(databaseURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveHighestScore(int highestScore) {
        if (isInDB(highestScore))
            return;
        connect();
        String sql = "INSERT INTO highscores(id, score) VALUES (NULL, ?)";

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, highestScore);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    public static int getRankingPosition(int score) {
        connect();
        String sql = "SELECT count(*) FROM highscores where score > " + score;
        int position = 0;

        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next())
                position = result.getInt(1) + 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return position;
    }

    private static boolean isInDB(int score) {
        connect();
        String sql = "SELECT * FROM highscores where score = " + score;

        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return false;
    }

    public static int[] getTopRanging() {
        connect();
        int[] top = new int[10];

        String sql = "SELECT score FROM highscores ORDER BY score DESC LIMIT 10";

        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            int i = 0;
            while (result.next()) {
                top[i] = result.getInt(1);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return top;
    }


}
