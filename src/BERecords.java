import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
 * @author mariam
 * Date: April 2021
 * Description: This class creates the GUI for a bank employee. it allows the user to choose a customer file to display. it
 * 				also allows the user to make changes to the file, sort the file, and save it.
 * Method List:
 * 	public BERecords()
 * 	--> constructor to create GUI
 * 	
 * 	public void actionPerformed(ActionEvent e) 
 * 	--> method to listen to buttons
 * 
 * 	public void showComponents (boolean text, boolean textField, boolean enter, boolean cancel)
 * 	--> method to choose which components to show 
 * 	
 * 	public static void main(String[] args)
 * 	--> main method to run GUI
 * 
 * Code Credit:
 * (1): https://stackoverflow.com/questions/42690425/jtextarea-border-in-java-swing
 *
 */
public class BERecords extends JFrame implements ActionListener{
	/**
	 * instance variables
	 */
	private JLabel background, title, prompt; //labels for background and text
	//buttons for saving the information into a text file, displaying the records, sorting the records, inserting records, and deleting records
	//entering information and canceling
	private JButton btnSave, btnDisplay, btnSort, btnEnter, btnCancel, btnInsert, btnDelete;  
	private Icon saveIcon, displayIcon, sortIcon, enterIcon, cancelIcon, insertIcon, deleteIcon; //icons for the buttons
	private JTextField textField; //textfield for records and file names
	private boolean display, delete, save, insert;// variables to determine what buttons were pressed
	private JTextArea summary; //JTextArea to display transaction summary
	private JScrollPane scroller; //scroller for JTextArea
	private TransactionList listOfTransactions;
	private TransactionRecord record;

