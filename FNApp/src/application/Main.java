package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// FLORENCE LOGIN
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.PostgreSQLConnection;
import application.User;
// FLORENCE ENDE

public class Main extends Application {

    private Scene registration;  // Deklaration der Szene für Registrierung außerhalb der start()-Methode

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // ** Szene 1: Login-Seite **
            // HBox mit Bild
            HBox pic = new HBox();
            Image image = new Image(getClass().getResource("/resources/smartwatch.jpg").toExternalForm());
            ImageView imageview = new ImageView(image);
            imageview.setFitWidth(350);
            imageview.setFitHeight(600);
            pic.getChildren().add(imageview);
            
            
            // Überschrift
            VBox intro = new VBox();
            Text h1 = new Text("Willkommen");
            h1.getStyleClass().add("h1");
            h1.setTextAlignment(TextAlignment.CENTER);
            Text h2 = new Text("Die App die dich Fit hält");
            h2.setTextAlignment(TextAlignment.CENTER);
            h2.getStyleClass().add("h2");
            
            // VBox mit Label und Textfeld
            VBox userInput = new VBox(10);
            Label username = new Label("Username: ");
            TextField uName = new TextField();
            userInput.getStyleClass().add("TextField");
            Label password = new Label("Password: ");
            PasswordField pWord = new PasswordField();         
            userInput.setAlignment(Pos.TOP_CENTER);
            userInput.getStyleClass().add("PasswordField");
            userInput.getStyleClass().add("Label");
            
            // Button zum Absenden und Registrieren
            Button submitButton = new Button("Login");
               
            Button registButton = new Button("Registrieren");

            // ** Layout und Szene für die Login-Seite **
            userInput.getChildren().addAll(h1,h2,username, uName, password, pWord, submitButton, registButton);
            BorderPane loginlayout = new BorderPane();
            loginlayout.setLeft(pic);
            loginlayout.setCenter(userInput);
            Scene login = new Scene(loginlayout, 800, 600);
            login.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            loginlayout.getStyleClass().add("login");
            
            // FLORENCE LOGIN
         // Fehlermeldungstext für die GUI hinzufügen
            Text errorMessage = new Text();
            errorMessage.setFill(Color.MISTYROSE);
            errorMessage.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            errorMessage.setVisible(false);
            userInput.getChildren().add(errorMessage); // Zur VBox hinzufügen

            // Login-Button-Action
            submitButton.setOnAction(e -> {
                String inputUsername = uName.getText();
                String inputPassword = pWord.getText();

                User user = PostgreSQLConnection.authenticate(inputUsername, inputPassword);

                if (user != null) {
                    errorMessage.setVisible(false); // Verstecke evtl. vorherige Fehlermeldung
                    System.out.println("Login erfolgreich!");

                    // Benutzerinformationen anzeigen
                    Text userDetails = new Text("Vorname: " + user.getFirstName() + "\n" +
                                                "Nachname: " + user.getLastName() + "\n" +
                                                "Größe: " + user.getHeight() + " cm\n" +
                                                "Gewicht: " + user.getWeight() + " kg\n" +
                                                "Aktueller BMI: " + user.getBmi());
                    userDetails.setStyle("-fx-font-size: 24px;");
                    userDetails.setFill(Color.WHITE);

                    VBox userInfo = new VBox(10);
                    userInfo.setAlignment(Pos.CENTER);
                    userInfo.getChildren().add(userDetails);

                    loginlayout.setCenter(userInfo);
                } else {
                    // Prüfen, ob es am Passwort oder am Usernamen liegt
                    String sql = "SELECT * FROM mitarbeiter WHERE user_name = ?";
                    try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fnFitnesstracker", "postgres", "histidine");
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, inputUsername);
                        ResultSet rs = pstmt.executeQuery();

                        if (rs.next()) {
                            errorMessage.setText("❌ Falsches Passwort!");
                        } else {
                            errorMessage.setText("❌ Benutzername nicht gefunden!");
                        }
                        errorMessage.setVisible(true);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        errorMessage.setText("❌ Datenbankfehler!");
                        errorMessage.setVisible(true);
                    }
                }
            });

            // FLORENCE ENDE
            
            //Szene 2 - Registrierung 
            //Head Pic & Überschrift
            VBox header = new VBox();
            Image headimg = new Image(getClass().getResource("/resources/jogging640.jpg").toExternalForm());
            ImageView headimgv = new ImageView(headimg);
            headimgv.setFitWidth(700);
            headimgv.setFitHeight(280);
            Text regh1 = new Text("Registrierung");
            header.setAlignment(Pos.CENTER);
            header.getChildren().addAll(headimgv,regh1);
            regh1.getStyleClass().add("h1");
            
            
            // ** Registrierung Layout erstellen **
            VBox regForm = new VBox(5);
            Label fname = new Label("Vorname: ");
            TextField firstname = new TextField();
			fname.getStyleClass().add("custom-label-reg");
			
            Label lname = new Label("Nachname: ");
            TextField lastname = new TextField();
			lname.getStyleClass().add("custom-label-reg");
			
            Label email = new Label("Email: ");
            TextField Email = new TextField();
			email.getStyleClass().add("custom-label-reg");
			
			Label psswort = new Label("Wähle Ein Passwort:");
			PasswordField passwort = new PasswordField();
			psswort.getStyleClass().add("custom-label-reg");
			
			Label pssbest = new Label("Gib es erneut ein:");
			PasswordField passbest = new PasswordField();
			pssbest.getStyleClass().add("custom-label-reg");
			
			Label height = new Label("Größe(cm): ");
			TextField heightcm = new TextField();
			height.getStyleClass().add("custom-label-reg");
			
			Label weight = new Label("Gewicht(Kg): ");
			TextField weightKG = new TextField();
			weight.getStyleClass().add("custom-label-reg");
            regForm.setAlignment(Pos.CENTER);
         
            Button regButton = new Button("Registrieren");
            Button backButton = new Button("Zurück");
            
            regForm.getChildren().addAll(fname,firstname,lname,lastname,email,Email,psswort,passwort,
            		pssbest,passbest,height,heightcm,weight,weightKG,regButton,backButton);
            regForm.getStyleClass().addAll("TextField","PasswordField");
            // Layout für Registrierungs-Seite 
            BorderPane registrationlayout = new BorderPane();
            registrationlayout.setCenter(regForm);
            registrationlayout.setTop(header);
           
            

            registration = new Scene(registrationlayout, 700,700);  // Szene zuweisen
            registration.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            registrationlayout.getStyleClass().add("registration");
            
            
            // ** Aktionen für die Buttons **
            registButton.setOnAction(e -> primaryStage.setScene(registration));  // Szene wechseln
            backButton.setOnAction(e -> primaryStage.setScene(login));  // Zurück zur Login-Szene

            // ** Hauptfenster anzeigen **
            primaryStage.setScene(login);
            primaryStage.setTitle("Fitness Track App");
            primaryStage.show();
          

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
