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

import java.util.List;
import java.util.Optional;

/**
 * User Controller class with the GET and POST methods for the User objects
 * @author kaminisaldanha
 *
 */
@RestController
public class UserController {
	
    @Autowired
    UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Saves a user in the database
     * @param user this is the user we want to save in the database
     * @return the saved user
     */
    @RequestMapping(method = RequestMethod.POST, path = "/user/new")
    public User saveUser(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    /**
     * Gets all users 
     * @return every single user saved in the database
     */
    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<User> getAllUsers() {
        logger.info("Entered into Controller Layer");
        List<User> results = (List<User>) userRepository.findAll();
        logger.info("Got list of users");
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    /**
     * Validates whether the email and password typed in exists as a user on the login page
     * @param email this is the email the user types in
     * @param password this is the password the user types in
     * @return the userId if they exist and 0 if they do not exist, -1 if there's an error
     */
    @RequestMapping(method = RequestMethod.GET, path = "/validate/{email}")
    public int validateUser(@PathVariable("email") String email, @RequestParam("password") String password) {
    
    	try {
    		logger.info("Entered into Controller Layer");

        	List<User> results = (List<User>) userRepository.findAll();
        	logger.info("Got list of users");
        	
        	if(email == null || password == null) {
        		return 0;
        	}
        	
        	for(User user: results) {
    			if(user.getEmailId().equals(email) && user.getPassword().equals(password)) {
        			return user.getUserId();
    			}
        	}
        	
        	return 0;
        	
    	} catch(NullPointerException n) {
    		return -1;
    	}
    }

    /**
     * Get a user by their userId 
     * @param id this is the userId for that specific user that is passed as a path variable
     * @return the user that has that userId
     */
    @RequestMapping(method = RequestMethod.GET, path = "/getUserById/{uid}")
    public Optional<User> findUserById(@PathVariable("uid") int id) {
        logger.info("Entered into Controller Layer");
        Optional<User> results = userRepository.findById(id);
        logger.info("Found user by id");
        return results;
    }

}