package practiceQuery;

import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CriteriaController {

    @Autowired
    CriteriaQueryAdmin criteriaQueryAdmin;
    @Autowired
    UserService userService;

    @GetMapping("/users/get")
    public User findUser(@RequestParam Integer id){
        userService.ensureUser();
        return criteriaQueryAdmin.findById(id);
    }
}
