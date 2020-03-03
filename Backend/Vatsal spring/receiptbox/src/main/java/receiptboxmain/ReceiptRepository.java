package receiptboxmain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Receipt Repository
 * @author kaminisaldanha
 *
 */
@Repository
public interface ReceiptRepository extends CrudRepository<Receipt, Integer> {
	
}

