import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 
 */

/**
 * @author Addison
 * Date: Apr. 2021
 * Description: Contains methods to access files to retrieve, store, and sort data.
 * Method List:
 * 			public static void main(String[] args)
 * 			public static String[] loadData (String accountType): Loads the account data from a file
 * 			public static void writeData (String accountType, String newData): Adds data to a file
 * 			public static void writeData (String accountType, String newData, String username): Updates existing data in a file
 * 			public static void writeData (String filename, TransactionRecord record, TransactionList recordList, boolean addTransaction): Writes transactions to files
 * 			public static void sortFileData (String accountType): Uses shell sort to sort the data in the file alphabetically by the usernames
 * 			public static String findData (String[] dataList, String username, String password, int low, int high, int middle): Uses binary search to find an account. Uses recursion.
 * 			public static boolean findData (String[] dataList, String username, int low, int high, int middle): Uses binary search to find if an account with the desired username exists. Uses recursion.
 * 			public static void createFile (String filename): Creates a new file
 *
 */
public class FileAccess {

	/**
	 * Method to load the account data from a file
	 * Returns a String array
	 */
	public static String[] loadData (String accountType) throws IOException {
		// Opens the file for reading
		FileReader accountsFile = new FileReader(accountType + "File.txt");
		BufferedReader accountsFileInput = new BufferedReader (accountsFile);

		String fileSize = accountsFileInput.readLine(); // Creates a variable to store the size of the file and initializes it to the first line in the file

		String[] dataList = new String [Integer.parseInt(fileSize) + 1]; // Creates a String array to store the data in the file

		dataList[0] = fileSize; // Sets the data at index 0 to the size of the file

		// For loop used to read the file and add the data to the array
		for (int i = 1; i < dataList.length; i++) {
			dataList[i] = accountsFileInput.readLine(); // Reads the data adds it to the array
		}

		accountsFileInput.close(); // Closes the file

		return dataList; // Returns the array
	}

	/**
	 * Method to add data to a file
	 * Returns nothing
	 */
	public static void writeData (String accountType, String newData) throws IOException {
		String[] fileData = loadData(accountType); // Creates a String array and calls the loadData method to set the data in the array

		fileData[0] = String.valueOf(Integer.parseInt(fileData[0]) + 1); // Reads the size of the file and increases it by 1

		// Opens the file to write to
		FileWriter sortedAccountsFile = new FileWriter(accountType + "File.txt");
		PrintWriter sortedAccountsFileOutput = new PrintWriter(sortedAccountsFile);

		// For loop used to write the old data in the file
		for (int i = 0; i < fileData.length; i++) {
			sortedAccountsFileOutput.println(fileData[i]); // Writes the old data into the file
		}

		sortedAccountsFileOutput.println(newData); // Writes the new data into the file

		sortedAccountsFileOutput.close(); // Closes the file

		sortFileData(accountType); // Calls the sortFileData method to sort the data in the file
	}

	/**
	 * Method to update existing data in a file
	 * Returns nothing
	 * Note: Sorting isn't needed because it's updating data in an already sorted file without changing the positions of any of the data
	 */
	public static void writeData (String accountType, String newData, String username) throws IOException {
		String[] fileData = loadData(accountType); // Creates a String array and calls the loadData method to set the data in the array
		DecimalFormat twoDecimals = new DecimalFormat("0.00"); // Formats a number to have 2 decimals

		// Opens the file to write to
		FileWriter sortedAccountsFile = new FileWriter(accountType + "File.txt");
		PrintWriter sortedAccountsFileOutput = new PrintWriter(sortedAccountsFile);

		Account tempAccount = new Account(newData); // Creates a new Account object and passes in the new data

		// For loop used to write data in the file
		for (int i = 0; i < fileData.length; i++) {
			// Executes if "i" isn't 0. Needed to prevent an error in line 104.
			if (i != 0) {
				// Executes if the account in the current array has the same username as the specified username
				if (fileData[i].substring(0, fileData[i].indexOf('/')).equalsIgnoreCase(username)) {
					sortedAccountsFileOutput.println(tempAccount.client + "/" + tempAccount.getAccountNumber() + "/" + twoDecimals.format(tempAccount.getBalance())); // Updates the data in the account
				}

				// Executes if the account in the current array doesn't have the same username as the specified username
				else {
					sortedAccountsFileOutput.println(fileData[i]); // Writes the data into the file
				}
			}

			// Executes if "i" is 0
			else {
				sortedAccountsFileOutput.println(fileData[i]); // Writes the data into the file
			}
		}

		sortedAccountsFileOutput.close(); // Closes the file

	}

