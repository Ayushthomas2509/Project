package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.CategoryField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryFieldRepo extends CrudRepository<CategoryField,Integer> {

    CategoryField findByName(@Param("name") String name);
    //Boolean exsistByName(@Param("name") String name);

}
