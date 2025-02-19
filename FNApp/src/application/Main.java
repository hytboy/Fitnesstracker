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
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TabPane;

import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;

import application.User;
import database.DataBaseCon.PostgreSQLConnection;


public class Main extends Application implements EventHandler<ActionEvent>{

	private Stage primaryStage;
    private Scene registration;  // Deklaration der Szene für Registrierung außerhalb der start()-Methode
    private Scene mainMenu;	
    
    private Label uStatVorname,uStatNachname,uStatheight, uStatweight;
    private Label errorMessage;
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
          
            
            //Szene Erstellen
            /*BorderPane menulayout = new BorderPane();
            mainMenu = new Scene(menulayout,800, 700);
            VBox userStats = new VBox();
            Label uStatVorname = new Label("Vorname:");
            uStatVorname.getStyleClass().add("custom-label-menu");
            Label uStatNachname = new Label("Nachname: \n");
            uStatNachname.getStyleClass().add("custom-label-menu");
            Label uStatheight = new Label("Größe: \n");
            uStatheight.getStyleClass().add("custom-label-menu");
            Label uStatweight = new Label("Gewicht: \n");
            uStatweight.getStyleClass().add("custom-label-menu");
            
            TabPane menuPane = new TabPane();
            Tab tab1 = new Tab("BMI-Calc");
            Tab tab2 = new Tab("Activitys");
            Tab tab3 = new Tab("Kcal-Calc");
            
         
            menuPane.getTabs().addAll(tab1, tab2, tab3);
            menulayout.setCenter(menuPane);
            menulayout.setLeft(userStats);*/
            
            BorderPane menulayout = new BorderPane();
            mainMenu = new Scene(menulayout,800, 700);
            mainMenu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            menulayout.getStyleClass().add("mainMenu");
            
            
            
            HBox menu = new HBox();
            MenuBar menubar = new MenuBar();
            Menu BMICalc = new Menu("BMI-Rechner");
            Menu activity = new Menu("Aktivität");
            Menu kcalCalc = new Menu("KCal-Berechnen");
            menubar.getMenus().addAll(BMICalc,activity,kcalCalc);
            menubar.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            Button logoutButton = new Button("Logout");
            menu.setAlignment(Pos.CENTER);
            menu.getChildren().addAll(menubar,logoutButton);

            VBox userStats = new VBox();
            Label uStatVorname = new Label("Vorname:");
            uStatVorname.getStyleClass().add("custom-label-menu");
            Label uStatNachname = new Label("Nachname: \n");
            uStatNachname.getStyleClass().add("custom-label-menu");
            Label uStatheight = new Label("Größe: \n");
            uStatheight.getStyleClass().add("custom-label-menu");
            Label uStatweight = new Label("Gewicht: \n");
            uStatweight.getStyleClass().add("custom-label-menu");
            
            userStats.setPrefWidth(200);
            userStats.setBorder(new Border(new BorderStroke(
            		Color.BLACK,
            		BorderStrokeStyle.SOLID, 
            		new CornerRadii(0),
            		new BorderWidths(2)
            )
            		)
            		);
            VBox bmiCalc = new VBox();
            
            Text bmiCalcHeader = new Text("BMI-Calculator");
            bmiCalcHeader.getStyleClass().add("h1");
            Label yourWeight = new Label("Dein aktuelles Gewicht: \n");
            TextField yWeight = new TextField();
            yWeight.getStyleClass().add("TextField");
            Label yourHeight = new Label("Deine Größe(cm): \n");
            TextField yHeight = new TextField();
            yHeight.getStyleClass().add("TextField");
            bmiCalcHeader.set
            
            
            userStats.getChildren().addAll(uStatVorname,uStatNachname,uStatheight,uStatweight);
            bmiCalc.getChildren().addAll(bmiCalcHeader, yourWeight, yWeight, yourHeight, yHeight);
            userStats.setAlignment(Pos.TOP_LEFT);
            menulayout.setTop(menu);
            menulayout.setLeft(userStats);
            menulayout.setCenter(bmiCalc);
            
            
            // ** Aktionen für die Buttons **
            //regButton
            logoutButton.setOnAction(e -> primaryStage.setScene(login));	//Logout Button(Abmeldung erfolgt und Szenen wecheseln zum Login-Szene)
            submitButton.setOnAction(e -> primaryStage.setScene(mainMenu));
            //submitButton.setOnAction(e -> handleLogin(uName.getText(),pWord.getText()));
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

	
	public void handleLogin(String uName, String pWord) {
		User user = PostgreSQLConnection.authenticate(uName, pWord);
		
		if (user != null) {
            
			// Benutzerstatistiken aktualisieren
            uStatVorname.setText("Vorname: " + user.getFirstName());
            uStatNachname.setText("Nachname: " + user.getLastName());
            uStatheight.setText("Größe: " + user.getHeight() + " cm");
            uStatweight.setText("Gewicht: " + user.getWeight() + " kg");
        
            // In das Hauptmenü wechseln
            primaryStage.setScene(mainMenu);
            errorMessage.setVisible(false);
        } else {
        	
        }
    }
	public static User authenticate(String uName, String pWord) {
	    String sql = "SELECT * FROM mitarbeiter WHERE user_name = ? AND passwort = ?";
	    try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Fitnesstracker", "postgres", "vn7791g782K!");
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, uName);
	        pstmt.setString(2, pWord);
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // Falls Benutzer nicht gefunden oder Fehler
	}
	
}
	

