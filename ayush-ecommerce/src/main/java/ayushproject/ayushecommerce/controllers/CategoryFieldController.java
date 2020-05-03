package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.entities.CategoryField;
import ayushproject.ayushecommerce.services.CategoryFieldService;
import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryFieldController {
    @Autowired
    CategoryFieldService categoryFieldService;
    @Autowired
    UserService userService;

    @GetMapping("/category-fields")
    public Iterable<CategoryField> findAll(){
        userService.ensureUser();
        return categoryFieldService.findAll();}

    @PostMapping("/add/category-field")
    public String addCategory(@RequestParam Integer categoryId,@RequestParam String value,@RequestParam String name){
        userService.ensureAdmin();
        return categoryFieldService.addCategoryField(categoryId,value,name);
    }

}
