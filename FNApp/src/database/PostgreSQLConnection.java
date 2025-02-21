package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.User;

public class PostgreSQLConnection {
    
        private static final String URL = "jdbc:postgresql://localhost:5432/fitnessapp";
        private static final String USER = "postgres";
        private static final String PASSWORD = "root";

        public static User authenticate(String inputusername, String inputpassword) {
            String sql = "SELECT * FROM mitarbeiter WHERE user_name = ? AND passwort = ?";

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, inputusername);
                pstmt.setString(2, inputpassword);
                ResultSet rs = pstmt.executeQuery();
                
                
                if (rs.next()) {
                    // Benutzer gefunden - Benutzerobjekt erstellen
                    User user = new User();
                    user.setFirstName(rs.getString("vorname"));
                    user.setLastName(rs.getString("nachname"));
                    user.setHeight(rs.getInt("groesse"));
                    user.setWeight(rs.getDouble("aktuelles_gewicht"));
                    user.setBmi(rs.getDouble("aktuelle_bmi"));
                    user.setUsername(rs.getString("user_name"));
                    return user;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Benutzer nicht gefunden oder Fehler
            return null;
        }
        
    }

