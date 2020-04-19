package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.Reviews;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewRepo extends CrudRepository<Reviews,Integer>{
Iterable<Reviews> findByProductId(@Param("productId")Integer productId);
}