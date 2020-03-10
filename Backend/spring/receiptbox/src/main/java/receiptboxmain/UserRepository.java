package receiptboxmain;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * User model repository
 * @author kaminisaldanha
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	

}