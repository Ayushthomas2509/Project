package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.services.CategoryFieldValuesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryFieldValuesController {

    @Autowired
    CategoryFieldValuesServices categoryFieldValuesServices;

    @GetMapping("/CategoryFieldValues")
    public Iterable allValues(){
        return categoryFieldValuesServices.findAll();
    }

    @PostMapping("/intial-categoriesValues")
    public String beforeCategoryFieldValues(@RequestParam Integer categoryId,@RequestParam Integer categoryFieldId,@RequestParam String value){
        return categoryFieldValuesServices.beforeCategoryFieldsValues(categoryId,categoryFieldId,value);
    }

    @PostMapping("/add-categoryfieldvalues")
    public String addCategoryFieldValues(@RequestParam Integer categoryId,@RequestParam Integer categoryFieldId,@RequestParam String value){
        return categoryFieldValuesServices.addCategoryFieldsValues(categoryId,categoryFieldId,value);
    }
}
