import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 */

/**
 * @author mariam, addison
 * Date: April 2021
 * Description: This class contains methods to insert, delete, and sort transaction records in the list and saves them to a file.
 * Method List:
 * 	public TransactionList() 
 * 	--> default constructor 
 * 
 * 	public int getSize()
 * 	--> getter for size
 * 
 * 	public TransactionRecord[] getList() 
 * 	--> getter for list 
 * 
 * 	public boolean insert(TransactionRecord record)
 * 	--> method to insert a record into the list 
 * 
 * 	public boolean delete (Date searchKey)
 * 	--> method to delete record 
 * 
 * 	public int binarySearch (Date specifiedDate)
 *  --> method to use binary search to search for the specified date and time
 * 
 * 	public void insertionSort ()
 * 	--> method to sort list
 * 
 * `public static void main(String[] args) throws ParseException
 * 	--> self-testing main.
 * 
 */
public class TransactionList {
	/**
	 * instance variables
	 */
	private TransactionRecord list[]; //list of transaction records
	private int maxSize;  //maximum number of records that the list can have
	private int size;  //number of records in the list at a given time

	/**
	 * default constructor
	 */
	public TransactionList() {
		this.maxSize = 100; //set maximum size to 100 records
		this.size = 0; //number of valid records 
		this.list = new TransactionRecord[maxSize];  //set the list size to the maximum size
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size; // Returns the size of the list
	}

	/**
	 * @return the list
	 */
	public TransactionRecord[] getList() {
		return list; // Returns the list
	}

	/**
	 * method to insert a record into the list
	 */
	public boolean insert(TransactionRecord record) {
		//check if the size is below max
		if(this.size < this.maxSize) {
			this.size++; //increases size by 1
			list[this.size-1] = record; //add to the record
			return true; //returns true if successful
		}
		return false; //returns false if not successful
	}

	/**
	 * Method to delete records from the list
	 * Returns a boolean
	 */
	public boolean delete (Date searchKey) {
		// Executes if the record is found
		if (binarySearch(searchKey) >= 0) {
			list[binarySearch(searchKey)] = list[size - 1]; // "Deletes" the record
			size--; // Decrease the list by one

			insertionSort(); // Sorts the records

			return true; // Record was found and deleted
		}

		return false; // Record wasn't found
	}

	/**
	 * Method to use binary search to search for the specified date and time
	 * Returns an int
	 */
	public int binarySearch (Date specifiedDate) {
		int low = 0; // Declares a variable for the lower limit of the list of records and initializes it to 0
		int high = size - 1; // Declares a variable for the upper limit of the list of records and initializes it to the size of the list
		int middle; // Declares a variable for the mid-point of the list

		insertionSort(); // Calls the insertionSort method to sort the records from oldest to newest

		// Executes if the lower limit is below the upper limit
		while (low <= high) {
			middle = (high + low) / 2; // Divides the valid array into 2

			// Executes if the record is found
			if (specifiedDate.compareTo(this.list[middle].getTransDate()) == 0) {
				return middle; // Returns the index of the record
			}

			// Executes if the record of the mid-point of the list occurs after the specified date
			else if (specifiedDate.compareTo(this.list[middle].getTransDate()) < 0) {
				high = middle - 1; // Ignore the upper side of the array
			}

			// Executes if the record of the mid-point of the list occurs before the specified date
			else {
				low = middle + 1; // Ignore the lower side of the array
			}
		}

		return -1; // Returns an invalid index (record wasn't found)
	}

	/*
	 * Method to use insertion sort to sort the array of TransactionRecords by oldest to newest
	 * Returns nothing
	 */
	public void insertionSort () {
		// Loop used to go through every record starting from the second record
		for (int top = 1; top < size; top++) {
			TransactionRecord currentRecord = this.list[top]; // Saves the current record that's being sorted into a temporary variable
			int i = top; // Variable used to store the index of the record that's being compared to the record above

			// While loop used to sort the records by oldest to newest
			while (i > 0 && currentRecord.getTransDate().compareTo(this.list[i - 1].getTransDate()) < 0) {
				this.list[i] = this.list[i - 1]; // Swaps the positions of the records
				i--; // Moves to the next record
			}

			this.list[i] = currentRecord; // Saves the current record in the correct position
		}
	}
	
