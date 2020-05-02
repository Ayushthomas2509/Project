package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.CategoryFieldValues;
import ayushproject.ayushecommerce.entities.CompositeKeyFieldValues;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryFeildValueRepo extends CrudRepository<CategoryFieldValues, CompositeKeyFieldValues>{
}