	/**
	 * Method to write transactions to files
	 * Returns nothing
	 */
	public static void writeData (String filename, TransactionRecord record, TransactionList recordList, boolean addTransaction) throws IOException {
		DecimalFormat twoDecimals = new DecimalFormat("0.00"); // Formats a number to have 2 decimals
		DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss"); // Formats a date into a String in the format of "yyyy-M-dd hh:mm:ss"

		// Executes if addTransaction is true. Indicates that the user wants to add a Transaction Record to a file.
		if (addTransaction == true) {
			String[] fileData = loadData(filename); // Creates a String array and calls the loadData method to set the data in the array

			fileData[0] = String.valueOf(Integer.parseInt(fileData[0]) + 1); // Reads the size of the file and increases it by 1

			// Opens the file to write to
			FileWriter transactionFile = new FileWriter(filename + "File.txt");
			PrintWriter transactionFileOutput = new PrintWriter(transactionFile);

			// For loop used to write the old data in the file
			for (int i = 0; i < fileData.length; i++) {
				transactionFileOutput.println(fileData[i]); // Writes the old data into the file
			}

			transactionFileOutput.println(dateFormat.format(record.getTransDate()) + "/" + record.getAccType() + "/" + record.getTransType() + "/" + 
					twoDecimals.format(record.getStartBalance()) + "/" + twoDecimals.format(record.getEndBalance())); // Writes the new data into the file

			transactionFileOutput.close(); // Closes the file
		}

		// Executes if addTransaction is false. Indicates that the user wants to rewrite an entire file with new information.
		else {
			// Opens the file to write to
			FileWriter transactionFile = new FileWriter(filename + "File.txt");
			PrintWriter transactionFileOutput = new PrintWriter(transactionFile);

			TransactionRecord[] currentRecord = recordList.getList(); // Creates a new TransactionRecord array and loads in the information from the passed in TransactionList

			transactionFileOutput.println(recordList.getSize()); // Writes the size of the list in the file

			// For loop used to write the data in the file
			for (int i = 0; i < recordList.getSize(); i++) {
				transactionFileOutput.println(dateFormat.format(currentRecord[i].getTransDate()) + "/" + currentRecord[i].getAccType() + "/" + currentRecord[i].getTransType() + "/" + 
						twoDecimals.format(currentRecord[i].getStartBalance()) + "/" + twoDecimals.format(currentRecord[i].getEndBalance())); // Writes data into the file
			}

			transactionFileOutput.close(); // Closes the file
		}

	}

	/**
	 * Method to use shell sort to sort the data in the file alphabetically by the usernames
	 * Returns nothing
	 */
	public static void sortFileData (String accountType) throws IOException {
		String[] dataList = loadData(accountType); // Creates a String array and calls the loadData method to set the data inside the array

		// Loop used to determine the size of the gap of the 2 accounts being compared
		for (int gap = dataList.length / 2; gap > 0; gap /= 2) {
			// Loop used to determine the account that's being sorted
			for (int i = gap; i < dataList.length; i++) {
				String temp = dataList[i]; // Declares and initializes a variable to temporarily store the current account that's being sorted
				int j = i; // Declares a variable and initializes it to the value of i

				// Loop used to sort the current account
				for (j = i; j >= gap && dataList[j - gap].compareToIgnoreCase(temp) > 0; j -= gap) {
					dataList[j] = dataList[j - gap]; // Swaps the place of the accounts
				}

				dataList[j] = temp; // Swaps the place of the accounts
			}
		}

		// Opens the file to write to
		FileWriter sortedAccountsFile = new FileWriter(accountType + "File.txt");
		PrintWriter sortedAccountsFileOutput = new PrintWriter(sortedAccountsFile);

		// Loop used to write the sorted accounts back into the file
		for (int i = 0; i < dataList.length; i++) {
			sortedAccountsFileOutput.println(dataList[i]); // Writes the data into the file
		}

		sortedAccountsFileOutput.close(); // Closes the file
	}

