package receiptboxmain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

/**
 * ReceiptItems Model Class
 * @author kaminisaldanha
 *
 */
@Entity
@Table(name = "receipt_items")
public class ReceiptItems {
	
	@Column(name = "receipt_id")
	private int receipt_id;
	
	@Column(name = "user_id")
	private int user_id;
	
	@Column(name = "item_name")
	private String item_name;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "item_id")
	private int item_id;
	
	@Column(name = "item_price")
	private double item_price;
	
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updated_at;
	
    /**
     * Gets the receipt id for this receipt item
     * @return receipt_id
     */
	public int getReceiptId() {
		return this.receipt_id;
	}
	
	/**
	 * Sets the receipt id for this receipt item
	 * @param receiptId is what you would like to set the receipt_id to
	 */
	public void setReceiptId(int receiptId) {
		this.receipt_id = receiptId;
	}
	
	/**
	 * Gets the user id for this receipt item
	 * @return user_id
	 */
	public int getUserId() {
		return this.user_id;
	}
	
	/**
	 * Sets the user id for this receipt item
	 * @param user_id is what you would like to set the user_id to
	 */
	public void setUserId(int userId) {
		this.user_id = userId;
	}
	
	/**
	 * Gets the name of this item 
	 * @return item_name
	 */
	public String getItemName() {
		return this.item_name;
	}
	
	/**
	 * Sets the name of this item
	 * @param itemName is what you'd like to set the item_name to
	 */
	public void setItemName(String itemName) {
		this.item_name = itemName;
	}
	
	/**
	 * Gets the item id of this receipt item
	 * @return item_id
	 */
	public int getItemId() {
		return this.item_id;
	}
	
	/**
	 * Gets the item price for this receipt item
	 * @return item_price
	 */
	public double getItemPrice() {
		return this.item_price;
	}
	
	/**
	 * Sets the item price for this receipt item
	 * @param itemPrice is what you'd like to set the item_price to
	 */
	public void setItemPrice(double itemPrice) {
		this.item_price = itemPrice;
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
	 * @param date is what you'd like to set the date to
	 */
	public void setDate(Date date) {
	    this.updated_at = date;
	}
	
}
