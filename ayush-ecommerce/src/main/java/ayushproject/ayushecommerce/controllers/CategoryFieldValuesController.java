package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.services.CategoryFieldValuesService;
import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryFieldValuesController {

    @Autowired
    CategoryFieldValuesService categoryFieldValuesService;
    @Autowired
    UserService userService;

    @GetMapping("/CategoryFieldValues")
    public Iterable allValues(){
        userService.ensureUser();
        return categoryFieldValuesService.findAll();
    }

    @PostMapping("/intial-categoriesValues")
    public String beforeCategoryFieldValues(@RequestParam Integer categoryId,@RequestParam Integer categoryFieldId,@RequestParam String value){
        userService.ensureAdmin();
        return categoryFieldValuesService.beforeCategoryFieldsValues(categoryId,categoryFieldId,value);
    }

    @PostMapping("/add-categoryfieldvalues")
    public String addCategoryFieldValues(@RequestParam Integer categoryId,@RequestParam Integer categoryFieldId,@RequestParam String value){

        userService.ensureAdmin();
        return categoryFieldValuesService.addCategoryFieldsValues(categoryId,categoryFieldId,value);
    }
}
