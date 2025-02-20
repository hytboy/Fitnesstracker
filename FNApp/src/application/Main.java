package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.BorderPane;



import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;

import application.User;
import database.PostgreSQLConnection;


public class Main extends Application implements EventHandler<ActionEvent>{

	private Scene registration;  // Deklaration der Szene für Registrierung außerhalb der start()-Methode
    private Scene mainMenu;	
    private User currentUser;
    private Label uStatVorname,uStatNachname,uStatheight, uStatweight;
    private Text uStatvname, uStatnname, uStath, uStatw, uStatbmi, aktbmi;
   // private Label errorMessage;
    //private TabPane mainmenu;
   

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // ** Szene 1: Login-Seite **
            // HBox mit Bild
            HBox pic = new HBox();
            Image image = new Image(getClass().getResource("/resources/img/smartwatch.jpg").toExternalForm());
            ImageView imageview = new ImageView(image);
            imageview.setFitWidth(350);
            imageview.setFitHeight(600);
            pic.getChildren().add(imageview);


            // Überschrift
            HBox intro = new HBox();
            Text h1 = new Text("FT App");
            h1.getStyleClass().add("h1");
            h1.setTextAlignment(TextAlignment.CENTER);
            Text h2 = new Text("Willkommen\n Die App die dich Fit hält");
            h2.setTextAlignment(TextAlignment.CENTER);
            h2.getStyleClass().add("h2");
            intro.getChildren().addAll(h1,h2);
            
            // VBox mit Label und Textfeld
            VBox userInput = new VBox(10);
            Label username = new Label("Username: ");
            TextField uName = new TextField();
            uName.getStyleClass().add("TextField");
            Label passwort = new Label("Password: ");
            PasswordField pWord = new PasswordField();
            pWord.getStyleClass().add("PasswordField");
   
            userInput.setAlignment(Pos.TOP_CENTER);

            // Button zum Absenden und Registrieren
            Button submitButton = new Button("Login");
          
            Button registButton = new Button("Registrieren");
            userInput.getChildren().addAll(h1,h2,username, uName, passwort, pWord, submitButton, registButton);
            
            
            
            // ** Layout und Szene für die Login-Seite **
            
            BorderPane loginlayout = new BorderPane();
            loginlayout.setLeft(pic);
            loginlayout.setCenter(userInput);
            Scene login = new Scene(loginlayout, 800, 700);
            login.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            loginlayout.getStyleClass().add("login");
            imageview.fitHeightProperty().bind(login.heightProperty());