	/**
	 * default constructor
	 */
	public BERecords() {
		super("TD Employee"); //create and name window
		setLayout(null);  //setting layout
		setIconImage(new ImageIcon("TDLogo.png").getImage());

		setSize(660, 640); //sets the size of the frame 
		setLocationRelativeTo(null); //centers the window when it opens
		setDefaultCloseOperation(EXIT_ON_CLOSE); // closes the program when the user presses the close button (X button)
		setResizable(false); //restricts the user from resizing the window

		//*********** creating components *************//

		//create labels 
		background = new JLabel(new ImageIcon("bebackground.png")); 

		title = new JLabel("Transaction Summary");
		UITransaction.createLabel(title, 22, 107, 105, 105);

		prompt = new JLabel();
		UITransaction.createLabel(prompt, 14, 107, 105, 105);

		//create button images 
		displayIcon = new ImageIcon("button_display-transactions.png");
		saveIcon = 	new ImageIcon("button_save.png");
		sortIcon = 	new ImageIcon("button_sort.png");
		insertIcon = new ImageIcon("button_insert.png");
		deleteIcon = new ImageIcon("button_delete.png");
		enterIcon = new ImageIcon("button_enter.png");
		cancelIcon = new ImageIcon("button_cancel.png");

		//create buttons
		btnDisplay = new JButton(displayIcon);
		UILogIn.createButton(btnDisplay);

		btnSave = new JButton(saveIcon);
		UILogIn.createButton(btnSave);

		btnSort = new JButton(sortIcon);
		UILogIn.createButton(btnSort);

		btnInsert = new JButton(insertIcon);
		UILogIn.createButton(btnInsert);

		btnDelete = new JButton(deleteIcon);
		UILogIn.createButton(btnDelete);

		btnEnter = new JButton(enterIcon);
		UILogIn.createButton(btnEnter);

		btnCancel = new JButton(cancelIcon);
		UILogIn.createButton(btnCancel);


		//create JTextField
		textField = new JTextField();
		UICreateAccount.createFields(textField, 270, 490, 300, 25);

		//create JTextArea
		summary = new JTextArea();
		//create a border for the JTextArea
		//code credit for next 2 lines: (1)
		Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY); 
		//set the border 
		summary.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10))); 
		summary.setEditable(false); //restricts user from editing 

		//add JTextArea to scroller
		scroller = new JScrollPane(summary);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		//******** setting and adding components *********//

		//text area
		scroller.setBounds(60, 160, 520, 140);
		add(scroller);

		//buttons
		btnDisplay.setBounds(215, 310, 200, 45);
		add(btnDisplay);

		btnSave.setBounds(260, 420, 110, 45);
		add(btnSave);

		btnSort.setBounds(130, 370, 110, 40);
		add(btnSort);

		btnInsert.setBounds(260, 370, 110, 40);
		add(btnInsert);

		btnDelete.setBounds(390, 370, 110, 40);
		add(btnDelete);

		btnEnter.setBounds(200, 520, 120, 50);
		btnEnter.setVisible(false);
		add(btnEnter);

		btnCancel.setBounds(300, 520, 120, 50);
		btnCancel.setVisible(false);
		add(btnCancel);

		//JTextField
		textField.setVisible(false);
		add(textField);

		//labels
		//text
		title.setBounds(60, 115, 300, 50);
		add(title);

		prompt.setBounds(75, 490, 210, 25);
		prompt.setVisible(false);
		add(prompt);

		//background
		background.setBounds(0, 0, 650, 600);
		add(background);

		//set buttons as listeners
		btnDisplay.addActionListener(this);
		btnSave.addActionListener(this);
		btnSort.addActionListener(this);
		btnInsert.addActionListener(this);
		btnDelete.addActionListener(this);
		btnEnter.addActionListener(this);
		btnCancel.addActionListener(this);


		setVisible(true); //make windows visible

		//indicates that the user hasn't clicked on any button
		display = false;
		save = false;
		insert = false;
		delete = false; 
	}

	/**
	 * method to listen to buttons
	 */
	public void actionPerformed(ActionEvent e) {
		//if display transactions button is clicked
		if(e.getSource() == btnDisplay) {
			//indicate that display transactions was clicked
			display = true;
			save = false;
			insert = false;
			delete = false; 

			//set text
			prompt.setText("Enter the username of the customer:");
			//change its boundaries 
			prompt.setBounds(30, 490, 240, 25);
			//set text field
			textField.setText("Customer's Username");
			//make the components visible
			showComponents(true, true, true, true);  
		}
		//if save button is clicked
		else if(e.getSource() == btnSave) {
			//indicate that save was clicked
			display = false;
			save = true;
			insert = false;
			delete = false; 

			//set text
			prompt.setText("Enter the username of the customer:");
			//change its boundaries
			prompt.setBounds(30, 490, 240, 25);
			//set text field
			textField.setText("Customer's Username");
			//make the components visible
			showComponents(true, true, true, true);  

		}
		//if sort button is clicked
		else if(e.getSource() == btnSort) {
			if(listOfTransactions != null) {
				listOfTransactions.insertionSort(); // Calls the insertionSort method to sort the records

				summary.setText(listOfTransactions.toString()); // Displays the records

				//set text
				prompt.setText("Sorted.");
				//set boundaries
				prompt.setBounds(290, 490, 210, 25);
				//only make the text visible
				showComponents(true, false, false, false);  
			}

		}
		//if insert button is clicked
		else if(e.getSource() == btnInsert) {
			//indicate that insert was clicked
			display = false;
			save = false;
			insert = true;
			delete = false; 

			//set text
			prompt.setText("Enter the record to insert:");
			//set boundaries
			prompt.setBounds(75, 490, 210, 25);
			//set the text field
			textField.setText("yyyy-mm-dd hh:mm:ss/account type/transaction type/start balance/end balance");
			//make the components visible
			showComponents(true, true, true, true);  

		}
		//if delete button is clicked
		else if(e.getSource() == btnDelete) {
			//indicate that delete was clicked
			display = false;
			save = false;
			insert = false;
			delete = true; 

			//set text
			prompt.setText("Enter the record to delete:");
			//set boundaries
			prompt.setBounds(75, 490, 210, 25);
			//set text field
			textField.setText("yyyy-mm-dd hh:mm:ss");
			//make the components visible
			showComponents(true, true, true, true);  
		}
		//if enter button is clicked
		else if(e.getSource() == btnEnter) {
			//for display transactions button
			if(display == true) {
				try {
					String[] output; // Declares a String variable to store the data retrieved from a file

					listOfTransactions = new TransactionList(); // Creates a new TransactionList object

					output = FileAccess.loadData(textField.getText()); // Calls the loadData method to load in data from a file

					// For loop used turn the data in the String array into TransactionRecords
					for (int i = 1; i < output.length; i++) {
						record = new TransactionRecord(); // Creates a new TransactionRecord object
						record.processRecord(output[i]); // Calls the processRecord method to process the String into a record
						this.listOfTransactions.insert(record); // Calls the insert method to insert the record into the list
					}

					summary.setText(listOfTransactions.toString()); // Displays the records
				}

				catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "File Not Found"); // Tells the user that the file wasn't found
				}

				catch (ParseException p) {

				}
			}

			//for insert button
			else if(insert == true) {
				try {
					record = new TransactionRecord(textField.getText()); // Creates a new TransactionRecord object using the text in the textbox

					listOfTransactions.insert(record); // Calls the insert method to insert the record into the list

					summary.setText(listOfTransactions.toString()); // Displays the records

				}

				catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "Invalid Record. Please Check The Format Again."); // Tells the user that the record wasn't able to be inserted
				}

			}

			//for delete button
			else if(delete == true) {
				try {
					String dateFormat = "yyyy-M-dd hh:mm:ss"; // Variable that stores the format of the date
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat); // Variable that formats a given date

					// Executes if the record was found and deleted
					if (listOfTransactions.delete(simpleDateFormat.parse(textField.getText()))) {
						summary.setText(listOfTransactions.toString()); // Displays the records
					}

					else {
						JOptionPane.showMessageDialog(null, "Record Wasn't Found"); // Tells the user that the record wasn't found
					}

				}

				catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "Invalid Date and Time. Please Check The Format Again."); // Tells the user that the record wasn't formatted properly
				}

			}

			//for save button
			else if(save == true) {
				try {
					listOfTransactions.insertionSort(); // Calls the insertionSort method to sort the records

					File specifiedFile = new File(textField.getText() + "File.txt"); // Creates a new File variable that stores the name of the file that the user wants to save to

					// Executes if the file exists
					if (specifiedFile.exists()) {
						FileAccess.writeData(textField.getText(), null, listOfTransactions, false); // Calls the writeData method to write the data into the file
					}

					// Executes if the file doesn't exist
					else {
						JOptionPane.showMessageDialog(null, "File Not Found"); // Tells the user that the file wasn't found
					}
				}

				catch (IOException e1) {

				}
			}

		}
		//if cancel button is clicked
		else {
			//hide the components
			showComponents(false, false, false, false);
		}

	}

	/**
	 * method to choose which components become visible from button clicks
	 */
	public void showComponents (boolean text, boolean textBox, boolean enter, boolean cancel) {
		//makes the selected component visible depending on the input
		prompt.setVisible(text);
		textField.setVisible(textBox);
		btnEnter.setVisible(enter);
		btnCancel.setVisible(cancel);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new BERecords();

	}

}