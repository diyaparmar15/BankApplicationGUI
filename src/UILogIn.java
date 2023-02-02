import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 * 
 */

/**
 * @author mariam (front end), addison (back end) 
 * Date: April 2021
 * Description: This class creates the log in window of the bank application. it requires customers to enter their account number 
 * 				and password to log in. the log in button will take them to the transaction page. for new customers, the GUI includes
 * 				a button to create a new account.
 * Method List:
 * 	public UILogIn() throws Exception
 * 	--> constructor that creates the GUI
 * 
 * 	public static void createButton(JButton input)
 * 	--> method to set the properties of the buttons
 * 
 * 	public void actionPerformed(ActionEvent e)
 * 	--> method to listen to buttons
 * 	
 *  public static void main(String[] args) throws Exception
 *  --> main method to run GUI
 * 
 * Code Credit: 
 *  (1): https://stackoverflow.com/questions/19755259/hide-show-password-in-a-jtextfield-java-swing
 *  (2): https://www.youtube.com/watch?v=bApp35dCgB0
 * 
 */
public class UILogIn extends JFrame implements ActionListener{
	/**
	 * instance variables
	 */
	private JLabel background, text; //labels for background and text
	private JTextField username; //text field for customer username
	private JPasswordField password;  //password field for customer password.  code credit: (1)
	private JCheckBox checkPass; //check box to show/hide the password.  code credit: (2)
	private JButton btnLogin, btnCreate, btnLock;  //buttons to login, create an account, and employee login (lock)
	private Icon loginIcon, createIcon, lockIcon; //icons for buttons
	public static Customer currentCustomer; 
	private String customerData;
	
	//************************** ASK ADDISON ABOUT THE CUSTOMER LOGIN ********************************//

	/**
	 * default constructor
	 * @throws FontFormatException 
	 */
	public UILogIn() throws Exception{
		super("TD Online Banking"); //create and name window
		setLayout(null);  //setting layout

		setSize(510, 530); //sets the size of the frame 
		setLocationRelativeTo(null); //centers the window when it opens
		setDefaultCloseOperation(EXIT_ON_CLOSE); // closes the program when the user presses the close button (X button)
		setResizable(false); //restricts the user from resizing the window

		//******* creating components *******//

		//create labels 
		background = new JLabel(new ImageIcon("loginbackground.jpg")); 

		text = new JLabel("New Customer?");
		UITransaction.createLabel(text, 13, 107, 105, 105);

		//create text field
		username = new JTextField("Username");	
		UICreateAccount.createFields(username, 130, 200, 250, 35); 


		//create password field
		password = new JPasswordField("Password");
		UICreateAccount.createFields(password, 130, 245, 250, 35); 
		//code credit: (1)
		password.setEchoChar((char)0);   


		//create the icons
		loginIcon = new ImageIcon("button_login.png");
		createIcon = new ImageIcon("button_create-an-account.png");
		lockIcon = new ImageIcon("lock icon.png");

		//create buttons
		btnLogin = new JButton(loginIcon);
		createButton(btnLogin);

		btnCreate = new JButton(createIcon); 
		createButton(btnCreate);

		btnLock = new JButton(lockIcon); 
		createButton(btnLock);


		//create check box
		checkPass = new JCheckBox("Hide Password");
		checkPass.setFont(new Font("Open Sans", Font.PLAIN, 11));
		checkPass.setForeground(Color.gray); //gray text


		//******* setting dimensions and adding components *******//

		//username and password fields 
		add(username);
		add(password);

		//buttons
		btnLogin.setBounds(200, 315, 110, 40);
		add(btnLogin);

		btnCreate.setBounds(170, 400, 173, 31);
		add(btnCreate);

		btnLock.setBounds(450, 450, 50, 50);
		add(btnLock);

		//check box
		checkPass.setBounds(130, 275, 150, 40);
		add(checkPass);

		//labels 
		text.setBounds(210, 360, 200, 50);
		add(text);

		background.setBounds(0, 0, 510, 510);
		add(background);


		//set buttons and check box as listeners
		btnLogin.addActionListener(this);
		btnCreate.addActionListener(this);
		btnLock.addActionListener(this);
		checkPass.addActionListener(this);

		//make window visible
		setVisible(true); 
	}

	/**
	 * method to set the properties of the buttons
	 */
	public static void createButton(JButton input){
		//create buttons
		input.setOpaque(false);
		input.setContentAreaFilled(false); 
		input.setBorderPainted(false);
	}

	/**
	 * method that listens to buttons 
	 *   // addison's work // 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//if the log in button is clicked
		if(e.getSource() == btnLogin) {
			//search for customer's login information from text file
			try {
				//if username & password can't be found
				if(FileAccess.findData(FileAccess.loadData("Savings"), username.getText(), String.valueOf(password.getPassword()), 0, FileAccess.loadData("Savings").length, 0) == null) {
					JOptionPane.showMessageDialog(null, "Sorry, incorrect username or password."); //display not found message
				}
				//if it was found
				else {
					customerData = FileAccess.findData(FileAccess.loadData("Savings"), username.getText(), String.valueOf(password.getPassword()), 0, FileAccess.loadData("Savings").length, 0);

					currentCustomer = new Customer(customerData.substring(0, customerData.lastIndexOf("/", customerData.lastIndexOf("/") - 1)));
					
					//close this window
					setVisible(false);
					//open transaction window
					new UITransaction();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//if the create an account button is clicked
		else if(e.getSource() == btnCreate) {
			//close this window
			setVisible(false);
			//open create account window
			new UICreateAccount(); 
		}
		//if the hide password check box is clicked.
		//code credit:(2)
		else if (checkPass.isSelected()) {
			password.setEchoChar('\u2022');
		}
		else if(e.getSource() == btnLock) {
			setVisible(false);
			new BELogin(); 
		}
		
		//if the check box is clicked again, show the password.
		//code credit: (2)
		else {
			password.setEchoChar((char)0);		
		}

	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//create and display GUI
		UILogIn window = new UILogIn(); 

	}

}
