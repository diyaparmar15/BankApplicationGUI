import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * 
 */

/**
 * @author mariam, addison
 * Date: April 2021
 * Description: This class makes the create account GUI. It asks the user for their full name, address, phone number, 
 * 				current savings balance, current GIC balance, and to make a username and password.
 * Method List:
 * 	public UICreateAccount()
 * 	--> constructor 
 * 
 * 	public static void createFields(JTextField input, int x, int y, int w, int h) 
 *  --> method to create text fields.
 * 			takes in the input (name, address, phone number, etc.), the x & y position, and the dimensions
 * 			sets the borders, colors, font, position, and dimensions
 * 
 * 	public void actionPerformed(ActionEvent e)
 * 	--> method that listens to buttons
 * 
 * 	public static void main(String[] args) 
 * 	--> main method that calls the constructor
 * 
 * Code Credit:
 * 	(1): https://stackoverflow.com/questions/8792651/how-can-i-add-padding-to-a-jtextfield
 */
public class UICreateAccount extends JFrame implements ActionListener{
	/**
	 * instance variables
	 */
	private JLabel background; //label for background
	//text fields to get customer information
	private JTextField name, address, phoneNumber, username, password, savingsBalance, investmentBalance; 
	private JButton btnCreate, btnReturn;  //buttons to finish creating the account and return to previous screen
	private Icon createIcon, returnIcon; //icons for buttons

	/**
	 * default constructor
	 */
	public UICreateAccount() {
		super("TD Online Banking"); //create and name window
		setLayout(null);  //setting layout
		setIconImage(new ImageIcon("TDLogo.png").getImage());

		setSize(520, 550); //sets the size of the frame 
		setLocationRelativeTo(null); //centers the window when it opens
		setDefaultCloseOperation(EXIT_ON_CLOSE); // closes the program when the user presses the close button (X button)
		setResizable(false); //restricts the user from resizing the window

		//******* creating and setting components *******//

		//label
		background = new JLabel(new ImageIcon("rsz_createaccbackground.png")); 
		background.setBounds(0, 0, 510, 510);

		//text fields
		name = new JTextField("Full Name");
		createFields(name, 80, 140, 350, 35);  //call method to create text field

		address = new JTextField("Address");
		createFields(address, 80, 180, 350, 35);

		phoneNumber = new JTextField("Phone Number");
		createFields(phoneNumber, 80, 220, 350, 35);

		username = new JTextField("Username");
		createFields(username, 80, 260, 350, 35);

		password = new JTextField("Password");
		createFields(password, 80, 300, 350, 35);

		savingsBalance = new JTextField("Current Savings Balance (0.00)");
		createFields(savingsBalance, 80, 340, 350, 35);

		investmentBalance = new JTextField("Current GIC Balance (0.00)");
		createFields(investmentBalance, 80, 380, 350, 35);

		//buttons
		//create the button image
		createIcon = new ImageIcon("button_create.png");
		returnIcon = new ImageIcon("rsz_return.png");

		//create buttons
		btnCreate = new JButton(createIcon);
		UILogIn.createButton(btnCreate);

		btnReturn = new JButton(returnIcon);
		UILogIn.createButton(btnReturn);


		//******* add components *******//
		//text fields
		add(name);
		add(address);
		add(phoneNumber);
		add(username);
		add(password);
		add(savingsBalance);
		add(investmentBalance);

		//buttons
		btnCreate.setBounds(165, 425, 180, 35);
		add(btnCreate);

		btnReturn.setBounds(42, 35, 50, 50);
		add(btnReturn);

		//background
		add(background);

		//make buttons listeners
		btnCreate.addActionListener(this);
		btnReturn.addActionListener(this);

		// Listens to key presses
		savingsBalance.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent k) {
				// Executes code if keyboard input is between 0-9, backspace, or period
				if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9') || (int) k.getKeyChar() == 8 || (int) k.getKeyChar() == 46) {
					savingsBalance.setEditable(true); // Allows the user to interact with the textbox
				}

				// Executes the code if any other key is pressed
				else {
					savingsBalance.setEditable(false); // Prevents the user from interacting with the textbox
				}
			}
		});

		// Listens to key presses
		investmentBalance.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent k) {
				// Executes code if keyboard input is between 0-9, backspace, or period
				if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9') || (int) k.getKeyChar() == 8 || (int) k.getKeyChar() == 46) {
					investmentBalance.setEditable(true); // Allows the user to interact with the textbox
				}

				// Executes the code if any other key is pressed
				else {
					investmentBalance.setEditable(false); // Prevents the user from interacting with the textbox
				}
			}
		});

		//make frame visible
		setVisible(true);
	}

	/**
	 * method to create text fields
	 */
	public static void createFields(JTextField input, int x, int y, int w, int h) {
		//change border so text isn't close to the edge.
		//code credit: (1)
		input.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		//set font
		input.setFont(new Font("Open Sans", Font.PLAIN, 13));
		//set colors
		input.setForeground(Color.DARK_GRAY); //dark gray text 
		input.setBackground(new Color(236, 237, 236)); //light gray background
		//set bounds
		input.setBounds(x, y, w, h);
	}

	/**
	 * method that listens to buttons
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		// Executes if the Return button is pressed
		if(e.getSource() == btnReturn) {
			//close this window
			setVisible(false);
			try {
				//open login window
				new UILogIn();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		// Executes if the Create button is pressed
		else if(e.getSource() == btnCreate) {
			try {
				// Executes if no account with the specified username is found (username is available)
				if (FileAccess.findData(FileAccess.loadData("Savings"), username.getText(), 0, FileAccess.loadData("Savings").length, 0) == false) {

					Savings newSavings = new Savings(); // Creates a new Savings object
					GIC newGIC = new GIC(); // Creates a new GIC object

					newSavings.client.setName(name.getText()); // Sets the name in the Savings account
					newSavings.client.setAddress(address.getText()); // Sets the address in the Savings account
					newSavings.client.setPhoneNumber(phoneNumber.getText()); // Sets the phone number in the Savings account
					newSavings.client.setUserName(username.getText()); // Sets the username in the Savings account
					newSavings.client.setPassword(password.getText()); // Sets the password in the Savings account
					newSavings.deposit(Double.parseDouble(savingsBalance.getText())); // Sets the balance of the Savings account
					FileAccess.writeData("Savings", newSavings.toString()); // Writes the data of the account into the Savings file

					newGIC.client.setName(name.getText()); // Sets the name in the GIC account
					newGIC.client.setAddress(address.getText()); // Sets the address in the GIC account
					newGIC.client.setPhoneNumber(phoneNumber.getText()); // Sets the phone number in the GIC account
					newGIC.client.setUserName(username.getText()); // Sets the username in the GIC account
					newGIC.client.setPassword(password.getText()); // Sets the password in the GIC account
					newGIC.deposit(Double.parseDouble(investmentBalance.getText())); // Sets the balance of the GIC account
					FileAccess.writeData("General Investment Certificate", newGIC.toString()); // Writes the data of the account into the Savings file

					FileAccess.createFile(username.getText()); // Calls the createFile method and creates a new file to store their transactions

					JOptionPane.showMessageDialog(null, "Account Successfully Created"); // Tells the user that their accounts were successfully created

					setVisible(false); // Makes the current window invisible

					new UILogIn(); // Opens the login window
				}

				else {
					JOptionPane.showMessageDialog(null, "The Username: '" + username.getText() + "' Is Already Taken"); // Tells the user that the username is already taken
				}
			}

			catch (IOException r) {

			}

			catch (Exception t) {

			}
		}

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new UICreateAccount();

	}

}