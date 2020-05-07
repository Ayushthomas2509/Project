package practiceQuery;

import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.repo.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

interface UserRepo extends JpaRepository<User, Integer>, UserRepository, JpaSpecificationExecutor<User>{

}