package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.CategoryField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryFieldRepositary extends CrudRepository<CategoryField,Integer> {

    CategoryField findByName(@Param("name") String name);
    //Boolean exsistByName(@Param("name") String name);

}
