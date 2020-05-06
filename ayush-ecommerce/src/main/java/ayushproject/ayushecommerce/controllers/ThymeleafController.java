package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.entities.Category;
import ayushproject.ayushecommerce.repo.CategoryRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ThymeleafController {

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }
//    }

    @RequestMapping(value = "/category/all", method = RequestMethod.GET)
    public String count() {
        Iterable<Category> category = categoryRepository.findAll();
        categoryRepository.count();
        return "category";
    }
}