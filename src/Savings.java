import java.text.NumberFormat;
import java.util.Locale;

/**
 * 
 */

/**
 * @author diyaparmar
 * Date: April 2021
 * 
 * Description: The Savings class inherits from the Account class. Being a type of account,
 * it has a special condition which is, if the balance of the account is below $4000, each withdrawal
 * transaction will be charged a fee ($4.25) otherwise it will follow the same procedure as a normal account
 * 
 * Methods:
 * Getter and Setter method for the fee variable
 * --> public double getFee()
 * --> public void setFee(double fee)
 * --> public double getMinBalance()
 * --> public void setMinBalance(double minBalance)
 * --> public boolean withdraw (double amount)
 * 
 * Withdrawing method using the withdraw method made in Account class passes in an amount the customer
 * wants to withdraw and firstly checks if the amount passed in is less than or equal to the balance and 
 * if the balance is greater than or equal to the minimum balance. If so, then withdraw the amount without 
 * any fees. However, if the amount is less than or equal to the balance and the balance is less than the
 * minimum balance, then we would withdraw the amount passed in plus the fee ($4.25)
 * 
 * --> public static void main(String[] args)
 * Self - Testing main
 *
 */
public class Savings extends Account {

	//Instance data: variable for fee and minimum balance (specific to the savings class)
	private double fee; 
	private double minBalance;

	/**
	 * Default Constructor 
	 */
	public Savings() {

		//call the parent constructor (Account)
		super();

		//Initialize the data (fee, minimum balance and general balance)
		this.fee = 0;
		this.balance = 0;
		this.minBalance = 0;
	}

	/**
	 * Overloaded Constructor
	 */
	public Savings(String data) {

		//call the parent constructor (Account)
		super(data);

		//Initialize the data to what it is equal to
		this.fee = 4.25;
		this.minBalance = 4000;
	}

	/**
	 * @return the fee
	 */
	public double getFee() {
		return fee;
	}


	/**
	 * @param fee the fee to set
	 */
	public void setFee(double fee) {
		this.fee = fee;
	}


	/**
	 * @return the minBalance
	 */
	public double getMinBalance() {
		return minBalance;
	}

	/**
	 * @param minBalance the minBalance to set
	 */
	public void setMinBalance(double minBalance) {
		this.minBalance = minBalance;
	}

	/**
	 * Extension of the withdraw method from the Account class to deduct the fee
	 * when the balance is less than $4000, otherwise withdraw without a deduction
	 */

	public boolean withdraw (double amount) {

		//check amount passed in is less than or equal to the balance and 
		//if the balance is greater than or equal to the minimum balance 
		if (amount <= balance && balance >= minBalance) {
			super.withdraw(amount); //call withdraw method from account and withdraw the amount passed in
			return true;  
		}

		//otherwise if  the amount is less than or equal to the balance and
		//the balance is less than the minimum balance.
		else if (amount <= balance && balance < minBalance) {
			super.withdraw(amount + fee); //call the withdraw method and add in the fee to be deducted
			return true;
		}
		return false; //the transaction did not go through, return false 
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//Create an object of the class
		Savings transaction = new Savings();

		System.out.println(transaction.toString());

		//display the current balance (should be $0.00)
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(transaction.getBalance()));

		//test getter for fee
		System.out.println(transaction.getFee());

		//test getter for minimum balance
		System.out.println(transaction.getMinBalance());

		//test overloaded constructor
		transaction = new Savings("Burgers/AreTasty/Burger King/123 Fake St./905-123-4567/709323001672/100.0");
		System.out.println(transaction.toString());	

		//display the current balance (should be $0.00)
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(transaction.getBalance()));

		//deposit an amount
		transaction.deposit(4900);

		//should print $100 as the account already has $100 in the account.
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(transaction.getBalance()));

		//display the result of calling the withdrawS method for withdrawing $4400.50)
		System.out.println("Money Withdrawn: "+ transaction.withdraw(4400.50));

		//test getter for minimum balance
		System.out.println(transaction.getMinBalance());

		//display the updated balance
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(transaction.getBalance()));

		//display the result of calling the withdrawS method for withdrawing the following amount
		System.out.println("Money Withdrawn: "+ transaction.withdraw(400.50));

		//display the updated balance (599.50 - 400.50 - 4.25 = 194.75)
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(transaction.getBalance()));

		//test setter
		transaction.setFee(5);

		//display the result of calling the withdrawS method for withdrawing the following amount
		System.out.println("Money Withdrawn: "+ transaction.withdraw(50.50));

		//display the updated balance (190.25 - 50.50 = 140.00 - fee($5.00) = 135, SETTER WORKS
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(transaction.getBalance()));

		//check result if withdrawing amount is greater than the current balance 
		//(should display false and balance should remain the same) - means transaction did not go through
		System.out.println("Money Withdrawn: "+ transaction.withdraw(200.50));
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(transaction.getBalance()));



	}
}