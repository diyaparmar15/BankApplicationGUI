import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 */

/**
 * @author mariam
 * Date: April 2021
 * Description: This class holds transaction records. It takes in a record String formatted like: accType/transType/startBalance/endBalance 
 * 				and converts it to display in a user-friendly way.
 * Method List:
 * 	public TransactionRecord()
 * 	--> default constructor
 * 
 * 	public TransactionRecord(String input)
 * 	--> overloaded constructor. sets instance data to data from the input string
 * 
 * 	public void processRecord(String input)
 * 	--> method that splits the String and adds the data into an array.
 * 
 * 	getters and setters for all instance variables.
 * 
 * 	public String toString()
 * 	--> toString method that returns the new formatted record in a string and converts the accType to its appropriate word. 
 * 		i.e. g --> general investment certificate, s --> savings, anything else --> invalid.
 * 
 * 	public static void main(String[] args)
 * 	--> self-testing main
 */
public class TransactionRecord {
	/**
	 * instance variables
	 */
	private char accType; //char for account type (gic or savings)
	private String transType; //string for transaction type (deposit or withdrawal)
	private double startBalance, endBalance; //customer's starting and end balances
	private Date transDate;  //date of transaction
	private String dateFormat;
	private DateFormat simpleDateFormat;

	/**
	 * default constructor
	 */
	public TransactionRecord() {
		//initialize all instance variables
		this.accType = ' ';
		this.transType = "";
		this.startBalance = 0;
		this.endBalance = 0;
		this.transDate = null; 
	}

	/**
	 * overloaded constructor
	 */
	public TransactionRecord(String input) throws ParseException {
		//call process method 
		processRecord(input);

		//set instance variables to the data from the string
		this.accType = getAccType(); 
		this.transType = getTransType(); 
		this.startBalance = getStartBalance(); 
		this.endBalance = getEndBalance();
		this.transDate = getTransDate(); 
	}


	/**
	 * method that splits the record String
	 * @param record
	 */

	public void processRecord(String record) throws ParseException {
		this.dateFormat = "yyyy-M-dd hh:mm:ss";
		this.simpleDateFormat = new SimpleDateFormat(dateFormat);
		
		//splits input string
		String words[]; //array to store words from the split string
		words = record.split("/"); //split string at each slash

		//store each variable in its own element in the array
		this.transDate = simpleDateFormat.parse(words[0]);
		this.accType = words[1].charAt(0); 
		this.transType = words[2];
		this.startBalance = Double.parseDouble(words[3]);
		this.endBalance = Double.parseDouble(words[4]); 
	}

	//***** getters *****//
	/**
	 * @return the accType
	 */
	public char getAccType() {
		return accType;
	}


	/**
	 * @return the transType
	 */
	public String getTransType() {
		return transType;
	}

	/**
	 * @return the startBalance
	 */
	public double getStartBalance() {
		return startBalance;
	}
	

	/**
	 * @return the endBalance
	 */
	public double getEndBalance() {
		return endBalance;
	}
	

	/**
	 * @return the transDate
	 */
	public Date getTransDate() {
		return transDate;
	}

	//***** setters *****//
	
	/**
	 * @param accType the accType to set
	 */
	public void setAccType(char accType) {
		this.accType = accType;
	}
	/**
	 * @param transDate the transDate to set
	 */
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	/**
	 * @param transType the transType to set
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}


	/**
	 * @param startBalance the startBalance to set
	 */
	public void setStartBalance(double startBalance) {
		this.startBalance = startBalance;
	}


	/**
	 * @param endBalance the endBalance to set
	 */
	public void setEndBalance(double endBalance) {
		this.endBalance = endBalance;
	}

	/**
	 * toString method to convert the string properly
	 */

	public String toString() {
		String accountType; //variable for the account type

		//switch case to determine the account type
		switch(accType) {
		case 'g': {
			accountType = "General Investment Certificate";
			break;
		}
		case 's':{
			accountType = "Savings";
			break;
		}
		default:{
			accountType = "Invalid";
			break;
		}
		}//end of switch

		//output record
		return "Date and Time: " + getTransDate() + ", Account: " + accountType + ", Transaction Type: " + getTransType() 
		+ ", Previous Balance: " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(getStartBalance()) 
		+ ", New Balance: " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(getEndBalance());  
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws ParseException {
		//testing
		String test = "2021-04-20 02:56:43/g/Deposit/30000/70000";

		//create object 
		TransactionRecord tr = new TransactionRecord(); 

		//test default constructor (should be empty)
		System.out.println(tr.toString());
		//works

		System.out.println("\n"); //to separate

		//process string
		tr.processRecord(test);

		//test toString method
		System.out.println(tr.toString()); 
		//works

		System.out.println("\n"); //to separate

		//test overloaded constructor 
		TransactionRecord oc = new TransactionRecord(test);

		System.out.println(oc.toString());
		//works
		
		System.out.println("\n"); //to separate

		//test setters 

		tr.setAccType('s');
		tr.setTransType("Withdraw");
		tr.setStartBalance(4000);
		tr.setEndBalance(3000);

		System.out.println(tr.toString());
		//works
		
		/*
		String pattern = "yyyy-M-dd hh:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date date = null, date2 = null;
		try {
			date = simpleDateFormat.parse(String.valueOf(LocalDate.now()) + " " + String.valueOf(LocalTime.now().truncatedTo(ChronoUnit.SECONDS)));
			date2 = simpleDateFormat.parse("2021-04-20 02:56:43");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(date);
		
		System.out.println(LocalDate.now());
		System.out.println(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
		
		System.out.println(date.compareTo(date2));
		*/

	}

}