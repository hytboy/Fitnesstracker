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
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
 
import javafx.scene.layout.BorderPane;
 
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
 
 
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
 
import database.PostgreSQLConnection;
 
 
public class Main extends Application implements EventHandler<ActionEvent>{
 
	private Scene registration;  // Deklaration der Szene für Registrierung außerhalb der start()-Methode
    private Scene mainMenu;	
    private User currentUser;
    private Text uStatvname, uStatnname, uStath, uStatw, uStatbmi, aktbmi,kcalclac;
	private VBox bmiCalc;
    private VBox activity;
	
   
 
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
            
            // ** Layout und Szene für die Login-Seite **
            
            intro.getChildren().addAll(h1,h2);
            userInput.getChildren().addAll(h1,h2,username, uName, passwort, pWord, submitButton, registButton);
            BorderPane loginlayout = new BorderPane();
            loginlayout.setLeft(pic);
            loginlayout.setCenter(userInput);
            Scene login = new Scene(loginlayout, 800, 700);
            login.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            loginlayout.getStyleClass().add("login");
            imageview.fitHeightProperty().bind(login.heightProperty());
 
         // Login-Button-Action
            submitButton.setOnAction(_ -> {
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
                    try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fitnessapp", "postgres", "root");
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
            
         
         // Labels und Textfelder für Eingaben
            
         Label usernameLabel = new Label("Benutzername:");
         TextField usernames = new TextField();
         usernameLabel.getStyleClass().add("custom-label-reg");
            
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

         regButton.setOnAction(_ -> {
             // Sicherstellen, dass die Felder für Vorname und Nachname nicht leer sind
             String vorname = firstname.getText();
             String nachname = lastname.getText();
             
             if (vorname.isEmpty() || nachname.isEmpty() ){
                 System.err.println("Bitte geben Sie Vorname und Nachname ein!");
                 return; // Beende die Methode, wenn die Felder leer sind
             }
             String usern = usernames.getText();
             
             if ( usern.isEmpty() ){
                 System.err.println("Bitte geben Sie Username ein!");
                 return; // Beende die Methode, wenn die Felder leer sind
             }
             
             String em = Email.getText();
             if (em.isEmpty() ){
                 System.err.println("Bitte geben Sie Email ein!");
                 return; // Beende die Methode, wenn die Felder leer sind
             }
             // Sicherstellen, dass Passwort und Bestätigung übereinstimmen
             String pass1 = password1.getText();
             String pass2 = passbest.getText();
             if (pass1.isEmpty() || pass2.isEmpty() || !pass1.equals(pass2)) {
                 System.err.println("Bitte geben Sie dasselbe Passwort ein!");
                 return; // Beende die Methode, wenn Passwörter nicht übereinstimmen oder leer sind
             }

             // Größe und Gewicht konvertieren und validieren
             double groesse = 0;
             double gewicht1 = 0;
             try {
                 groesse = Double.parseDouble(heightcm.getText());
                 gewicht1 = Double.parseDouble(weightKG.getText());
             } catch (NumberFormatException ex) {
                 System.err.println("Ungültige Zahl für Größe oder Gewicht!");
                 return; // Beende die Methode bei ungültiger Zahleneingabe
             }

             // Verbindung zur Datenbank herstellen
             try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fitnessapp", "postgres", "root")) {
                 String query = "INSERT INTO mitarbeiter (vorname, nachname, groesse, aktuelles_gewicht, aktuelle_bmi, user_name, passwort,email) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
                 try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                     pstmt.setString(1, vorname);
                     pstmt.setString(2, nachname);
                     pstmt.setDouble(3, groesse);
                     pstmt.setDouble(4, gewicht1); 
                     pstmt.setInt(5, 0); 
                     pstmt.setString(6, usern); 
                     pstmt.setString(7, pass2);
                     pstmt.setString(8, em);

                     int rowsInserted = pstmt.executeUpdate();
                     if (rowsInserted > 0) {
                         System.out.println("Daten erfolgreich gespeichert.");
                     } else {
                         System.err.println("Fehler beim Einfügen in die Datenbank!");
                     }
                 }
             } catch (SQLException ex) {
                 System.err.println("SQL-Fehler: " + ex.getMessage());
                 ex.printStackTrace();
             }
         });
            regForm.getChildren().addAll(usernameLabel, usernames,fname,firstname,lname,lastname,email,Email,psswort,password1,
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
          
            
            BorderPane menulayout = new BorderPane();
            mainMenu = new Scene(menulayout,800, 700);
            mainMenu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            menulayout.getStyleClass().add("mainMenu");
           
			
			Label bmiLabel = new Label("BMI-Rechner");
            bmiLabel.getStyleClass().add("menu-label"); // CSS-Klasse, die du definierst
            bmiLabel.setOnMouseClicked(_ -> menulayout.setCenter(bmiCalc));

            Label activitys = new Label("Aktivität");
            activitys.getStyleClass().add("menu-label");
            activitys.setOnMouseClicked(_ -> menulayout.setCenter(activity));

            
            
            Button logoutButton = new Button("Logout");
            logoutButton.setOnAction(_ -> primaryStage.setScene(login));
            
            HBox menu = new HBox(10, bmiLabel, activitys, logoutButton);
           
            menu.setAlignment(Pos.CENTER);
           
            menulayout.setTop(menu);
            
            
            
 
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
           
         // Update-Button
            Button updateButton = new Button("Update");
            updateButton.setOnAction(_ -> {
            	if (currentUser != null) {
                    try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fitnessapp", "postgres", "root")) {
                         
                    	
                    	double gewicht = 0.0;
                    	double bmi = 0.0;	
                    	try (PreparedStatement pstmt = conn.prepareStatement("SELECT aktuelle_bmi, aktuelles_gewicht  FROM mitarbeiter WHERE user_name = ?")) {
                    		pstmt.setString(1, currentUser.getUsername());
                    		ResultSet rs = pstmt.executeQuery();
                    		if (rs.next()) {
                    			bmi = rs.getDouble("aktuelle_bmi");
                    			gewicht = rs.getDouble("aktuelles_gewicht");
                    			uStatbmi.setText(" " + bmi);
                    			uStatw.setText(" " + gewicht);
                    		} else {
                    			System.err.println("Kein Benutzer gefunden !");
                    			return;
                    		} 
                    		
                    	}     
                    	
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        System.err.println("Fehler beim Abrufen des BMI aus der Datenbank!");
                    }
                } else {
                    System.err.println("Kein Benutzer angemeldet!");
                }
            });

