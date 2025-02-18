package database;

import java.sql.*;
import application.User;

public class PostgreSQLConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/fnFitnesstracker";
    private static final String USER = "postgres";
    private static final String PASSWORD = "histidine";

    // FLORENCE - LOGIN
    public static User authenticate(String inputUsername, String inputPassword) {
        String sql = "SELECT * FROM mitarbeiter WHERE user_name = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, inputUsername);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Benutzer existiert
                String storedPassword = rs.getString("passwort");

                if (storedPassword.equals(inputPassword)) {
                    // Passwort korrekt, Benutzerdaten zurückgeben
                    String firstName = rs.getString("vorname");
                    String lastName = rs.getString("nachname");
                    double height = rs.getDouble("groesse");
                    double weight = rs.getDouble("aktuelles_gewicht");
                    double bmi = rs.getDouble("aktuelle_bmi");

                    // Rückgabe des User-Objekts
                    return new User(firstName, lastName, height, weight, bmi);
                } else {
                    // Falsches Passwort
                    System.out.println("Falsches Passwort");
                    return null;
                }
            } else {
                // Benutzername nicht gefunden
                System.out.println("User nicht gefunden");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;  // Fehler bei der Verbindung zur Datenbank
        }
    }
    // FLORENCE - ENDE	
}
