package application;

public class User {
		private String passwort;
		private String username;
	    private String firstName;
	    private String lastName;
	    private double height;
	    private double weight;
	    private double bmi;

	    // Konstruktor zum Initialisieren der Benutzerinformationen
	    public User() {}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public double getHeight() {
			return height;
		}

		public void setHeight(double height) {
			this.height = height;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}
	        
		public double getBMI() {
			return bmi;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPasswort() {
			return passwort;
		}

		public void setPasswort(String passwort) {
			this.passwort = passwort;
		}
		
	    
	}