         // Login-Button-Action
            submitButton.setOnAction(e -> {
                String inputUsername = uName.getText();
                String inputPassword = pWord.getText();

                User user = PostgreSQLConnection.authenticate(inputUsername, inputPassword);
               
                if (user != null) {
                	System.out.println("Login erfolgreich!");
                    currentUser = user; 
                 // Benutzerinformationen in die Textfelder setzen

                   uStatvname.setText(String.valueOf(user.getFirstName()));
                   uStatnname.setText(String.valueOf(user.getLastName()));
                   uStath.setText(String.valueOf(user.getHeight()));
                   uStatw.setText(String.valueOf(user.getWeight()));
                   uStatbmi.setText(String.valueOf(user.getBmi()));
                   
                   primaryStage.setScene(mainMenu);
                   primaryStage.show();
                   
                } else {
                    // Prüfen, ob es am Passwort oder am Usernamen liegt
                    String sql = "SELECT * FROM mitarbeiter WHERE user_name = ?";
                    try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Fitnesstracker", "postgres", "vn7791g782K!");
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, inputUsername);
                        ResultSet rs = pstmt.executeQuery();

                        if (rs.next()) {
                        	System.err.println("Falsches Passwort!");
                       //     errorMessage.setText("❌ Falsches Passwort!");
                        } else {
                        	System.err.println("Benutzername nicht gefunden!");
                         //   errorMessage.setText("❌ Benutzername nicht gefunden!");
                        }
                       
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        System.err.println("Datenbankfehler!");
                     //   errorMessage.setText("❌ Datenbankfehler!");
                     //   errorMessage.setVisible(true);
                    }
                    
                }
            });
            
     
            //Szene 2 - Registrierung
            //Head Pic & Überschrift
            VBox header = new VBox();
            Image headimg = new Image(getClass().getResource("/resources/img/jogging640.jpg").toExternalForm());
            ImageView headimgv = new ImageView(headimg);
            headimgv.setFitWidth(800);
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
			PasswordField password1 = new PasswordField();
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

            regForm.getChildren().addAll(fname,firstname,lname,lastname,email,Email,psswort,password1,
            		pssbest,passbest,height,heightcm,weight,weightKG,regButton,backButton);

            regForm.getStyleClass().addAll("TextField","PasswordField");

            // Layout für Registrierungs-Seite
            BorderPane registrationlayout = new BorderPane();
            registrationlayout.setCenter(regForm);
            registrationlayout.setTop(header);

            registration = new Scene(registrationlayout, 800,700);  // Szene zuweisen
            registration.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            registrationlayout.getStyleClass().add("registration");
            headimgv.fitWidthProperty().bind(registration.widthProperty());
          
            //Layout für MainMenu
            BorderPane menulayout = new BorderPane();
            mainMenu = new Scene(menulayout,800, 700);
            mainMenu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            menulayout.getStyleClass().add("mainMenu");
            
            
            HBox menu = new HBox();
            MenuBar menubar = new MenuBar();
            Menu BMICalc = new Menu("BMI-Rechner");
            Menu activity = new Menu("Aktivität");
            Menu kcalCalc = new Menu("KCal-Berechnen");
            Button logoutButton = new Button("Logout");
            menubar.getMenus().addAll(BMICalc,activity,kcalCalc);
            menubar.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            menu.setAlignment(Pos.CENTER);
            menu.getChildren().addAll(menubar,logoutButton);
            

            VBox userStats = new VBox(5);
            Label uStatVorname = new Label("Vorname:");
            uStatVorname.getStyleClass().add("custom-label-menu");
            uStatvname = new Text("");
            Label uStatNachname = new Label("Nachname: \n");
            uStatNachname.getStyleClass().add("custom-label-menu");
            uStatnname = new Text("");
            Label uStatheight = new Label("Größe: \n");
            uStatheight.getStyleClass().add("custom-label-menu");
            uStath = new Text("");
            Label uStatweight = new Label("Gewicht: \n");
            uStatweight.getStyleClass().add("custom-label-menu");
            uStatw = new Text("");
            Label uStatBmi = new Label("BMI: ");
            uStatBmi.getStyleClass().add("custom-label-menu");
            uStatbmi = new Text("");
            //userStats.setPrefWidth(267);
            userStats.getChildren().addAll(uStatVorname, uStatvname,uStatNachname, uStatnname,uStatheight, uStath,uStatweight, uStatw, uStatBmi, uStatbmi);
            
            VBox bmiCalc = new VBox(5);
            
            Text bmiCalcHeader = new Text("BMI-Calculator");
            bmiCalcHeader.getStyleClass().add("h1");
            Label yourWeight = new Label("Dein aktuelles Gewicht: \n");
            TextField yWeight = new TextField();
            yWeight.getStyleClass().add("TextField");
            Label yourHeight = new Label("Deine Größe(cm): \n");
            TextField yHeight = new TextField();
            yHeight.getStyleClass().add("TextField");
            Button enterButton = new Button("Enter");
            
            Label aktBmi = new Label("Dein neuer BMI: ");
            aktbmi = new Text("");
            
            //bmiBewertung = new Text("");
            
            //bmiCalc.setPrefSize(267, 700);
            bmiCalc.getChildren().addAll(bmiCalcHeader, yourWeight, yWeight, yourHeight, yHeight, enterButton, aktBmi, aktbmi);
            
            //enter-Button Aktion
            enterButton.setOnAction(e -> {
                try {
                    // Eingabewerte aus den Textfeldern abrufen
                    double userweight = Double.parseDouble(yWeight.getText());
                    double userheightCm = Double.parseDouble(yHeight.getText());
                    double userheightM = userheightCm / 100.0;

                    // Berechnung des BMI
                    double bmi = userweight / Math.pow(userheightM, 2);
                    //Ausgabe der Berechnung
                    System.out.println("BMI: " + bmi);  // Ausgabe zur Überprüfung
                    aktbmi.setText(String.format("%.2f", bmi)); // 
                    
                  
                    // Datenbank-Update
                    String sql = "UPDATE mitarbeiter SET groesse = ?, aktuelles_gewicht = ?,  aktuelle_bmi = ? WHERE user_name = ?";
                   
                    try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Fitnesstracker", "postgres", "vn7791g782K!");
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setDouble(1, userheightCm);
                        pstmt.setDouble(2, userweight);
                        pstmt.setDouble(3, bmi);
                        pstmt.setString(4, currentUser.getUsername());
                        System.out.println("Benutzername für Update: " + currentUser.getUsername());
   
                        int rowsUpdated = pstmt.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("Daten erfolgreich aktualisiert.");
                        } else {
                            System.out.println("Datenbank-Update fehlgeschlagen.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        System.err.println("Fehler beim Aktualisieren der Datenbank!");
                    }

                    // Optional: Zeige den BMI in der Benutzeroberfläche an
                    //Label bmiLabel = new Label("Dein berechneter BMI: " + String.format("%.2f", bmi));
                    
                    
                   // bmiCalc.getChildren().add(bmiLabel);  // Füge das Label zur BMI-Box hinzu

                } catch (NumberFormatException ex) {
                    System.err.println("Fehler: Bitte gültige Zahlen für Gewicht und Größe eingeben.");
                }
            });
            
            userStats.setAlignment(Pos.CENTER_LEFT);
            bmiCalc.setAlignment(Pos.CENTER);
            menulayout.setTop(menu);
            menulayout.setLeft(userStats);
            menulayout.setCenter(bmiCalc);
            
            
            // ** Aktionen für die Buttons **
            //regButton
            logoutButton.setOnAction(e -> primaryStage.setScene(login));	//Logout Button(Abmeldung erfolgt und Szenen wecheseln zum Login-Szene)
          
            
            registButton.setOnAction(e -> primaryStage.setScene(registration));  // Registrierungs Button (Wechsel Szene wechseln
            backButton.setOnAction(e -> primaryStage.setScene(login));  // Zurück zur Login-Szene

            // ** Hauptfenster anzeigen **
            primaryStage.setScene(login);
            primaryStage.setTitle("Fitness Track App");
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
	

