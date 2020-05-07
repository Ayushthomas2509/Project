package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.Category;
import ayushproject.ayushecommerce.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends CrudRepository<Product,Integer> {

    @Query("from Product where category=:category")
    List<Product> perCategory(@Param("category")Category category);

    @Query("from Product where sellerId=:sellerId")
    List<Product> perSellerProducts(@Param("sellerId") Integer sellerId);

//    @Query(value = "select brand, price from product where category=:category",nativeQuery = true)
    List<Product> findByCategory(Category category);

    List<Product> findByName(String name);

    @Query(value = "select * from product where quantity<=2",nativeQuery = true)
    List<Product> findAllByQuantity();

    @Query(value = "select count(*) as c, category_id from product group by category_id",nativeQuery =  true)
    List<Integer[]> countByCategoryId();
}
