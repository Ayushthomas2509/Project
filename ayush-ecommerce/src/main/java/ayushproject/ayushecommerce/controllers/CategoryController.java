package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.dto.CategoryDTO;
import ayushproject.ayushecommerce.dto.CategoryFilterDTO;
import ayushproject.ayushecommerce.entities.Category;
import ayushproject.ayushecommerce.services.CategoryServices;
import ayushproject.ayushecommerce.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    @Autowired
    CategoryServices categoryServices;
    @Autowired
    UserService userService;

    Logger logger= LoggerFactory.getLogger(CategoryController.class);

    @GetMapping("/Category")
    public Iterable<Category> findAll(){
        logger.info("Method Is Accessed");
        userService.ensureUser();
        return categoryServices.findAll();}

    @GetMapping("/Category/{categoryId}")
    public CategoryDTO findCategory(@PathVariable Integer categoryId){
        userService.ensureUser();
        return categoryServices.findCategory(categoryId);
    }

    @PostMapping("/add-category")
    public String addCategory(@RequestParam String name, @RequestParam(required = false) Integer parentCategoryId) {
        logger.info("Category added");
        userService.ensureAdmin();
        return categoryServices.addCategory(name,parentCategoryId);
    }

    @PostMapping("/edit-category/{categoryId}")
    public String editCategory(@RequestParam String newName,@PathVariable Integer categoryId) {
        userService.ensureAdmin();
        return categoryServices.editCategory(newName,categoryId);
    }

    @GetMapping("/seller/Category")
    public Iterable<CategoryDTO> leafCategories(){
        userService.ensureSeller();
        return categoryServices.leafnodeCategories();
    }
    @GetMapping("/customer/Category/{categoryId}")
    public CategoryFilterDTO CategoryFilter(@PathVariable Integer categoryId){
        logger.info("Filtered data shown ");
        logger.warn("No Warning");
        userService.ensureUser();
        return categoryServices.CategoryFilter(categoryId);
    }
}
