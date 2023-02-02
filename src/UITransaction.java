import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * 
 */

/**
 * @author mariam, addison
 * Date: April 2021
 * Description: This class is the transaction window of the application. It displays the user's personal information. It allows the user to
 * 				choose which account they want to make transactions from. It displays that account's number and balance. The window allows
 * 				users to deposit and withdraw from the account they choose from the drop menu. At the bottom of the window is a transaction
 * 				summary that displays their past transactions. 
 * Method List:
 * 	public UITransaction()
 * 	--> constructor for window
 * 
 * 	public void actionPerformed(ActionEvent e)
 * 	--> method to listen to the clicks in the drop menu
 * 
 * 	public void actionPerformed(ActionEvent b) 
 * 	--> method to listen to buttons
 * 
 * 	public void showInfo (boolean accountNumber, boolean text, boolean balance)
 * 	--> method to choose which labels become visible from drop menu selection
 * 
 * 	public void showComponents (boolean text, boolean sign, boolean amt, boolean enter, boolean cancel)
 * 	--> method to choose which components become visible from button clicks
 * 
 * 	public void createLabel(JLabel lbl, int textSize)
 * 	--> method to change the color & text size of a JLabel
 * 	
 * 	public static void main(String[] args)
 * 	--> main method to call constructor 
 * 
 * Code Credit: 
 * (1): https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html#uneditable
 * (2): https://stackoverflow.com/questions/42690425/jtextarea-border-in-java-swing
 */
public class UITransaction extends JFrame implements ActionListener {
	/**
	 * instance variables
	 */
	//labels for background and all text that appears on frame
	private JLabel background, description, instruction, character, transactionTxt, balanceTxt, title, lblName, lblAddress, lblPhoneNum, lblAccountNum, lblBalance; 
	private JComboBox accounts; //drop down menu for account selection
	private JButton btnDeposit, btnWithdraw, btnEnter, btnCancel, btnLogout;  //buttons to deposit/withdraw, & to enter the amount or cancel transaction
	private JTextField amount; //JTextField for users to enter the amount to withdraw/deposit
	private Icon depositIcon, withdrawIcon, enterIcon, cancelIcon, logoutIcon; //icons for the buttons
	private JTextArea summary; //JTextArea to display user's transaction summary
	private JScrollPane scroller; //scroller for JTextArea
	private String accountSelect; // String variable that stores the selected item in the drop down menu
	private boolean deposit, withdraw; // Boolean variables that are used to determine if the deposit or withdraw button was pressed
	private Savings savingsAccount; // Creates a Savings object
	private GIC gicAccount; // Creates a GIC object
	private TransactionRecord record; //creates transactionrecord object
	private TransactionList list;  //creates transactionlist object
	private double pastBalance;  //variable to hold the balance before transaction

