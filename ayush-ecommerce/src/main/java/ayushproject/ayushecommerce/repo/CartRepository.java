package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepository extends CrudRepository<Cart,Integer> {
    @Query(value = "select * from Cart",nativeQuery = true)
    List<Cart> allcarts();
    Cart findByuserid(@Param("userid") Integer userid);
}
