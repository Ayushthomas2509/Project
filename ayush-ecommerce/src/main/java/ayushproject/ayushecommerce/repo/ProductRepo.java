package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product,Integer> {

    @Query("from Product where category=:category")
    List<Product> perCategory(@Param("category")String category);

    @Query("from Product where sellerId=:sellerId")
    List<Product> perSellerProducts(@Param("sellerId") Integer sellerId);
}
