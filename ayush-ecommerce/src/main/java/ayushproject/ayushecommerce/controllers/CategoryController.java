package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.dto.CategoryDto;
import ayushproject.ayushecommerce.dto.CategoryFilterDto;
import ayushproject.ayushecommerce.entities.Category;
import ayushproject.ayushecommerce.services.CategoryService;
import ayushproject.ayushecommerce.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;

    Logger logger= LoggerFactory.getLogger(CategoryController.class);

    //cache done
    @RequestMapping(value = "/Category",method = RequestMethod.GET)
    public Iterable<Category> findAll(){
        logger.info("Method Is Accessed");
        userService.ensureUser();
        return categoryService.findAll();}


    @GetMapping("/Category/{categoryId}")
    public CategoryDto findCategory(@PathVariable Integer categoryId){
        userService.ensureUser();
        return categoryService.findCategory(categoryId);
    }

    @PostMapping("/add-category")
    public String addCategory(@RequestBody Category category) {
        logger.info("Category added");
        userService.ensureAdmin();
        return categoryService.addCategory(category);
    }

    @PostMapping("/edit-category/{categoryId}")
    public String editCategory(@RequestParam String newName,@PathVariable Integer categoryId) {
        userService.ensureAdmin();
        logger.info("Method Accessed");
        return categoryService.editCategory(newName,categoryId);
    }


    @GetMapping("/seller/Category")
    public Iterable<CategoryDto> leafCategories(){
        userService.ensureUser();
        logger.info("Leaf Nodes Displayed");
        return categoryService.leafnodeCategories();
    }

    @GetMapping("/customer/Category/{categoryId}")
    public CategoryFilterDto CategoryFilter(@PathVariable Integer categoryId){
        logger.info("Filtered data shown ");
        logger.warn("No Warning");
        userService.ensureUser();
        return categoryService.CategoryFilter(categoryId);
    }
}
