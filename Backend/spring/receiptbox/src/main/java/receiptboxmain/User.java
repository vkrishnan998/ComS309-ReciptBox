package receiptboxmain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.UpdateTimestamp;

/**
 * User Model Class
 * @author kaminisaldanha
 *
 */
@Entity
@Table(name = "user")
public class User{
	
	@Column(name = "user_id")
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int user_id; 
	
	@Column(name = "first_name")
	private String first_name; 
	
	@Column(name = "last_name")
	private String last_name;
	
	@Column(name = "email_id")
	private String email_id;
	
	@Column(name = "phone_number")
	private String phone_number;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "premium")
	private String premium;
	
	@Column(name = "username")
	private String username;
	
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updated_at;
	
    /**
     * Gets the user id for this user
     * @return user_id
     */
	public int getUserId() {
		return this.user_id;
	}
	
	/**
	 * Sets the user id for this user
	 * @param userId is what you'd like to set the user_id to
	 */
	public void setUserId(Integer userId) {
		this.user_id = userId;
	}
	
	/**
	 * Gets the first name of this user
	 * @return first_name
	 */
	public String getFirstName() {
		return this.first_name;
	}
	
	/**
	 * Sets the first name of this user
	 * @param firstName is what you'd like to set the first_name to
	 */
	public void setFirstName(String firstName) {
		this.first_name = firstName;
	}
	
	/**
	 * Gets the last name of this user
	 * @return last_name
	 */
	public String getLastName() {
		return this.last_name;
	}
	
	/**
	 * Sets the last name of this user
	 * @param lastName is what you'd like to set the last_name to
	 */
	public void setLastName(String lastName) {
		this.last_name = lastName;
	}
	
	/**
	 * Gets the email ID for this user
	 * @return email_id
	 */
	public String getEmailId() {
		return this.email_id;
	}
	
	/**
	 * Sets the email ID for this user
	 * @param emailId is what you'd like to set the email_id to
	 */
	public void setEmailId(String emailId) {
		this.email_id = emailId;
	}
	
	/**
	 * Gets the phone number for this user
	 * @return phone_number
	 */
	public String getPhoneNumber() {
		return this.phone_number;
	}
	
	/**
	 * Sets the phone number for this user
	 * @param phoneNumber is what you'd like to set the phone_number to
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phone_number = phoneNumber;
	}
	
	/**
	 * Gets the address for this user
	 * @return address
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * Sets the address for this user
	 * @param address is what you'd like to set the address to
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Gets the password for this user
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Sets the password for this user
	 * @param password is what you'd like to set the password to 
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets whether this user is premium or not
	 * @return premium (Yes if premium and no if not premium)
	 */
	public String getPremium() {
		return this.premium;
	}
	
	/**
	 * Sets whether this user is premium or not
	 * @param premium is what you'd like to set premium to (Yes or no)
	 */
	public void setPremium(String premium) {
		this.premium = premium;
	}
	
	/**
	 * Gets the username of this user
	 * @return username
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Sets the username of this user
	 * @param username is what you'd like to set the username to
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gets the date this user information was last entered into the database
	 * @return date
	 */
	public Date getDate() {
		return this.updated_at;
	}
	    
	/**
	 * Sets the date this user information was last entered into the database
	 * @param date is what you'd like to change the date to
	 */
	public void setDate(Date date) {
	    this.updated_at = date;
	}
	
}
