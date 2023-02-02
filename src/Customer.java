/**
 * 
 */

/**
 * @author diyaparmar
 * Date: April 2021
 * 
 * Description: The Customer class is responsible for attaining the customers
 * information which includes their name, address, phone number, user-name and password.
 * 
 * Methods:
 * --> public Customer(String record)
 * Overloaded constructor to take in a string input that has all the customer information and is split by " , "
 * 
 * Setter and getter methods for all the variables (name, address, phone number, user-name and password)
 * --> public String getName()
 * --> public void setName(String name)
 * --> public String getAddress()
 * --> public void setAddress(String address)
 * --> public String getPhoneNumber()
 * --> public void setPhoneNumber(String phoneNumber)
 * --> public String getUserName()
 * --> public void setUserName(String userName)
 * --> public String getPassword()
 * --> public void setPassword(String password)
 * 
 * --> public static void main(String[] args)
 * Self - Testing main
 *
 */
public class Customer {

	//declare instance data
	private String name;
	private String address;
	private String phoneNumber;
	private String userName;
	private String password;


	/**
	 * Default Constructor
	 */
	public Customer() {

		//initialize all data to an empty string
		this.name = "";
		this.address = "";
		this.phoneNumber = "";
		this.userName = "";
		this.password = "";
	}

	/**
	 * Overloaded Constructor that passes in the String input which includes
	 * the customers info (name, address, phone number, user name, password)
	 */

	public Customer(String input) {

		//splits the single string input 
		String words [];
		words = input.split("/");

		this.userName = words[0];
		this.password = words[1];
		this.name = words [2];
		this.address = words[3];
		this.phoneNumber = words[4];

	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * toString method to format the record 
	 */
	public String toString() {
		return getUserName() + "/" + getPassword() + "/" + getName() + "/" + getAddress() + "/" + getPhoneNumber();
	}

	/**
	 * @param args
	 * Self-Testing Main
	 */
	public static void main(String[] args) {

		//create an object of the Customer class
		Customer cInfo = new Customer("diva123/password/Diya/123 hollywood st/123-456-7890");

		//test the getters within the toString method
		System.out.println(cInfo.toString());

		//test the setters
		cInfo.setName("Rory");
		cInfo.setAddress("Stars Hallow");
		cInfo.setPhoneNumber("456-678-789");
		cInfo.setUserName("lukesDiner123");
		cInfo.setPassword("jess1234");

		//display result
		System.out.println(cInfo.toString());
	}

}