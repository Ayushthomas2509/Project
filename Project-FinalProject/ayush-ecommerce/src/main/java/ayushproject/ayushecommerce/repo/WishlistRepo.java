package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.Wishlist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishlistRepo extends CrudRepository<Wishlist,Integer> {
    @Query(value = "select * from wishlist",nativeQuery = true)
    List<Wishlist> allCarts();
    Iterable<Wishlist> findByUserId(@Param("userid")Integer userId);
}
