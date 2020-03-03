package com.example.vignesh.textrecognitionimage.Model;

/**
 * User object
 */
public class User {

    public User() {
        firstName = "";
        lastName = "";
        username = "";
        email = "";
        password = "";
        phoneNumber = "";
        address = "";
        premium = "false";
    }

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String username;
    private String premium;

    /**
     * Get userID
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Set userID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get firstName of user
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set firstName of user
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get LastName of user
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set LastName of user
     * @return
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get email of user
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email of user
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get password of user
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password of user
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get phone number of user
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set phone number of user
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get address of user
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set address of user
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get premium status
     * @return
     */
    public String getPremium() {
        return premium;
    }

    /**
     * Set premium status
     * @param premium
     */
    public void setPremium(String premium) {
        this.premium = premium;
    }

    /**
     * Get username for user for chatting
     * @return
     */
    public String getUsername() {return username; }

    /**
     * Set username for user for chatting
     * @param userName
     */
    public void setUsername(String userName) {this.username = userName; }
}
