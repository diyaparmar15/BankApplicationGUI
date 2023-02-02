import java.text.NumberFormat;
import java.util.Locale;

/**
 * 
 */

/**
 * @author Addison
 * Date: Apr. 2021
 * Description: Contains the customer's balance, account number, and personal information. Also enables the GIC and Savings classes to deposit and withdraw money. Account numbers are also generated in this class.
 * Method List:
 * 			public static void main(String[] args)
 * 			public Account(): Default Constructor
 * 			public Account (String data): Overloaded Constructor
 * 			public double getBalance(): Gets the account balance
 * 			public String getAccountNumber(): Gets the account number
 * 			public void setClient(Customer client): Sets the data for the customer
 * 			public void deposit (double amount): Deposits money into an account
 * 			public boolean withdraw (double amount): Withdraws money from an account
 * 			public String toString(): Converts the object into a String. Formatted to be saved to a file.
 *
 */
public class Account {

	/**
	 * Instance Data
	 */
	double balance; // Variable that contains the customer's account balance
	String accountNumber; // Variable that contains the customer's account number
	Customer client; // Creates a variable that contains the customer's personal information

	/**
	 * Default Constructor
	 */
	public Account() {
		// Initializes instance variables
		this.balance = 0; // Initializes the account balance to 0
		this.accountNumber = ""; // Initializes the account number to nothing to remove the null value inside the variable (for formatting purposes)

		// For loop used to generate a 12 digit account number
		for (int i = 0; i < 12; i++) {
			this.accountNumber = this.accountNumber + (int) (Math.random() * 10); // Generates a random number between 1-9 and adds it to the account number
		}

		client = new Customer(); // Initializes the data for the object
	}

	/**
	 * Overloaded Constructor
	 */
	public Account (String data) {
		String info[]; // Declares a String variable to store the data that's passed in
		
		info = data.split("/"); // Processes the String into its individual pieces of data
		
		// Initializes instance variables
		this.balance = Double.parseDouble(info[6]); // Initializes the account balance to the value from the passed in data
		this.accountNumber = info[5]; // Initializes the account number to the account number from the passed in data
		
		client = new Customer(data.substring(0, data.lastIndexOf("/", data.lastIndexOf("/") - 1))); // Creates a new Customer object with the data that's passed in
	}
	
	/**
	 * @return the balance
	 * Method to get the account balance
	 */
	public double getBalance() {
		return balance; // Returns the account balance
	}

	/**
	 * @return the accountNumber
	 * Method to get the account number
	 */
	public String getAccountNumber() {
		return accountNumber; // Returns the account number
	}

	/**
	 * @param client the client to set
	 * Method to set the data for the customer
	 */
	public void setClient(Customer client) {
		this.client = client; // Sets the data
	}

	/**
	 * Method to deposit money into an account
	 */
	public void deposit (double amount) {
		this.balance = this.balance + amount; // Adds the amount that the user deposited into their account balance
	}

	/**
	 * Method to withdraw money from an account
	 */
	public boolean withdraw (double amount) {
		// Executes if the amount that the user wishes to withdraw is greater than their balance
		if (amount > this.balance) {
			return false; // Withdrawal failed
		}
		
		// Executes if the amount that the user wishes to withdraw is less than or equal to their account balance balance
		else {
			this.balance = this.balance - amount; // Subtracts the amount from their account balance
			return true; // Successful withdrawal
		}
	}
	
	/**
	 * Method to convert the object into a String. Formatted to be saved to a file.
	 * Returns a String
	 */
	public String toString() {
		return client + "/" + getAccountNumber() + "/" + getBalance();
	}
	
	/**
	 * @param args
	 * Self-testing main
	 */
	public static void main(String[] args) {
		Account test = new Account(); // Creates a new account object
		
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(test.getBalance())); // Displays the balance in the account
		
		System.out.println(test.getAccountNumber()); // Displays the account number
		
		test.deposit(100); // Deposits $100 into the account
		
		// Displays the balance in the account to see if the amount was successfully deposited
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(test.getBalance()));
		
		
		System.out.println("Money withdrawn: " + test.withdraw(50)); // Displays if the $50 was successfully withdrawn (should be true)
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(test.getBalance())); // Displays the balance in the account
		
		
		System.out.println("Money withdrawn: " + test.withdraw(500)); // Displays if the $500 was successfully withdrawn (should be false)
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(test.getBalance())); // Displays the balance in the account
		
		Customer example = new Customer ("Username/Password123/Bob/123 Fake St./905-123-4567"); // Creates a Customer object
		
		test.setClient(example); // Sets the customer data in the account
		
		System.out.println(test.toString()); // Displays the data in the account
		
		String example2 = "SomeUsername/Password321/Jeff/321 Fake St./905-321-7654"; // Creates a String that contains the data for a customer
		
		Account test2 = new Account(example2 + "/012345678912/77.0"); // Creates a new account object using the overloaded constructor
		
		System.out.println(test2.toString()); // Displays the data in the account
		
		System.out.println(test2.client.getUserName()); // Displays the data in the account

	}

}