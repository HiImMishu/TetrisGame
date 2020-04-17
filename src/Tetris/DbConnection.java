package Tetris;

import java.sql.*;

/**
 * Klasa udostepniajaca statyczne metody do laczenia sie z baza, zakonczenia polaczenia, wykonania rzadanych zapytan.
 */
public class DbConnection {
    /**
     * Obiekt klasy Connection przechowujacy polaczene z baza.
     */
    public static Connection conn;

    /**
     * Metoda statyczna umozliwiajaca polaczenie sie z baza danych.
     */
    public static void connect() {
        String databaseURL = "jdbc:mysql://remotemysql.com/e6hO9brYMl?useSSL=false&user=e6hO9brYMl&password=35RRBBUkAH";

        try {
            conn = DriverManager.getConnection(databaseURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda statyczna umozliwiajaca zakonczenie polaczenia z baza danych.
     */
    public static void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metoda statyczna zapisujaca parametr highestScore do bazy danych.
     * Zapis nastepuje tylko w wypadku gdy nie ma go jeszcze w bazie danych.
     * @param highestScore Najwyzszy wynik uzyskany przez uzytkownika.
     */
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

    /**
     * Metoda statyczna uzyskujaca aktualna pozycje w rankingu punktow.
     * @param score Wynik punktowy gracza w stosunku do ktorego liczony bedzie ranking
     * @return Pozycja gracza w rankingu
     */
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

    /**
     * Prywatna metoda statyczna sprawdzajaca czy dany wynik znajduje sie juz w bazie danych.
     * @param score Wynik ktorego istnienie w bazie ma zostac sprawdzone.
     * @return Wartosc logiczna: true jest istnieje false jesli nie istnieje.
     */
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

    /**
     * Metoda statyczna pobierajaca z bazy top 10 pozycji z rankingu.
     * @return Tablica statyczna zawierajaca top 10 pozycji wynikow z rankingu.
     */
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
