import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

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
 * @author mariam, diya
 * Date: April 2021
 * Description: GUI for the bank employee login. prompts user to enter in their employee id and password. allows user to show or hide 
 * 				the password. 
 * Method List:
 * 	public BELogin()
 * 	--> constructor 
 * 	
 * 	public void actionPerformed(ActionEvent e)
 * 	--> method to listen to buttons
 * 
 * 	public static void main(String[] args)
 * 	--> to run GUI
 * 	
 * 
 * Code Credit: 
 *  (1): https://stackoverflow.com/questions/19755259/hide-show-password-in-a-jtextfield-java-swing
 *  (2): https://www.youtube.com/watch?v=bApp35dCgB0
 */
public class BELogin extends JFrame implements ActionListener{
	/**
	 * instance variables
	 */
	private JLabel background, text; //labels for background and text
	private JTextField username; //text field for employee id
	private JPasswordField password;  //password field for employee password.  code credit: (1)
	private JCheckBox checkPass; //check box to show/hide the password.  code credit: (2)
	private JButton btnLogin, btnReturn; //button to login and return to previous window
	private Icon loginIcon, returnIcon;  //icon for buttons

	/**
	 * default constructor
	 */
	public BELogin() {
		super("TD Employee"); //create and name window
		setLayout(null);  //setting layout
		setIconImage(new ImageIcon("TDLogo.png").getImage()); // Replaces the coffee cup icon in the top left of the window and the in the taskbar with the TD logo

		setSize(395, 410); //sets the size of the frame 
		setLocationRelativeTo(null); //centers the window when it opens
		setDefaultCloseOperation(EXIT_ON_CLOSE); // closes the program when the user presses the close button (X button)
		setResizable(false); //restricts the user from resizing the window

		//******* creating components *******//

		//create labels 
		background = new JLabel(new ImageIcon("belogin.png")); 

		text = new JLabel("Employee Login");
		//set font
		text.setFont(new Font("Open Sans", Font.PLAIN, 19));
		//set color
		text.setForeground(Color.gray); //gray text

		//create text field
		username = new JTextField("Username");	
		UICreateAccount.createFields(username, 70, 160, 250, 35); 

		//create password field
		password = new JPasswordField("Password");
		UICreateAccount.createFields(password, 70, 210, 250, 35); 
		//code credit: (1)
		password.setEchoChar((char)0);   
		
		//create the icons
		loginIcon = new ImageIcon("button_login.png");
		returnIcon = new ImageIcon("rsz_return.png");

		//create buttons
		btnLogin = new JButton(loginIcon);
		UILogIn.createButton(btnLogin);

		btnReturn = new JButton(returnIcon); 
		UILogIn.createButton(btnReturn);

		//create check box
		checkPass = new JCheckBox("Hide Password");
		checkPass.setFont(new Font("Open Sans", Font.PLAIN, 11));
		checkPass.setForeground(Color.gray); //gray text
		checkPass.setOpaque(false);

		//******* settings and adding components *******//

		//text label
		text.setBounds(115, 100, 150, 50);
		add(text);

		//buttons
		btnLogin.setBounds(100, 280, 180, 40);
		add(btnLogin);

		btnReturn.setBounds(25, 25, 50, 50);
		add(btnReturn);

		//text fields
		add(username);
		add(password); 

		//check box
		checkPass.setBounds(70, 240, 150, 40);
		add(checkPass);

		//background
		background.setBounds(0, 0, 383, 375);
		add(background);

		//make buttons and check box as listeners
		btnLogin.addActionListener(this);
		btnReturn.addActionListener(this);
		checkPass.addActionListener(this);

		//make frame visible
		setVisible(true);

	}
	/**
	 * method to listen to buttons
	 */
	public void actionPerformed(ActionEvent e) {
		//if the log in button is clicked
		if(e.getSource() == btnLogin) {
			
			try {
				// Executes if the login information is wrong
				if (FileAccess.findData(FileAccess.loadData("Employee"), username.getText(), String.valueOf(password.getPassword()), 0, FileAccess.loadData("Employee").length, 0) == null){
					JOptionPane.showMessageDialog(null, "Sorry, incorrect username or password"); // Indicates that the login has failed
				}
				else {
					new BERecords(); // Opens the new window
				}
			}
			
			catch (IOException p) {
				
			}
			
		}
		else if(e.getSource() == btnReturn) {
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
		//if the hide password check box is clicked.
		//code credit:(2)
		else if (checkPass.isSelected()) {
			password.setEchoChar('\u2022');
		}
		//if the check box is clicked again, show the password.
		//code credit: (2)
		else {
			password.setEchoChar((char)0);		
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new BELogin();

	}

}