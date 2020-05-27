package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.OrderProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepo extends CrudRepository<OrderProduct, Integer> {
}
