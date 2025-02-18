package application;

import java.sql.*;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/fitnessapp"; 
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "root"; 

    public static Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn =  DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" verbingung zur DB erfolgreich ");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
        	System.err.println(" Fehler bei verbingung zur DB ");
            e.printStackTrace();
            return null;
        }
    }

    // Methode, um nach einem Benutzernamen zu suchen und Mitarbeiter-Daten abzurufen
    public static String searchByUserName(String userName) {
        String result = null;
        String query = "SELECT mitarbeiter_id, vorname, nachname, groesse, aktuelles_gewicht, aktuelle_bmi, user_name " +
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
                         "\nAktuelle BMI: " + rs.getDouble("aktuelle_bmi");
            } else {
                result = "Kein Mitarbeiter mit diesem Benutzernamen gefunden.";
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return result;
    }
}
