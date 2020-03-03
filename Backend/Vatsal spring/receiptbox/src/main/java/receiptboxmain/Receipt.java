package receiptboxmain;

import java.awt.Image;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.core.style.ToStringCreator;

/**
 * Receipt Model Class
 * @author kaminisaldanha
 *
 */
@Entity
@Table(name = "receipt")
public class Receipt {
	
	@Column(name = "receipt_id")
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int receipt_id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "store_name")
    private String store_name;
    
    @Column(name = "phone_number")
    private String phone_number;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "subtotal")
    private int subtotal;
    
    @Column(name = "tax")
    private int tax;
    
    @Column(name = "completetotal")
    private double completetotal;
    
    @Column(name = "receiptName")
    private String receipt_name;
    
    @Column(name = "dateadded")
    private String date_added;
    
    @Column(name = "category")
    private String category;
    
    @Lob
    @Column(name = "image")
    private byte[] image;

    /**
     * Gets the receipt ID for this receipt
     * @return receipt_id
     */
    public int getReceiptId() {
        return this.receipt_id;
    }
    
    /**
     * Sets the receipt ID for this receipt
     * @param receiptId is what you'd like to set receipt_id to
     */
    public void setReceiptId(int receiptId) {
    	this.receipt_id = receiptId;
    }
    
    /**
     * Gets the user ID for this receipt
     * @return user_id
     */
    public int getUserId() {
        return this.user_id;
    }
    
    /**
     * Sets the user ID for this receipt
     * @param user_id is what you'd like to set user_id to
     */
    public void setUserId(int userId) {
        this.user_id = userId;
    }
    
    /**
     * Gets the store name for this receipt
     * @return store_name
     */
    public String getStoreName() {
        return this.store_name;
    }
    
    /**
     * Sets the store name for this receipt
     * @param sname is what you'd like to set store_name to
     */
    public void setStoreName(String sname) {
        this.store_name = sname;
    }
    
    /**
     * Gets the phone number for this receipt
     * @return phone_number 
     */
    public String getPhoneNumber() {
        return this.phone_number;
    }

    /**
     * Sets the phone number for this receipt
     * @param number is what you'd like to set phone_number to
     */
    public void setPhoneNumber(String number) {
        this.phone_number = number;
    }
    
    /**
     * Gets the address for this receipt
     * @return address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Sets the address for this receipt
     * @param address is what you'd like to set address to 
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * Gets the subtotal for this receipt
     * @return subtotal
     */
    public int getSubtotal() {
        return this.subtotal;
    }
    
    /**
     * Sets the subtotal for this receipt
     * @param subTotal is what you'd like the subtotal to
     */
    public void setSubTotal(int subTotal) {
    	this.subtotal = subTotal;
    }
    
    /**
     * Gets the tax for this receipt
     * @return tax
     */
    public int getTax() {
        return this.tax;
    }
    
    /**
     * Sets the tax for this receipt
     * @param tax is what you'd like the tax to
     */
    public void setTax(int tax) {
    	this.tax = tax;
    }
    
    /**
     * Gets the complete total for this receipt
     * @return completetotal
     */
    public double getCompletetotal() {
        return this.completetotal;
    }
    
    /**
     * Sets the complete total of this receipt
     * @param completeTotal is what you'd like to set completetotal to
     */
    public void setCompleteTotal(double completetotal) {
    	this.completetotal = completetotal;
    }
    
    /**
     * Gets the receipt name for this receipt
     * @return receipt_name
     */
    public String getReceiptName() {
    	return this.receipt_name;
    }
    
    /**
     * Sets the receipt name for this receipt
     * @param receiptName is what you'd like to set the receipt_name to
     */
    public void setReceiptName(String receiptName) {
    	this.receipt_name = receiptName;
    }
    
    /**
     * Gets the category of the receipt
     * @return category
     */
    public String getCategory() {
    	return this.category;
    }
    
    /**
     * Sets the category of this receipt
     * @param category is what you'd like to set category to
     */
    public void setCategory(String category) {
    	this.category = category;
    }
    
    /**
     * Gets the date this receipt was added into the database
     * @return
     */
    public String getDate() {
    	return this.date_added;
    }
    
    /**
     * Gets the date this receipt was added to the new date added
     * @param dateAdded is what you'd like to change the date_added to
     */
    public void setDate(String dateAdded) {
    	this.date_added = dateAdded;
    }
    
    /**
     * Gets the image in this receipt
     * @return image
     */
    public byte[] getImage() {
    	return this.image;
    }
    
    /**
     * Sets the image in the database 
     * @param image is what you'd like to set the image to
     */
    public void setImage(byte[] image) {
    	this.image = image;
    }
}
