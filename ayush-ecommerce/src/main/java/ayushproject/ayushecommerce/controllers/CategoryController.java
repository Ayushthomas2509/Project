package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.dto.CategoryDTO;
import ayushproject.ayushecommerce.dto.CategoryFilterDTO;
import ayushproject.ayushecommerce.entities.Category;
import ayushproject.ayushecommerce.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    @Autowired
    CategoryServices categoryServices;

    @GetMapping("/Category")
    public Iterable<Category> findAll(){return categoryServices.findAll();}

    @GetMapping("/Category/{categoryId}")
    public CategoryDTO findCategory(@PathVariable Integer categoryId){
        return categoryServices.findCategory(categoryId);
    }

    @PostMapping("/add-category")
    public String addCategory(@RequestParam String name, @RequestParam(required = false) Integer parentCategoryId) {
        return categoryServices.addCategory(name,parentCategoryId);
    }

    @PostMapping("/edit-category/{categoryId}")
    public String editCategory(@RequestParam String newName,@PathVariable Integer categoryId) {
        return categoryServices.editCategory(newName,categoryId);
    }

    @GetMapping("/seller/Category")
    public Iterable<CategoryDTO> leafCategories(){
        return categoryServices.leafnodeCategories();
    }
    @GetMapping("/buyer/Category/{categoryId}")
    public CategoryFilterDTO CategoryFilter(@PathVariable Integer categoryId){
        return categoryServices.CategoryFilter(categoryId);
    }
}