	/**
	 * Method to use binary search to find an account. Uses recursion.
	 * Returns a String
	 */
	public static String findData (String[] dataList, String username, String password, int low, int high, int middle) {
		middle = (high + low) / 2; // Divides the valid array into 2

		/**
		 * Executes if the lower limit is below or equal to the upper limit and if high isn't equal to 0
		 * Note: "high != 0" is needed in order to prevent a SteckOverflowError. If the specified username is lexicographically less than the first username in the file, the method will run perpetually
		 * (ex. The specified username is "AA" and the first username in the file is "AB").
		 * 
		 * Note: "dataList.length != 1" is needed in order prevent an IndexOutOfBoundsException. The error occurs on line 240 because it expects data for an account to be passed in, not a single number
		 * (ex. The file is empty/only contains the integer that displays the number of accounts in the file).
		 */
		if (low <= high && high != 0 && dataList.length != 1) {
			// Executes if the middle is 0. Needed because the middle is never "1" and an error occurs due to index 0 of the array in dataList being a number.
			if (middle == 0) {
				middle = 1; // Changes the value in the variable "middle" to 1
			}

			/**
			 * Executes if the middle is greater or equal to the size of dataList. Prevents an ArrayIndexOutOfBoundsException error from occurring.
			 * Documented Occurrences
			 * 	- The specified username is correct but the password is incorrect
			 * 	- The specified username is lexicographically more than the last username in the file (ex. Specified username starts with a "B", last username in file starts with "A")
			 */
			else if (middle >= dataList.length) {
				return null; // Returns a null value (account wasn't found)
			}

			Account currentArray = new Account(dataList[middle]); // Creates an Account object and sets the data inside the object to the current account being searched

			// Executes if the account with the specified username exists and the password is correct
			if (username.equalsIgnoreCase(currentArray.client.getUserName()) && password.equals(currentArray.client.getPassword())) {
				return currentArray.toString(); // Returns the data in the account
			}

			// Executes if the username of the mid-point of the list is lexicographically less than the specified username
			else if (username.compareToIgnoreCase(currentArray.client.getUserName()) < 0) {
				return findData (dataList, username, password, low, middle - 1, middle); // Ignore the upper side of the array and calls itself again
			}

			// Executes if the username of the mid-point of the list is lexicographically more than the specified username
			else {
				return findData (dataList, username, password, middle + 1, high, middle); // Ignore the lower side of the array and calls itself again
			}
		}

		return null; // Returns a null value (account wasn't found)
	}

	/**
	 * Method to use binary search to find if an account with the desired username exists. Uses recursion.
	 * Returns a boolean
	 */
	public static boolean findData (String[] dataList, String username, int low, int high, int middle) {
		middle = (high + low) / 2; // Divides the valid array into 2

		/**
		 * Executes if the lower limit is below or equal to the upper limit and if high isn't equal to 0
		 * Note: "high != 0" is needed in order to prevent a SteckOverflowError. If the specified username is lexicographically less than the first username in the file, the method will run perpetually
		 * (ex. The specified username is "AA" and the first username in the file is "AB").
		 * 
		 * Note: "dataList.length != 1" is needed in order prevent an IndexOutOfBoundsException. The error occurs on line 291 because it expects data for an account to be passed in, not a single number
		 * (ex. The file is empty/only contains the integer that displays the number of accounts in the file).
		 */
		if (low <= high && high != 0 && dataList.length != 1) {
			// Executes if the middle is 0. Needed because the middle is never "1" and an error occurs due to index 0 of the array in dataList being a number.
			if (middle == 0) {
				middle = 1; // Changes the value in the variable "middle" to 1
			}

			/**
			 * Executes if the middle is greater or equal to the size of dataList. Prevents an ArrayIndexOutOfBoundsException error from occurring.
			 * Documented Occurrences
			 * 	- The specified username is lexicographically more than the last username in the file (ex. Specified username starts with a "B", last username in file starts with "A")
			 */
			else if (middle >= dataList.length) {
				return false; // Returns false (account with username doesn't exist)
			}

			Account currentArray = new Account(dataList[middle]); // Creates an Account object and sets the data inside the object to the current account being searched

			// Executes if the account is found
			if (username.equalsIgnoreCase(currentArray.client.getUserName())) {
				return true; // Returns true (account with username exists)
			}

			// Executes if the username of the mid-point of the list is lexicographically less than the specified username
			else if (username.compareToIgnoreCase(currentArray.client.getUserName()) < 0) {
				return findData (dataList, username, low, middle - 1, middle); // Ignore the upper side of the array and calls itself again
			}

			// Executes if the username of the mid-point of the list is lexicographically more than the specified username
			else {
				return findData (dataList, username, middle + 1, high, middle); // Ignore the lower side of the array and calls itself again
			}
		}

		return false; // Returns false (account with username doesn't exist)
	}

	/**
	 * Method to create a new file
	 * Returns nothing
	 */
	public static void createFile (String filename) throws IOException {
		File newFile = new File(filename + "File.txt"); // Creates a new file variable
		newFile.createNewFile(); // Creates the new file

		// Opens the file to write to
		FileWriter newFileWriter = new FileWriter(filename + "File.txt");
		PrintWriter newFileWriterOutput = new PrintWriter(newFileWriter);
		
		newFileWriterOutput.print("0"); // Writes a "0" to the file
		
		newFileWriterOutput.close(); // Closes the file
	}

