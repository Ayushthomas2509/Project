package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.services.AdminService;
import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    UserService  userService;


}
