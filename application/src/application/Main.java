package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Label resultLabel;  // Label für die Anzeige des Ergebnisses
    private TextField userNameField;  // Textfeld für die Eingabe des Benutzernamens
    private Label questionLabel;  // Label für die Frage

    @Override
    public void start(Stage primaryStage) {
        // Frage-Label: "Geben Sie Ihren Benutzernamen ein"
        questionLabel = new Label("Geben Sie Ihren Benutzernamen ein:");

        // Textfeld für die Eingabe des Benutzernamens
        userNameField = new TextField();
        userNameField.setPromptText("Benutzernamen hier eingeben");

        // Button für die Suche
        Button searchButton = new Button("Daten suchen");

        // Label für das Ergebnis
        resultLabel = new Label();

        // Button-Aktion: Benutzernamen abfragen und Daten anzeigen
        searchButton.setOnAction(e -> {
            String userName = userNameField.getText().trim();
            if (!userName.isEmpty()) {
                String result = DatabaseConnection.searchByUserName(userName);
                appendToTextArea(result);
            } else {
                appendToTextArea("Bitte geben Sie einen Benutzernamen ein.");
            }
        });

        // Layout: VBox für vertikale Anordnung der Elemente
        VBox root = new VBox(10);
        root.getChildren().addAll(questionLabel, userNameField, searchButton, resultLabel);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mitarbeiter-Datenbank");
        primaryStage.show();
    }

    // Methode, um Text im resultLabel anzuzeigen
    private void appendToTextArea(String message) {
        Platform.runLater(() -> resultLabel.setText(message));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