	/**
	 * @param args
	 * Self-testing main
	 * IMPORTANT: Before you run the self-testing main, you must copy over the data from TestFileReference.txt to TestFile.txt. In order to get accurate results, this process must be done every time you run this self-testing main.
	 * To see the results, compare TestFileReference.txt to TestFile.txt after the program has been run
	 */
	public static void main(String[] args) throws IOException, ParseException {
		sortFileData("Test"); // Calls the sortFileData method to sort the file

		String[] testArray = loadData("Test"); // Creates a String array and calls the loadData method to load in the data from the file

		// For loop used to display the data within the array
		for (int i = 0; i < testArray.length; i++) {
			System.out.println(testArray[i]); // Displays the data
		}

		System.out.println("\n"); // Readability formatting

		//writeData("Test", "New/Account/Fake/000 Fake St./905-000-0000/000000000009/100.10"); // Calls the writeData method to write a new account to the file

		// Tests the findData method by calling it to find the specified account
		System.out.println(findData(loadData("Test"), "AUsername", "Password123", 0, loadData("Test").length, 0)); // Tests a special case in the findData method. Refer to the comment in the first nested if statement.
		System.out.println(findData(loadData("Test"), "AUsername", "False", 0, loadData("Test").length, 0));
		System.out.println(findData(loadData("Test"), "SomeUsername", "Password222", 0, loadData("Test").length, 0));
		System.out.println(findData(loadData("Test"), "SomeUsername", "X", 0, loadData("Test").length, 0)); // Tests a special case in the findData method. Refer to the comment in the first nested else if statement.
		System.out.println(findData(loadData("Test"), "Something", "Password321", 0, loadData("Test").length, 0));
		System.out.println(findData(loadData("Test"), "French", "Fries", 0, loadData("Test").length, 0));
		System.out.println(findData(loadData("Test"), "False", "Attempt", 0, loadData("Test").length, 0));
		System.out.println(findData(loadData("Test"), "Zero", "Something", 0, loadData("Test").length, 0)); // Tests a special case in the findData method. Refer to the comment in the first nested else if statement.
		System.out.println(findData(loadData("Test"), "AA", "Battery", 0, loadData("Test").length, 0)); // Tests a special case in the findData method. Refer to the comment in the first if statement.

		System.out.println("\n"); // Readability formatting

		// Tests the findData method by calling the findData method to find if the specified account exists
		System.out.println(findData(loadData("Test"), "AUsername", 0, loadData("Test").length, 0)); // Tests a special case in the findData method. Refer to the comment in the first nested if statement.
		System.out.println(findData(loadData("Test"), "New", 0, loadData("Test").length, 0));
		System.out.println(findData(loadData("Test"), "Fake", 0, loadData("Test").length, 0));
		System.out.println(findData(loadData("Test"), "Zero", 0, loadData("Test").length, 0)); // Tests a special case in the findData method. Refer to the comment in the first nested else if statement.
		System.out.println(findData(loadData("Test"), "AA", 0, loadData("Test").length, 0)); // Tests a special case in the findData method. Refer to the comment in the first if statement.

		writeData("Test", "AUsername/Password123/Bob/123 Fake St./905-123-4567/000000000001/20.0", "AUsername"); // Calls the writeData method to update data in an existing account

		TransactionList listOfTransactions = new TransactionList(); // Creates a new TransactionList object

		TransactionRecord test = new TransactionRecord(); // Creates a TransactionRecord object
		test.processRecord("2021-04-20 02:56:43/g/Deposit/1/70000"); // Processes the record
		listOfTransactions.insert(test); // Inserts the record

		test = new TransactionRecord();
		test.processRecord("2001-04-20 05:13:13/g/Withdraw/2/55"); // Processes the record
		listOfTransactions.insert(test); // Inserts the record

		test = new TransactionRecord();
		test.processRecord("2021-04-20 15:00:53/g/Deposit/3/1344"); // Processes the record
		listOfTransactions.insert(test); // Inserts the record

		test = new TransactionRecord();
		test.processRecord("2020-04-20 11:41:22/g/Deposit/4/14.75"); // Processes the record
		listOfTransactions.insert(test); // Inserts the record

		writeData("TestTransaction", null, listOfTransactions, false); // Writes an entire list of Transaction records into a file

		test = new TransactionRecord();
		test.processRecord("2077-12-7 07:17:17/g/Deposit/77/77.77"); // Processes the record

		createFile("TestTransaction1"); // Calls the createFile method to create a new file
		
		writeData("TestTransaction1", test, null, true); // Adds a Transaction record into a file
	}

}