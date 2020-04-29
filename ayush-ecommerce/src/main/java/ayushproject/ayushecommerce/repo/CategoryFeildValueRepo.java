package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.CategoryFieldValues;
import ayushproject.ayushecommerce.entities.CompositeKeyFieldValues;
import org.springframework.data.repository.CrudRepository;

public interface CategoryFeildValueRepo extends CrudRepository<CategoryFieldValues, CompositeKeyFieldValues>{
}