	/**
	 * To String method to display the records in the list
	 * Converts the object to a String
	 */
	public String toString() {
		String list = ""; // Declares and creates a String variable for the list to be contained in. Initializes it to nothing.

		// Builds the list to be returned
		for (int i = 0; i < this.getSize(); i++) {
			list = list + "Transaction " + (i + 1) + ": " + this.list[i].toString() + "\n"; // Adds the record to the list
		}

		return list; // Returns the list
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws ParseException {
		String dateFormat = "yyyy-M-dd hh:mm:ss"; // Variable that stores the format of the date
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat); // Variable that formats a given date
		
		TransactionList listOfTransactions = new TransactionList(); // Creates a new TransactionList object
		
		TransactionRecord test = new TransactionRecord(); // Creates a TransactionRecord object
		test.processRecord("2021-04-20 02:56:43/g/Deposit/1/70000"); // Processes the record
		listOfTransactions.insert(test); // Inserts the record
		
		test = new TransactionRecord();
		test.processRecord("2001-04-20 02:56:43/g/Deposit/2/70000"); // Processes the record
		listOfTransactions.insert(test); // Inserts the record
		
		test = new TransactionRecord();
		test.processRecord("2021-04-20 07:56:43/g/Deposit/3/70000"); // Processes the record
		listOfTransactions.insert(test); // Inserts the record
		
		test = new TransactionRecord();
		test.processRecord("2020-04-20 02:56:43/g/Deposit/4/70000"); // Processes the record
		listOfTransactions.insert(test); // Inserts the record
		
		test = new TransactionRecord();
		test.processRecord("2025-04-20 02:56:43/g/Deposit/5/70000"); // Processes the record
		listOfTransactions.insert(test); // Inserts the record
		
		test = new TransactionRecord();
		test.processRecord("2021-03-20 02:56:43/g/Deposit/6/70000"); // Processes the record
		listOfTransactions.insert(test); // Inserts the record
		
		test = new TransactionRecord();
		test.processRecord("2088-04-20 02:56:43/g/Deposit/7/70000"); // Processes the record
		listOfTransactions.insert(test); // Inserts the record
		
		test = new TransactionRecord();
		test.processRecord("1999-04-20 02:56:43/g/Deposit/8/70000"); // Processes the record
		listOfTransactions.insert(test); // Inserts the record
		
		test = new TransactionRecord();
		test.processRecord("2021-07-20 02:56:43/g/Deposit/9/70000"); // Processes the record
		listOfTransactions.insert(test); // Inserts the record
		
		test = new TransactionRecord();
		test.processRecord("2000-04-20 02:56:43/g/Deposit/10/70000"); // Processes the record
		listOfTransactions.insert(test); // Inserts the record

		listOfTransactions.insertionSort(); // Sorts the records
		
		System.out.println(listOfTransactions.toString()); // Displays the records
		
		System.out.println(""); // Formats for readability

		// Displays the index of the specified date
		System.out.println(listOfTransactions.binarySearch(simpleDateFormat.parse("1999-04-20 02:56:43")));
		System.out.println(listOfTransactions.binarySearch(simpleDateFormat.parse("2088-04-20 02:56:43")));
		System.out.println(listOfTransactions.binarySearch(simpleDateFormat.parse("2021-07-20 02:56:43")));
		System.out.println(listOfTransactions.binarySearch(simpleDateFormat.parse("2021-12-20 02:56:43")));
		
		System.out.println(""); // Formats for readability
		
		listOfTransactions.delete(simpleDateFormat.parse("1999-04-20 02:56:43")); // Deletes the record with the specified date
		listOfTransactions.insertionSort(); // Sorts the records
		
		System.out.println(listOfTransactions.toString()); // Displays the records
		
	}

}
