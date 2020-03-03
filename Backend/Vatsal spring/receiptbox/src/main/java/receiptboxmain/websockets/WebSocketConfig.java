package receiptboxmain.websockets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket Configuration class
 * @author kaminisaldanha
 *
 */
@Configuration
public class WebSocketConfig {
	
	/**
	 * Runs the web socket endpoint exporter
	 * @return a new server endpoint exporter
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter(){
		return new ServerEndpointExporter(); 
		}
}