	/**
	 * default constructor
	 */
	public UITransaction() throws IOException{
		super("TD Online Banking"); //create and name window
		setLayout(null);  //setting layout
		setIconImage(new ImageIcon("TDLogo.png").getImage()); // Replaces the coffee cup icon in the top left of the window and the in the taskbar with the TD logo

		setSize(600, 620); //sets the size of the frame 
		setLocationRelativeTo(null); //centers the window when it opens
		setDefaultCloseOperation(EXIT_ON_CLOSE); // closes the program when the user presses the close button (X button)
		setResizable(false); //restricts the user from resizing the window

		// Loads in the data of the customer into the objects
		savingsAccount = new Savings(FileAccess.findData(FileAccess.loadData("Savings"), UILogIn.currentCustomer.getUserName(), UILogIn.currentCustomer.getPassword(), 0, FileAccess.loadData("Savings").length, 0));
		gicAccount = new GIC(FileAccess.findData(FileAccess.loadData("General Investment Certificate"), UILogIn.currentCustomer.getUserName(), UILogIn.currentCustomer.getPassword(), 
				0, FileAccess.loadData("General Investment Certificate").length, 0), 0.056, 0.078);

		//******* creating components *******//

		//create labels 
		background = new JLabel(new ImageIcon("transactionbackground.png")); 

		title = new JLabel("My Accounts");
		createLabel(title, 36, 42, 96, 65);

		lblName = new JLabel("Name: " + UILogIn.currentCustomer.getName());
		createLabel(lblName, 14, 42, 96, 65);

		lblAddress = new JLabel("Address: " + UILogIn.currentCustomer.getAddress());
		createLabel(lblAddress, 14, 42, 96, 65);

		lblPhoneNum = new JLabel("Phone Number: " + UILogIn.currentCustomer.getPhoneNumber());
		createLabel(lblPhoneNum, 14, 42, 96, 65);

		lblAccountNum = new JLabel("No. " + "123456789012");
		createLabel(lblAccountNum, 15, 42, 96, 65);

		lblBalance = new JLabel("$" + "0.00");
		createLabel(lblBalance, 24, 42, 96, 65);

		balanceTxt = new JLabel("Available Balance:");
		createLabel(balanceTxt, 16, 42, 96, 65);

		description = new JLabel("What would you like to do today?");
		createLabel(description, 14, 107, 105, 105);
		description.setForeground(Color.DARK_GRAY);  

		instruction = new JLabel(); 
		createLabel(instruction, 14, 107, 105, 105);
		instruction.setForeground(Color.DARK_GRAY);  

		character = new JLabel("$");
		createLabel(character, 16, 107, 105, 105);
		character.setForeground(Color.DARK_GRAY);

		transactionTxt = new JLabel("Transaction Summary");
		createLabel(transactionTxt, 16, 107, 105, 105);
		transactionTxt.setForeground(Color.DARK_GRAY);

		// create drop menu
		//code credit: (1)
		String[] accountList = {"Choose an Account", "Savings", "General Investment Certificate"}; //input the options
		accounts = new JComboBox(accountList); //add options to combobox
		accounts.setSelectedIndex(0);  //preset option 1

		//create the button images
		depositIcon = new ImageIcon("button_deposit.png");
		withdrawIcon = new ImageIcon("button_withdraw.png");
		enterIcon = new ImageIcon("button_enter.png");
		cancelIcon = new ImageIcon("button_cancel.png");
		logoutIcon = new ImageIcon("button_logout.png");

		//create buttons
		btnDeposit = new JButton(depositIcon);
		UILogIn.createButton(btnDeposit);

		btnWithdraw = new JButton(withdrawIcon); 
		UILogIn.createButton(btnWithdraw);

		btnEnter = new JButton(enterIcon);
		UILogIn.createButton(btnEnter);

		btnCancel = new JButton(cancelIcon);
		UILogIn.createButton(btnCancel);

		btnLogout = new JButton(logoutIcon);
		UILogIn.createButton(btnLogout);

		//create JTextField
		amount = new JTextField("0.00");
		UICreateAccount.createFields(amount, 390, 275, 100, 25);

		//create JTextArea
		summary = new JTextArea();
		//create a border for the JTextArea
		//code credit for next 2 lines: (2)
		Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY); 
		//set the border 
		summary.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10))); 
		summary.setEditable(false); //prevent users from editing

		//add JTextArea to scroller
		scroller = new JScrollPane(summary);
		//add scrollers as needed
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


		//******* setting dimensions and adding components *******//

		//labels 
		title.setBounds(30, 140, 250, 50);
		add(title);

		lblName.setBounds(150, 30, 200, 50);
		add(lblName);

		lblPhoneNum.setBounds(150, 50, 200, 50);
		add(lblPhoneNum);

		lblAddress.setBounds(150, 70, 200, 50);
		add(lblAddress);

		lblAccountNum.setBounds(75, 320, 200, 50);
		lblAccountNum.setVisible(false);
		add(lblAccountNum);

		balanceTxt.setBounds(75, 230, 200, 50);
		balanceTxt.setVisible(false);
		add(balanceTxt);

		lblBalance.setBounds(65, 270, 200, 50);
		lblBalance.setVisible(false);
		add(lblBalance);		

		description.setBounds(320, 150, 280, 50);
		add(description);

		instruction.setBounds(320, 235, 280, 50);
		instruction.setVisible(false);
		add(instruction);

		character.setBounds(380, 275, 100, 25);
		character.setVisible(false);
		add(character);

		transactionTxt.setBounds(40, 390, 170, 25);
		add(transactionTxt);

		//buttons
		btnDeposit.setBounds(300, 170, 100, 100);
		add(btnDeposit);

		btnWithdraw.setBounds(430, 170, 120, 100);
		add(btnWithdraw);

		btnEnter.setBounds(325, 315, 90, 25);
		btnEnter.setVisible(false);
		add(btnEnter);

		btnCancel.setBounds(430, 315, 90, 25);
		btnCancel.setVisible(false);
		add(btnCancel);

		btnLogout.setBounds(475, 546, 120, 30);
		add(btnLogout);

		//text field
		amount.setVisible(false);
		add(amount); 

		//text area
		scroller.setBounds(40, 420, 520, 120);
		add(scroller);

		//drop menu 
		accounts.setBounds(25, 190, 240, 30);
		add(accounts);

		showComponents(false, false, false, false, false, false, false, false); // Hides the components of the window

		deposit = false; // Indicates that the user didn't press the deposit button
		withdraw = false; // Indicates that the user didn't press the withdraw button

		//make the drop menu a listener
		accounts.addActionListener(new ActionListener(){
			//method to listen to the menu selections
			public void actionPerformed(ActionEvent e) {
				JComboBox choice = (JComboBox) e.getSource(); //sets the drop down menu's events to the variable choice
				accountSelect = (String) choice.getSelectedItem(); //gets the item selected in the drop down menu

				//executes if "choose an account" is selected 
				if(accountSelect.equals("Choose an Account")) {
					// Hides all of the components of the window
					showInfo(false, false, false);
					showComponents(false, false, false, false, false, false, false, false);
				}
				//executes if "savings" is selected
				else if (accountSelect.equals("Savings")) {
					lblBalance.setText(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format((savingsAccount.getBalance()))); // Displays the balance of the savings account
					lblAccountNum.setText("No. " + savingsAccount.getAccountNumber()); // Displays the account number of the savings account

					showInfo(true, true, true); // Displays the components containing the account information
					showComponents(true, true, true, false, false, false, false, false); // Displays the deposit and withdraw buttons
				}
				//executes if "general investment certificate" is selected
				else if (accountSelect.equals("General Investment Certificate")) {
					lblBalance.setText(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format((gicAccount.getBalance()))); // Displays the balance of the GIC account
					lblAccountNum.setText("No. " + gicAccount.getAccountNumber()); // Displays the account number of the GIC account

					showInfo(true, true, true); // Displays the components containing the account information
					showComponents(true, true, true, false, false, false, false, false); // Displays the deposit and withdraw buttons
				}

			}
		});

		//add background
		background.setBounds(0, 0, 600, 600);
		add(background);

		//set buttons as listeners
		btnDeposit.addActionListener(this); 
		btnWithdraw.addActionListener(this); 
		btnEnter.addActionListener(this); 
		btnCancel.addActionListener(this); 
		btnLogout.addActionListener(this);

		//make the window visible
		setVisible(true);

		// Listens to key presses
		amount.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent k) {
				// Executes code if keyboard input is between 0-9, backspace, or period
				if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9') || (int) k.getKeyChar() == 8 || (int) k.getKeyChar() == 46) {
					amount.setEditable(true); // Allows the user to interact with the textbox
				}

				// Executes the code if any other key is pressed
				else {
					amount.setEditable(false); // Prevents the user from interacting with the textbox
				}
			}
		});

	}

	/**
	 * method that listens to buttons
	 */
	public void actionPerformed(ActionEvent b) {
		try {
			// Executes if the deposit button was pressed
			if (b.getSource() == btnDeposit) {
				instruction.setText("Enter the amount to deposit:"); // Displays the appropriate instructions

				// Changes boolean variables to indicate that the deposit button was pressed
				deposit = true;
				withdraw = false;

				showComponents(true, true, true, true, true, true, true, true); // Makes all components visible
			}

			// Executes if the withdraw button was pressed
			else if (b.getSource() == btnWithdraw){
				instruction.setText("Enter the amount to withdraw:"); // Displays the appropriate instructions

				// Changes boolean variables to indicate that the withdraw button was pressed
				deposit = false;
				withdraw = true;

				showComponents(true, true, true, true, true, true, true, true); // Makes all components visible
			}

			// Executes if the cancel button was pressed
			else if (b.getSource() == btnCancel) {
				amount.setText("0.00"); // Resets the value inside textbox
				showComponents(true, true, true, false, false, false, false, false); // Hides the components that enable deposits and withdrawals
			}

			//if logout button was clicked
			else if (b.getSource() == btnLogout) {
				//close this window
				setVisible(false);
				//open login window
				try {
					new UILogIn();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

			}


			// Executes if the enter button was pressed
			else if (b.getSource() == btnEnter){
				// Executes if the deposit button was pressed
				if (deposit == true) {
					// Executes if the user is on the savings account
					if (accountSelect.equals("Savings")) {

						//save the balance before transaction
						pastBalance = savingsAccount.getBalance(); 

						savingsAccount.deposit(Double.parseDouble(amount.getText())); // Deposits the money in the account

						//create format for the date & time
						String pattern = "yyyy-M-dd hh:mm:ss";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

						//create list and record objects
						list = new TransactionList(); 
						record = new TransactionRecord();

						//set the variables
						record.setAccType('s'); //account type
						record.setTransType("Deposit"); //transaction type
						record.setStartBalance(pastBalance); //previous balance
						record.setEndBalance(savingsAccount.getBalance()); //current balance

						try {
							//date and time of transaction
							record.setTransDate(simpleDateFormat.parse(String.valueOf(LocalDate.now()) + " " + String.valueOf(LocalTime.now().truncatedTo(ChronoUnit.SECONDS))));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {

							FileAccess.writeData(accountSelect, savingsAccount.toString(), UILogIn.currentCustomer.getUserName()); // Updates the data in the file

							//writes the record into the customer file
							FileAccess.writeData(UILogIn.currentCustomer.getUserName(), record, list, true);

							lblBalance.setText(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(savingsAccount.getBalance())); // Updates the balance being displayed

							summary.setText(summary.getText() + record.toString() + "\n"); //display record in the JTextArea
						}

						catch (IOException e) {

						}
					}

					// Executes if the user is on the GIC account
					else if (accountSelect.equals("General Investment Certificate")) {

						//save the balance before the transaction
						pastBalance = gicAccount.getBalance(); 

						gicAccount.deposit(Double.parseDouble(amount.getText())); // Deposits the money in the account

						//create format for date and time
						String pattern = "yyyy-M-dd hh:mm:ss";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

						//set the variables
						record.setAccType('g'); //account type
						record.setTransType("Deposit"); //transaction type
						record.setStartBalance(pastBalance); //previous balance
						record.setEndBalance(gicAccount.getBalance()); //current balance


						try {
							//current date & time
							record.setTransDate(simpleDateFormat.parse(String.valueOf(LocalDate.now()) + " " + String.valueOf(LocalTime.now().truncatedTo(ChronoUnit.SECONDS))));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}


						try {
							FileAccess.writeData(accountSelect, gicAccount.toString(), UILogIn.currentCustomer.getUserName()); // Updates the data in the file

							//writes the record into the customer file
							FileAccess.writeData(UILogIn.currentCustomer.getUserName(), record, list, true);

							lblBalance.setText(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(gicAccount.getBalance())); // Updates the balance being displayed

							summary.setText(summary.getText() + record.toString() + "\n"); //display record in the JTextArea
						}

						catch (IOException e) {

						}
					}
				}

				// Executes if the withdraw button was pressed
				else if (withdraw == true) {
					// Executes if the user is on the savings account
					if (accountSelect.equals("Savings")) {
						//if the amount trying to withdraw is greater than the balance
						if(Double.parseDouble(amount.getText()) > savingsAccount.getBalance()) {
							//display error message to user
							JOptionPane.showMessageDialog(null, "Insufficient Funds.");
						}


						else {
							//save balance before the transaction happens
							pastBalance = savingsAccount.getBalance(); 

							savingsAccount.withdraw(Double.parseDouble(amount.getText())); // Withdraws the money from the account

							//create date & time format
							String pattern = "yyyy-M-dd hh:mm:ss";
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


							//set the variables
							record.setAccType('s'); //account type
							record.setTransType("Withdraw"); //transaction type
							record.setStartBalance(pastBalance); //previous balance
							record.setEndBalance(savingsAccount.getBalance()); //current balance


							try {
								//current date and time
								record.setTransDate(simpleDateFormat.parse(String.valueOf(LocalDate.now()) + " " + String.valueOf(LocalTime.now().truncatedTo(ChronoUnit.SECONDS))));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							try {
								FileAccess.writeData(accountSelect, savingsAccount.toString(), UILogIn.currentCustomer.getUserName()); // Updates the data in the file

								//writes the record into the customer file
								FileAccess.writeData(UILogIn.currentCustomer.getUserName(), record, list, true);

								lblBalance.setText(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(savingsAccount.getBalance())); // Updates the balance being displayed

								summary.setText(summary.getText() + record.toString() + "\n"); //display record in the JTextArea

							}

							catch (IOException e) {

							}
						}
					}

					// Executes if the user is on the GIC account
					else if (accountSelect.equals("General Investment Certificate")) {
						//if the amount trying to withdraw is greater than the balance
						if((Double.parseDouble(amount.getText()) * (1 + gicAccount.getPenaltyRate())) > gicAccount.getBalance()) {
							//display error message to user
							JOptionPane.showMessageDialog(null, "Insufficient Funds.");
						}

						else{
							//save balance before transaction happens
							pastBalance = gicAccount.getBalance(); 

							gicAccount.withdraw(Double.parseDouble(amount.getText())); // Withdraws the money from the account

							//create format for date and time
							String pattern = "yyyy-M-dd hh:mm:ss";
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

							//set variables
							record.setAccType('g'); //account type
							record.setTransType("Withdraw"); //transaction type
							record.setStartBalance(pastBalance); //previous balance
							record.setEndBalance(gicAccount.getBalance()); //current balance

							try {
								//current date and time
								record.setTransDate(simpleDateFormat.parse(String.valueOf(LocalDate.now()) + " " + String.valueOf(LocalTime.now().truncatedTo(ChronoUnit.SECONDS))));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							try {
								FileAccess.writeData(accountSelect, gicAccount.toString(), UILogIn.currentCustomer.getUserName()); // Updates the data in the file

								//write the data into the customer file
								FileAccess.writeData(UILogIn.currentCustomer.getUserName(), record, list, true);

								lblBalance.setText(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(gicAccount.getBalance())); // Updates the balance being displayed

								summary.setText(summary.getText() + record.toString() + "\n"); //display record in the JTextArea

							}

							catch (IOException e) {

							}
						}
					}
				}
			}
		}
		
		catch (NumberFormatException n) {
			JOptionPane.showMessageDialog(null, "Invalid Sum of Money"); // Tells the user that the amount of money they entered is invalid
		}
	}

	/**
	 * method to choose which labels become visible from drop menu selection
	 */
	public void showInfo (boolean accountNumber, boolean text, boolean balance) {
		//makes the selected component visible depending on the input
		lblAccountNum.setVisible(accountNumber);
		balanceTxt.setVisible(text); 
		lblBalance.setVisible(balance);
	}

	/**
	 * method to choose which components become visible from button clicks
	 */
	public void showComponents (boolean desc, boolean deposit, boolean withdraw, boolean text, boolean sign, boolean amt, boolean enter, boolean cancel) {
		//makes the selected component visible depending on the input
		description.setVisible(desc);
		btnDeposit.setVisible(deposit);
		btnWithdraw.setVisible(withdraw);
		instruction.setVisible(text);
		character.setVisible(sign);
		amount.setVisible(amt);
		btnEnter.setVisible(enter);
		btnCancel.setVisible(cancel);
	}

	/**
	 * method to change the color & text size of a JLabel
	 */
	public static void createLabel(JLabel lbl, int textSize, int r, int g, int b) {
		//set font
		lbl.setFont(new Font("Open Sans", Font.PLAIN, textSize));
		//set color
		lbl.setForeground(new Color(r, g, b)); 
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		new UITransaction(); 
	}

}