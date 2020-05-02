package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends CrudRepository<Orders,Integer> {
}
