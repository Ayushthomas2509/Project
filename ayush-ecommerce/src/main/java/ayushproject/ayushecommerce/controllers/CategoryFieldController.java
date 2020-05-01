package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.entities.CategoryField;
import ayushproject.ayushecommerce.services.CategoryFieldServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryFieldController {
    @Autowired
    CategoryFieldServices categoryFieldServices;

    @GetMapping("/category-fields")
    public Iterable<CategoryField> findAll(){

        return categoryFieldServices.findAll();}

    @PostMapping("/add/category-field")
    public String addCategory(@RequestParam Integer categoryId,@RequestParam String value,@RequestParam String name){
        return categoryFieldServices.addCategoryField(categoryId,value,name);
    }

}