            userStats.setPrefWidth(200);
            userStats.getChildren().addAll(uStatVorname, uStatvname, uStatNachname, uStatnname,
                                           uStatheight, uStath, uStatweight, uStatw,
                                           uStatBmi, uStatbmi, updateButton);

            
            
			
			
            bmiCalc = new VBox(5);
            
            Text bmiCalcHeader = new Text("BMI-Rechner");
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
            
            bmiCalc.setPrefSize(600, 700);
            bmiCalc.getChildren().addAll(bmiCalcHeader, yourWeight, yWeight, yourHeight, yHeight, enterButton, aktBmi, aktbmi);
            
            enterButton.setOnAction(_ -> {
                try {
                    // Eingabewerte aus den Textfeldern abrufen
                    double userweight = Double.parseDouble(yWeight.getText());
                    double userheightCm = Double.parseDouble(yHeight.getText());
                    double userheightM = userheightCm / 100.0;
 
                    // Berechnung des BMI
                    double bmi = userweight / Math.pow(userheightM, 2);
                     // Ausgabe zur Überprüfung
                    aktbmi.setText(String.format("%.2f", bmi));
                    
                    
                    // Datenbank-Update
                    String sql = "UPDATE mitarbeiter SET groesse = ?,aktuelles_gewicht = ?,  aktuelle_bmi = ? WHERE user_name = ?";
                    
                    //Aktuellen BMI auf der Oberfläche anzeigen lassen
                    //aktbmi.setText(String.format("%.2f", bmi));
                   
                    
                    try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fitnessapp", "postgres", "root");
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setDouble(1, userheightCm);
                        pstmt.setDouble(2, userweight);
                        pstmt.setDouble(3, bmi);
                        pstmt.setString(4, currentUser.getUsername());
 
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
                   /* Label bmiLabel = new Label("Dein berechneter BMI: " + String.format("%.2f", bmi));
                    
                    
                    bmiCalc.getChildren().add(bmiLabel);  // Füge das Label zur BMI-Box hinzu*/
 
                } catch (NumberFormatException ex) {
                    System.err.println("Fehler: Bitte gültige Zahlen für Gewicht und Größe eingeben.");
                }
            });
         // VBox für Aktivitäten
            activity = new VBox(10);

