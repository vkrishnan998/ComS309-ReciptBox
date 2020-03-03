package receiptboxmain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Receipt controller class with the GET and POST methods for Receipt objects 
 * @author kaminisaldanha
 *
 */
@RestController
public class ReceiptController {
	
	@Autowired
    ReceiptRepository receiptsRepository;

    private final Logger logger = LoggerFactory.getLogger(ReceiptController.class);
 
    /**
     * Save receipt in database for a particular user
     * @param receipt
     * @return message saying whether the receipt was saved or throws an error if it doesn't
     */
    @RequestMapping(method = RequestMethod.POST, path = "/receipt/saveReceipt")
    public Receipt saveReceipt(@RequestBody Receipt receipt) {
    	
    	try {
    		receiptsRepository.save(receipt);
    		return receipt;
    		
    	} catch (Exception e) {
    		e.getMessage();
    		throw new Error("Unable to save receipt in database");
    	}
    }
    
    /**
     * Get all the receipts for a particular user
     * @param id is the user id for the user that we are trying to get all the receipts for
     * @return a list of all receipts for that user id 
     */
    @RequestMapping(method = RequestMethod.GET, path = "/receipt/getAllReceiptsForUser/{uid}")
    public List<Receipt> getAllReceiptsForUser(@PathVariable("uid") int userId) {
     
        List<Receipt> userReceipts = new ArrayList<Receipt>();
      
        try {
        	
        	 logger.info("Entered into Controller Layer");
        	 List<Receipt> results = (List<Receipt>) receiptsRepository.findAll();
        	 logger.info("Got all receipts successfully for all users");
        	 
        	 for(Receipt receipt: results) {
             	if(receipt.getUserId() == userId) {
             		userReceipts.add(receipt);
             		logger.info("Got " + userReceipts.size() +" receipts for userid " + userId);
             			 
             	}
             }
        	 return userReceipts; 
        	
        	 
        } catch (Exception e) {
        	e.getMessage();
    		throw new Error("Unable to get all receipts for user");
        }    
    }
    
    /**
     * Changes the name of the receipt. It first looks for the receipt by the receiptId and then changes its name to the new name.
     * @param receiptId the receiptId of the receipt you want to change
     * @param receiptName the name you would like to change the receiptName to
     * @return receiptName String with the new receiptName 
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/receipt/renameReceipt/{receiptId}")
    public String changeReceiptName(@PathVariable("receiptId") int receiptId, @RequestParam("receiptName") String receiptName) {
    	
    	try {
    		logger.info("Entered into Controller Layer");
       	 	List<Receipt> results = (List<Receipt>) receiptsRepository.findAll();
       	 	logger.info("Got all receipts successfully for all users");
       	 	
       	 	for(Receipt receipt: results) {
       	 		if(receipt.getReceiptId() == receiptId) {
       	 			receipt.setReceiptName(receiptName);
       	 			receiptsRepository.save(receipt);
       	 			logger.info("Changed name of receipt to " + receiptName);
       	 			return "Receipt name changed to " + receiptName;
          		}
       	 	}
       	 	
       	 	return null;
       	 	
    	} catch (Exception e) {
    		e.getMessage();
    		throw new Error("Unable to change receipt name");
    	}
    	
    }

    /**
     * Find the receipt by its receiptName
     * @param receiptName the receiptName you are looking for
     * @param userId the userId of the user with this receipt that you'd like to change the name of
     * @return the receipt with the receiptName
     */
    @RequestMapping(method = RequestMethod.GET, path = "/receipt/getReceiptByName/{receiptName}")
    public Receipt findReceiptByName(@PathVariable("receiptName") String receiptName, @RequestParam("userId") int userId) {
       
        try {
        	 logger.info("Entered into Controller Layer");
        	 List<Receipt> results = (List<Receipt>) receiptsRepository.findAll();
        	 
        	 for(Receipt receipt: results) {
        		 if(receipt.getReceiptName().equals(receiptName) && receipt.getUserId() == userId) {
        			 return receipt;
        		 }
        	 }
        	 
        	 return null;
        	 
        } catch (Exception e) {
        	e.getMessage();
    		throw new Error("Unable to find this receipt. Sorry!");
        }  
    }
    
