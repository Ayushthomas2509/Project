package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<Orders,Integer> {
}