         // Überschrift
         Text activityHeader = new Text("Aktivitäten");
         activityHeader.getStyleClass().add("h1");

         // Menüleiste
         MenuBar menuBar = new MenuBar();
         Menu activityMenu = new Menu("Aktivitäten");

         String[] activities = {"Joggen", "Schwimmen", "Radfahren", "Nordic Walking", 
                                 "Krafttraining", "HIT-Workout", "Boxen", 
                                 "Fußball", "Basketball", "Turnen","Wandern","Fitnessstudio","Yoga"};

         TextField ActivityField = new TextField();
         ActivityField.setPromptText("Gewählte Aktivität");
         ActivityField.setEditable(false);

         // Menüeinträge hinzufügen
         for (String act : activities) {
             MenuItem item = new MenuItem(act);
             item.setOnAction(_ -> ActivityField.setText(act));
             activityMenu.getItems().add(item);
         }

         menuBar.getMenus().add(activityMenu);
         Label Stunden = new Label("Stunden : ");

         // Eingabe für Dauer der Aktivität
         Spinner<Integer> hoursSpinner = new Spinner<>(0, 23, 0);
         hoursSpinner.setEditable(false);
         hoursSpinner.setPrefWidth(70);

         Label Minute = new Label("Minuten :");

         Spinner<Integer> minutesSpinner = new Spinner<>(0, 59, 0);
         minutesSpinner.setEditable(false);
         minutesSpinner.setPrefWidth(70);

         Label KcalCalc = new Label("Dein kcal für diese Aufgabe  : ");
         kcalclac = new Text("");