    /**
     * Gets all the receipts belong to a user in the latest month that they have saved a receipt in the database
     * @param userId the userId of the user 
     * @return a list of all the latest receipts 
     */
    @RequestMapping(method = RequestMethod.GET, path = "/receipt/getReceiptsForMostRecentMonth/{userId}")
    public List<Receipt> getReceiptsForMostRecentMonth(@PathVariable("userId") int userId){
    	
    	//all receipts that belong to this user
    	List<Receipt> receipts = new ArrayList<>();
    	
    	//all receipts that belong to this user in the latest month
    	List<Receipt> currentReceipts = new ArrayList<>();
    	
    	try {
    		
    		logger.info("Entered into Controller Layer");
    		List<Receipt> results = (List<Receipt>) receiptsRepository.findAll();
    		logger.info("Got all receipts");
    		
    		for(Receipt receipt: results) {
    			
    			if(receipt.getUserId() == userId) {
    				receipts.add(receipt);
    				logger.info("Added a receipt with the userId: " +userId +" to the list of receipts with that userId");
    			}	
    		}
    		
    		String maxMonth = (receipts.get(0).getDate() + "").substring(0, 7);
    		logger.info("Got the first month to find the max month");
    		
    		for(Receipt receipt: receipts) {
    			
    			String date = (receipt.getDate() + "").substring(0, 7);
    			
    			if(date.compareTo(maxMonth) > 0) {
    				maxMonth = date;
    			}
    		}
    		
    		logger.info("Got the max month");	
    		for(Receipt receipt: receipts) {
    			
    			String date = (receipt.getDate() + "").substring(0, 7);
    			
    			if(date.compareTo(maxMonth) == 0) {
    				currentReceipts.add(receipt);
    			}
    		}
    		
    		return currentReceipts;
    		
    		
    	} catch (Exception e) {
    		e.getStackTrace();
    		throw new Error("Unable to get the most recent receipts");
    	}
    	
    }
    
     @RequestMapping(method = RequestMethod.GET, path = "/receipt/getPreviousMonthsReceipts/{uid}")
	 public List<Receipt> getReceiptsFromLastMonth(@PathVariable("uid") int userId){
		 
		 List<Receipt> receipts = new ArrayList<>();
    	 
    	 try {
			 
			 logger.info("Entered into Controller Layer");
	       	 List<Receipt> results = (List<Receipt>) receiptsRepository.findAll();
	       	 String first = (results.get(0).getDate() + "").substring(0, 7);
	       	 String second = (results.get(0).getDate() + "").substring(0, 7);
	       	 
	       	 for(Receipt receipt: results) {
	       		 if(receipt.getUserId() == userId) {	 
	       			 String date = (receipt.getDate() + "").substring(0, 7);
	       			 if(date.compareTo(first) > 0) {
	       				 second = first;
	       				 first = date;
	       			 }
	     
	       			 else if(date.compareTo(second) > 0 && (!date.equals(first))) {
	       				 second = date;
	       			 }	 
	       		 }
	       	 }
	       	 
	       	 for(Receipt receipt: results) {
	       		 if(receipt.getUserId() == userId &&
	       				 (receipt.getDate() + "").substring(0,  7).compareTo(second) == 0) {
	       			 		receipts.add(receipt);
	       		 }		 
	       	 }
			 
	       	 return receipts;
	       	 
		 } catch (Exception e) {
			 e.getMessage();
			 throw new Error("Unable to get the receipts from the previous month");
		 }
		 
	 }
    
    /**
	  * Gets the latest receiptId belonging to a user 
	  * @param userId the userId for the user you are looking for
	  * @return the receiptId of the latest receipt added to the database for that user 
	  */
	 @RequestMapping(method = RequestMethod.GET, path = "/receipt/getCurrentMonthReceipts/{uid}")
	 public int getLatestReceiptIdOfUser(@PathVariable("uid") int userId) {
		 
		 int receiptIdOfMax = 0;
		 
		 try {
			 
			 logger.info("Entered into Controller Layer");
	       	 List<Receipt> results = (List<Receipt>) receiptsRepository.findAll();
	       	 String max = results.get(0).getDate() + "";
	       	 
	       	 for(Receipt receipt: results) {
	       		 
	       		 if(receipt.getUserId() == userId) {
	       			 
	       			 String date = receipt.getDate() + "";
	       			 
	       			 if(date.compareTo(max) > 0) {
	       				 max = date;
	       				 receiptIdOfMax = receipt.getReceiptId();
	       			 }	 
	       		 }
	       	 }
       	 
       	 return receiptIdOfMax;
			 
		 } catch (Exception e) {
			 e.getMessage();
	    	throw new Error("Unable to get latest receipt id for user");
		 }
		 
	 }
	 

}
