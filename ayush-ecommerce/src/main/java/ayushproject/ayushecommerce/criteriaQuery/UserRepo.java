package ayushproject.ayushecommerce.criteriaQuery;

import ayushproject.ayushecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepo extends CrudRepository<User, Integer>, JpaSpecificationExecutor<User>{

}