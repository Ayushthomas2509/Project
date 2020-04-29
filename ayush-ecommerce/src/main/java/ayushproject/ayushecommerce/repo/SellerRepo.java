package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SellerRepo extends CrudRepository<Seller,Integer> {
    Seller findByName(@Param("name")String name);
}
