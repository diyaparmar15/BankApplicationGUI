import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

/**
 * @author mariam
 * Date: April 2021
 * Description: This class inherits the data and methods of the ÏAccountÓ class. It is the GIC account that is created for every customer.
 * 				This class includes an investment date (date when money is added to the account) and maturity date (30 days after the
 * 				investment). It also includes an interest rate and penalty rate. The interest rate is added to the withdrawal amount if the
 * 				withdraw happens after the maturity date. If the withdraw is before the maturity date, a penalty is given to the customer.
 * 
 * Method List:
 * 	public GIC()
 * 	--> default constructor. calls parent constructor and initializes data
 * 
 *  public GIC(Customer client, double interest, double penaltyRate)
 * 	--> overloaded constructor. takes in a customer object, the interest rate, and penalty rate. calls parent constructor with customer
 * 		data that was passed. initializes the interest and penalty rates with the information passed in. initializes the investment date 
 * 		to the current date and the mature date 30 days from the investment date
 * 
 * 	public boolean withdrawal (double amt)
 * 	--> withdraw method that overloads the Account withdraw method. if the withdraw happens after the maturity date, interest is added to
 * 		the amount. if the withdraw happens before the maturity date, a penalty rate is added to the amount. returns true if either action
 * 		is successful, returns false if either weren't.
 * 
 * 	Setters & Getters for all instance variables (8 methods total). 
 * 
 * 	public String toString()
 * 	--> toString method
 * 	
 * 	public static void main (String args[])
 * 	--> self-testing main method
 * 
 * Code Credit:
 * (1): https://docs.oracle.com/javase/9/docs/api/java/time/LocalDate.html
 * 
 */

public class GIC extends Account {
	/**
	 * instance data 
	 */
	private double interestRate, penaltyRate; //variables that contain the interest rate and penalty rate
	private LocalDate matureDate, investDate;  //variables for the investment date and the maturity date 

	/**
	 * default constructor
	 */
	public GIC(){
		super(); //calling parent constructor (Account)

		//Initialize all data 
		this.interestRate = 0; 
		this.penaltyRate = 0;
		this.investDate = null;
		this.matureDate = null;
	}

	/**
	 * overloaded constructor 
	 */
	public GIC (String data, double interestRate, double penaltyRate) {
		super(data); //calling parent constructor (Account) with customer data

		//Initialize all data 
		this.interestRate = interestRate; //interestRate; 
		this.penaltyRate = penaltyRate; //penaltyRate;
		//code credit: (1)
		this.investDate = LocalDate.now(); //takes the current date (yyyy-mm-dd)
		this.matureDate = LocalDate.now().plusMonths(1); //adds a month to the current date (30 days)
	}

	/**
	 * extension of withdraw method from Account that adds interest or penalty on withdraw amount
	 * @param amt
	 * @return
	 */

	public boolean withdraw (double amt){	
		//if the withdraw happens on or after the maturity date, add interest rate
		if(investDate.isAfter(matureDate) || investDate.equals(matureDate)) {
			return super.withdraw(amt + (amt * interestRate)); //return the new balance
		}
		//if the withdraw happens before the maturity date, add penalty
		else if(investDate.isBefore(matureDate)) {
			return super.withdraw(amt + (amt * penaltyRate)); //return the new balance
		}
		//return false if operation was unsuccessful (insufficient funds)
		return false; 
	}

	/**
	 * @return the matureDate
	 */
	public LocalDate getMatureDate() {
		return matureDate;
	}

	/**
	 * @param matureDate the matureDate to set
	 */
	public void setMatureDate(LocalDate matureDate) {
		this.matureDate = matureDate;
	}

	/**
	 * @return the investDate
	 */
	public LocalDate getInvestDate() {
		return investDate;
	}

	/**
	 * @param investDate the investDate to set
	 */
	public void setInvestDate(LocalDate investDate) {
		this.investDate = investDate;
	}

	/**
	 * @return the interestRate
	 */
	public double getInterestRate() {
		return interestRate;
	}


	/**
	 * @param interestRate the interestRate to set
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * @param penaltyRate the penaltyRate to set
	 */
	public void setPenaltyRate(double penaltyRate) {
		this.penaltyRate = penaltyRate;
	}

	/**
	 * @return the penaltyRate
	 */
	public double getPenaltyRate() {
		return penaltyRate;
	}

	/**
	 * toString method
	 */
	public String toString() {
		return super.toString() + "/ \nInterest Rate: " + getInterestRate() + ", Penalty Rate: " + getPenaltyRate() 
		+ ", Investment Date: " + getInvestDate() + ", Maturity Date: " + getMatureDate(); 
	} 

	/**
	 * self-testing main
	 * @param args
	 */

	public static void main (String args[]){

		//**** testing default constructor. 
		//should be empty/display 0s or nulls. 
		//also tests getters.

		//create a GIC object
		GIC test = new GIC(); 

		System.out.println(test.toString());
		//works

		System.out.println("\n");

		//**** testing overloaded constructor.
		//should display 5.6 as interest rate, 7.8 as penalty rate, todays date as the investment date
		//and 30 days from now as maturity date

		//create customer string
		String example = "Burgers/AreTasty/Burger King/123 Fake St./905-123-4567/709323001672/100.0";

		test = new GIC(example, 0.056, 0.078); 

		System.out.println(test.toString());
		//works

		System.out.println("\n");

		//***** testing withdraw method
		test.deposit(900); //depositing $900 in the account ($1000 total in account)

		test.withdraw(50); //withdraw $50 from account

		//display balance. should return $946.10 (with 7.8% penalty)
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(test.getBalance())); //works

		System.out.println("\n");

		//testing if interest rate is applied properly
		test.deposit(53.9); //bring account balance back to $1000 

		test.setMatureDate(LocalDate.now().minusDays(1)); //set mature date to yesterday

		test.withdraw(50); //withdraw 50 from account

		//display balance. should return $947.20 (5.6% from the interest)
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(test.getBalance())); //works

		System.out.println("\n");

		//test if withdraw happens on maturity date
		test.setMatureDate(LocalDate.now().plusDays(1)); //set mature date to today 

		test.withdraw(50); //withdraw 50 from account

		//display balance. should return $893.3 (5.6% from the interest)
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(test.getBalance())); //works

		System.out.println("\n");

		//testing unsuccessful withdraw
		//should return false
		System.out.println(test.withdraw(10000000)); //works

		System.out.println("\n");

		//***** testing setters
		test.setInterestRate(0.3);
		test.setPenaltyRate(2.0);
		test.setInvestDate(LocalDate.now().minusDays(20));  //investment date 20 days ago
		test.setMatureDate(LocalDate.now().plusMonths(2)); //maturity date in 2 months from now

		System.out.println(test.toString());
		//works

	}


}