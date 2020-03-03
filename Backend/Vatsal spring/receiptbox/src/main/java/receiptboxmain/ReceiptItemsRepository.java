package receiptboxmain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Receipt Items repository
 * @author kaminisaldanha
 *
 */
@Repository
public interface ReceiptItemsRepository extends CrudRepository<ReceiptItems, Integer> {
	
}
