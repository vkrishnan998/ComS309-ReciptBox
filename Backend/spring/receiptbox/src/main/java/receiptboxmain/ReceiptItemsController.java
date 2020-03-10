package receiptboxmain;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ReceiptItems Controller with the GET and POST requests for the receipt items
 * @author kaminisaldanha
 *
 */
@RestController
public class ReceiptItemsController {
	
	@Autowired
    ReceiptItemsRepository receiptItemsRepository;
	
	@Autowired
	ReceiptRepository receiptRepository;

    private final Logger logger = LoggerFactory.getLogger(ReceiptController.class);
	
    /**
     * Saves this receipt item in the receipt item table in the database
     * @param item is the receipt item getting saved
     * @return item if it is successful 
     */
	 @RequestMapping(method = RequestMethod.POST, path = "/receiptItems/saveItems")
	 public ReceiptItems saveReceiptItems(@RequestBody ReceiptItems item) {
		 
		 try {		 
			 
				receiptItemsRepository.save(item); 
			 return item;
				 
		 } catch (Error e) {
			 e.getMessage();
	    	 throw new Error("Unable to save items in database");
		 }
		 
	 }
    
	 /**
	  * Gets the receipt items that belong to to a particular receipt
	  * @param userId is the id of the user to get the receipt items for
	  * @param receipt_id the id of the receipt belonging to the user to get the receipt items for
	  * @return the list of receipt items belonging to the user with the receiptId and userId
	  */
	 @RequestMapping(method = RequestMethod.GET, path = "/receiptItems/getParticularReceiptItems/{uid}")
	 public List<ReceiptItems> getReceiptItemsForParticularReceipt(@PathVariable("uid") int userId, @RequestParam("receiptId") int receipt_id) {
	     
		 	List<ReceiptItems> receiptItems = new ArrayList<ReceiptItems>();
	      
	        try {
	        	 logger.info("Entered into Controller Layer");
	        	 List<ReceiptItems> results = (List<ReceiptItems>) receiptItemsRepository.findAll();
	        	 logger.info("Got all receipts successfully for all users");
	        	 
	        	 for(ReceiptItems item: results) {
	             	if(item.getReceiptId() == receipt_id && item.getUserId() == userId) {
	             		receiptItems.add(item);
	             	}
	             }
	        	 
	        	 logger.info("Got " + receiptItems.size() +" receipts for userid " + userId);
	        	 return receiptItems; 	 
	        	 
	        } catch (Exception e) {
	        	e.getMessage();
	    		throw new Error("Unable to get all receipts for user");
	        } 
	 }	
	 
	 /**
	  * Gets all receipt items for a particular user
	  * @param userId the userId of the user you want to get all receipt items for
	  * @return the list of receipt items for that user
	  */
	 @RequestMapping(method = RequestMethod.GET, path = "/receiptItems/getAllItems/{uid}")
	 public List<ReceiptItems> getAllReceiptItemsForUser(@PathVariable("uid") int userId) {
	     
		 	List<ReceiptItems> receiptItems = new ArrayList<ReceiptItems>();
	      
	        try {
	        	 logger.info("Entered into Controller Layer");
	        	 List<ReceiptItems> results = (List<ReceiptItems>) receiptItemsRepository.findAll();
	        	 logger.info("Got all receipts items successfully for all users");
	        	 
	        	 for(ReceiptItems item: results) {
	             	if(item.getUserId() == userId) {
	             		receiptItems.add(item);
	             	}
	             }
	        	 
	        	 logger.info("Got " + receiptItems.size() +" receipts for userid " + userId);
	        	 return receiptItems; 	 
	        	 
	        } catch (Exception e) {
	        	e.getMessage();
	    		throw new Error("Unable to get all receipts for user");
	        } 
	 }
	 
}
