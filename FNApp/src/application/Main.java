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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;

import javax.swing.text.Position;

import javafx.*;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//HBox mit Bild 
			HBox pic = new HBox();
			Image image = new Image(getClass().getResource("/resources/smartwatch.jpg").toExternalForm());
			ImageView imageview = new ImageView(image);
			imageview.setFitWidth(350);
			imageview.setFitHeight(600);
			//imageview.setPreserveRatio(true);
			pic.getChildren().add(imageview);
			
			//Überschrift 
			VBox intro = new VBox();
			Text h1 = new Text("Willkommen\n "
					+ "Die App die dich Fit hält");
			h1.setFill(Color.BLACK);
			h1.setFont(new Font(20));
			h1.setTextAlignment(TextAlignment.CENTER);
			
			
			
			
			//VBox mit label und Textfeld
			VBox userInput = new VBox(10);
			Label username = new Label("Username: ");
			TextField uName = new TextField();
			uName.setMaxWidth(200);
			uName.getStyleClass().add("custom-text-field");
			Label password = new Label("Passwort: ");
			PasswordField pWord = new PasswordField();
			pWord.setMaxWidth(200);
			pWord.getStyleClass().add("custom-password-field");
			userInput.setAlignment(Pos.TOP_CENTER);
			
			
			// Button zum Absenden
            Button submitButton = new Button("Anmelden");
           //button
			
			// 
            intro.getChildren().addAll(h1);
            
            //Fügt die Elemente der VBOX hinzu
            userInput.getChildren().addAll(h1,username, uName,password, pWord, submitButton);
            
            
			//Hauptlayout
			BorderPane root = new BorderPane();
			root.setLeft(pic);
			root.setCenter(userInput);
			
			//Szene erstellen
			Scene login = new Scene(root,800,600);
			login.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(login);
			primaryStage.show();
			primaryStage.setTitle("Fitness Track App");
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
