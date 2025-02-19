package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/Fitnesstracker";
    private static final String USER = "postgres";
    private static final String PASSWORD = "vn7791g782K!";

    public static Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Methode, um nach einem Benutzernamen zu suchen und Mitarbeiter-Daten abzurufen
    public static String searchByUserName(String userName) {
        String result = null;
        String query = "SELECT mitarbeiter_id, vorname, nachname, groesse, aktuelles_gewicht, aktueller_bmi, user_name " +
                       "FROM mitarbeiter WHERE user_name = ?";  // Benutzername als Parameter

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, userName);  // Benutzername als Parameter setzen
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Daten abrufen und als String formatieren
                result = "\nVorname: " + rs.getString("vorname") +
                         "\nNachname: " + rs.getString("nachname") +
                         "\nGröße: " + rs.getDouble("groesse") +
                         "\nAktuelles Gewicht: " + rs.getDouble("aktuelles_gewicht") +
                         "\nAktueller BMI: " + rs.getDouble("aktueller_bmi");
            } else {
                result = "Kein Mitarbeiter mit diesem Benutzernamen gefunden.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
