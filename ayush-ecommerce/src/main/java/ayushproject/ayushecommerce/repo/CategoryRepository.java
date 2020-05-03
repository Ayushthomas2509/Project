package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category,Integer> {

    Category findByName(@Param("name") String name);

    Optional<Category> findByParentCategory(Category parentCategory);
    //Boolean exsistByName(@Param("name") String name);
    //Boolean existsByName(@Param("name") String name);
}
