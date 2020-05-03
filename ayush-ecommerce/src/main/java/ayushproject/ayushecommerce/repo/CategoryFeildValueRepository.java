package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.CategoryFieldValues;
import ayushproject.ayushecommerce.entities.CompositeKeyFieldValues;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryFeildValueRepository extends CrudRepository<CategoryFieldValues, CompositeKeyFieldValues>{
}
