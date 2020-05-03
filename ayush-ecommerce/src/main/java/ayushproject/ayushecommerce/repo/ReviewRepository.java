package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.Reviews;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Reviews,Integer>{
Iterable<Reviews> findByProductId(@Param("productId")Integer productId);
}