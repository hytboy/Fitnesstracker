package application;

public class User {
    private String firstName;
    private String lastName;
    private double height;
    private double weight;
    private double bmi;

    // Konstruktor zum Initialisieren der Benutzerinformationen
    public User(String firstName, String lastName, double height, double weight, double bmi) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
    }

    // Getter-Methoden für die Attribute
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getBmi() {
        return bmi;
    }
}
