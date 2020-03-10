package receiptboxmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The ReceiptBox application class from where the backend SprinBoot runs
 * @author kaminisaldanha
 *
 */
@SpringBootApplication
//@ComponentScan(basePackages= {"websockets"})
public class ReceiptboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceiptboxApplication.class, args);
	}
}
