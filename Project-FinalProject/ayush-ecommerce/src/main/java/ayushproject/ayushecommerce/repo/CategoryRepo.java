package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepo extends PagingAndSortingRepository<Category,Integer> {

    Category findByName(@Param("name") String name);
    //Boolean exsistByName(@Param("name") String name);
    //Boolean existsByName(@Param("name") String name);
}
