package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.CartProductVariation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartProductVariationRepository extends CrudRepository<CartProductVariation, Integer> {

    List<CartProductVariation> findByCartId(Integer userid);

    @Query(value = "select * from cart_product_variation where cart_id=:cartId and variation_id=:productId",nativeQuery = true)
    Optional<CartProductVariation> findByCartIdAndProductId(@Param("cartId") Integer cartId, @Param("productId") Integer productID);
}
