package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepo extends CrudRepository<Customer,Integer>{

    @Query("from Customer where name=:name")
    Customer findByname(@Param("name") String name);

}