         // Bestätigungs-Button
         Button activitetButton = new Button("Speichern");
         activitetButton.setOnAction(_ -> {
             try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fitnessapp", "postgres", "root")) {
                 
                 int mitarbeiterId = -1;
                 double gewicht = 0.0;
                 double bmi = 0.0;    

                 // Benutzerinformationen abrufen
                 try (PreparedStatement pstmt = conn.prepareStatement("SELECT mitarbeiter_id, aktuelle_bmi, aktuelles_gewicht FROM mitarbeiter WHERE user_name = ?")) {
                     pstmt.setString(1, currentUser.getUsername());
                     ResultSet rs = pstmt.executeQuery();
                     if (rs.next()) {
                         bmi = rs.getDouble("aktuelle_bmi");
                         gewicht = rs.getDouble("aktuelles_gewicht");
                         mitarbeiterId = rs.getInt("mitarbeiter_id");
                     } else {
                         System.err.println("Kein Benutzer gefunden!");
                         return;
                     } 
                 }

                 int aktivitaetsId = -1;
                 double met = 0.0;

                 // Nutzer muss eine Aktivität ausgewählt haben
                 String activitySelected = ActivityField.getText();
                 if (activitySelected.isEmpty()) {
                     System.err.println("Bitte eine Aktivität auswählen!");
                     return;
                 }

                 System.out.println("Gewählte Aktivität: " + activitySelected);

                 // Aktivitätsinformationen abrufen
                 try (PreparedStatement pstmt = conn.prepareStatement("SELECT aktivitaets_id, kcal_pro_minute FROM aktivitaeten WHERE aktivitaetstyp = ?")) {
                     pstmt.setString(1, activitySelected);  // ✅ Hier war vorher ein Fehler!
                     ResultSet rs = pstmt.executeQuery();
                     if (rs.next()) {
                         aktivitaetsId = rs.getInt("aktivitaets_id");
                         met = rs.getDouble("kcal_pro_minute");
                         System.out.println("Aktivität gefunden! ID: " + aktivitaetsId + ", MET: " + met);
                     } else {
                         System.err.println("Kein Aktivitätstyp gefunden!");
                         return;
                     } 
                 }

                 // Aktivitätsdauer berechnen
                 int hours = hoursSpinner.getValue();
                 int minutes = minutesSpinner.getValue();
                 double duration = hours + (minutes / 60.0);

                 // Kalorienberechnung
                 double caloriesBurned = met * gewicht * duration;
                 kcalclac.setText(String.format("Kalorien verbrannt: %.2f kcal", caloriesBurned));
                 System.out.println("Berechnete Kalorien: " + caloriesBurned);

                 // Daten in die Datenbank einfügen
                 try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO daily_log (mitarbeiter_id, datum, gewicht, bmi, aktivitaets_id, aktivitaets_dauer, kcal_pro_aktivitaet) VALUES (?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)")) {
                     pstmt.setInt(1, mitarbeiterId);
                     pstmt.setDouble(2, gewicht);
                     pstmt.setDouble(3, bmi);
                     pstmt.setInt(4, aktivitaetsId);
                     pstmt.setDouble(5, duration);
                     pstmt.setDouble(6, caloriesBurned);

                     int rowsInserted = pstmt.executeUpdate();
                     if (rowsInserted > 0) {
                         System.out.println("Daten erfolgreich aktualisiert.");
                     } else {
                         System.err.println("Fehler beim Einfügen in die Datenbank!");
                     }
                 }

             } catch (SQLException e1) {
                 System.err.println("SQL-Fehler: " + e1.getMessage());
                 e1.printStackTrace();
             }
         });

         activity.setPrefSize(600, 700);
         ActivityField.setMaxWidth(250);
         menuBar.setMaxWidth(250);
         // Alle Elemente zur VBox hinzufügen
         activity.getChildren().addAll(activityHeader, menuBar, ActivityField, Stunden, hoursSpinner, Minute, minutesSpinner, activitetButton, KcalCalc, kcalclac);

            
         
            
            
            //enter-Button Aktion
            
            
            userStats.setAlignment(Pos.TOP_LEFT);
            bmiCalc.setAlignment(Pos.CENTER);
            activity.setAlignment(Pos.CENTER);
            menulayout.setTop(menu);
            menulayout.setLeft(userStats);
            menulayout.setCenter(bmiCalc);
            
            
            // ** Aktionen für die Buttons **
            //regButton
            logoutButton.setOnAction(_ -> primaryStage.setScene(login));	//Logout Button(Abmeldung erfolgt und Szenen wecheseln zum Login-Szene)
            registButton.setOnAction(_ -> primaryStage.setScene(registration));  // Registrierungs Button (Wechsel Szene wechseln
            backButton.setOnAction(_ -> primaryStage.setScene(login));  // Zurück zur Login-Szene
 
            // ** Hauptfenster anzeigen **
            primaryStage.setScene(login);
            primaryStage.setTitle("Fitness Track App");
            primaryStage.show();
 
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
	
	public void handle(ActionEvent arg0) {
		
		
	}
}
	
