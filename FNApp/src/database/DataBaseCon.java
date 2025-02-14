package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseCon {
	public class PostgreSQLConnection {

        private static final String URL = "jdbc:postgresql://localhost:5432/Fitnesstracker";
        private static final String USER = "postgres";
        private static final String PASSWORD = "vn7791g782K!";

        public static void main(String[] args) {
            Connection connection = null;

            try {
                // Verbindung herstellen
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                if (connection != null) {
                    System.out.println("Verbindung zur PostgreSQL-Datenbank erfolgreich!");
                }
            } catch (SQLException e) {
                System.out.println("Fehler beim Herstellen der Verbindung:");
                e.printStackTrace();
            } finally {
                // Verbindung schließen
                if (connection != null) {
                    try {
                        connection.close();
                        System.out.println("Verbindung geschlossen.");
                    } catch (SQLException e) {
                        System.out.println("Fehler beim Schließen der Verbindung:");
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
