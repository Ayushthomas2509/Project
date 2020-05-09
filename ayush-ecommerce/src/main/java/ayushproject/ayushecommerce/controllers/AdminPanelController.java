package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.dto.CategoryProductDto;
import ayushproject.ayushecommerce.entities.Category;
import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.repo.CategoryRepository;
import ayushproject.ayushecommerce.repo.ProductRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
public class AdminPanelController {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

//    @RequestMapping(value = "/index")
//    public String index() {
//        return "index";
//    }
////    }

    @RequestMapping(value = "/AdminPanel", method = RequestMethod.GET)
    public String count(Model model) {
        Iterable<Category> category = categoryRepository.findAll();
        List<CategoryProductDto> categoryProductDtos = new ArrayList<>();
        List<Integer[]> product = productRepository.countByCategoryId();
        List<Product> productList= (List<Product>) productRepository.findAll();
        for (Iterator<Category> iterator = category.iterator(); iterator.hasNext(); ) {
            Category category1 = iterator.next();
            CategoryProductDto categoryProductDto=new CategoryProductDto();
            categoryProductDto.setId(category1.getId());
            categoryProductDto.setName(category1.getName());
            categoryProductDto.setCount(0);
            for(Integer[] product1: product){
                if(product1[1] == category1.getId()){
                    System.out.println(product1[0]);
                    categoryProductDto.setCount(product1[0]);
                }
            }
            categoryProductDtos.add(categoryProductDto);
        }
        for(Product product1: productList){
            CategoryProductDto categoryProductDto=new CategoryProductDto();
            categoryProductDto.setProductId(product1.getId());
            categoryProductDto.setProductName(product1.getName());
            categoryProductDto.setQuantity(product1.getQuantity());
            categoryProductDtos.add(categoryProductDto);
        }

        model.addAttribute("categoryProductDtos",categoryProductDtos);
//        model.addAttribute("product",product);
        categoryRepository.count();
        return "category";
    }
}