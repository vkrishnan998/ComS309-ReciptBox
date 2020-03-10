package receiptboxmain.websockets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Websocket Server class
 * @author kaminisaldanha
 *
 */
@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketServer {
	
	//Store all socket sessions and their corresponding username
	private static Map<Session, String> sessionUsernameMap = new HashMap<>();
	private static Map<String, Session> usernameSessionMap = new HashMap<>();
	
    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    
    /**
     * Enters this session 
     * @param session the session you are currently on
     * @param username the username of the user who is entering the session
     * @throws IOException 
     */
    @OnOpen
    public void onOpen(
    	Session session, 
    	@PathParam("username") String username) throws IOException {
        
    		logger.info("Entered into Open");
    		sessionUsernameMap.put(session, username);
    		usernameSessionMap.put(username, session);
    }
 
    /**
     * When a user would like to send a message 
     * @param session the session you are currently on
     * @param message the message this user is sending
     * @throws IOException
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        
    	// Handle new messages
    	logger.info("Entered into Message: Got Message: "+message);
    	String username = sessionUsernameMap.get(session);
    	
    	String echo= "";
    	sendMessageToPArticularUser(session, echo);
    	broadcast(message);
    }
 
    /**
     * When a user is leaving a chat
     * @param session the session you are currently in
     * @throws IOException
     */
    @OnClose
    public void onClose(Session session) throws IOException{
    	logger.info("Entered into Close");
    	
    	String username = sessionUsernameMap.get(session);
    	sessionUsernameMap.remove(session);
    	usernameSessionMap.remove(username);
    	
        String message="Disconnected";
        broadcast(message);
    }
 
    /**
     * If there is an error in the web socket chat application
     * @param session the session you are currently on
     * @param throwable the error 
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    	logger.info("Entered into Error");
    }
    
    /**
     * If you'd like to send a message to a specific user
     * @param session the session you are currently on
     * @param message the message you'd like to send that user
     */
    private void sendMessageToPArticularUser(Session session,String message) {
    	
    	try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
        	logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }
    
    /**
     * If you'd like to send a message to all users online
     * @param message the message you'd like to send them 
     * @throws IOException
     */
    private static void broadcast(String message) throws IOException {	  
    	sessionUsernameMap.forEach((session, username) -> {
  		synchronized (session) {
	            try {
	                session.getBasicRemote().sendText(message);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}

}
