package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepo extends CrudRepository<User,Integer> {
    @Query("from User")
    List<User> allUsers();

    User findByname(@Param("name") String name);

//    @Modifying
//    @Transactional
//    @Query(value = "update User set password=:password where email=:email ")
//    void updatePassword(@Param("password") String password,@Param("email") String email);
}
