package ayushproject.ayushecommerce.criteriaQuery;

import ayushproject.ayushecommerce.dto.CategoryProductDto;
import ayushproject.ayushecommerce.dto.ProductCount;
import ayushproject.ayushecommerce.entities.Category;
import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class CriteriaController {

    @Autowired
    CriteriaQueryAdmin criteriaQueryAdmin;

    @GetMapping("/thomas")
    public List<User> findUser(){
        System.out.println("tata bhai");
        return criteriaQueryAdmin.getAllUserCriteria();
    }

    @RequestMapping(value = "/admin/category", method = RequestMethod.GET)
    public String getAllCategories(Model model){
        List<Category> categoryList = criteriaQueryAdmin.getAllcategories();
        model.addAttribute("categoryList", categoryList);
        return "categoryList";
    }


    @RequestMapping(value = "/admin/product/count", method = RequestMethod.GET)
    public String getProductCount(Model model){
        List<Integer[]> productCount = criteriaQueryAdmin.getProductCount();
        List<ProductCount> productCountList=new ArrayList<>();
        for(Integer[] count: productCount){
            ProductCount productCount1=new ProductCount();
            productCount1.setCount(count[0]);
            System.out.println(count[0]+" yoo bro "+count[1]);
            productCountList.add(productCount1);
        }
        System.out.println(productCount);
        model.addAttribute("productCountList", productCountList);
        return "ProductCount";
    }

    @RequestMapping(value = "/admin/product", method = RequestMethod.GET)
    public String getAllProducts(Model model){
        List<Product> productList = criteriaQueryAdmin.getAllProducts();
        model.addAttribute("productList", productList);
        return "ProductList";
    }


//    @RequestMapping(value = "/AdminPanel", method = RequestMethod.GET)
//    public String count(Model model) {
//        Iterable<Category> category = categoryRepository.findAll();
//        List<CategoryProductDto> categoryProductDtos = new ArrayList<>();
//        List<Integer[]> product = productRepository.countByCategoryId();
//        List<Product> productList= (List<Product>) productRepository.findAll();
//        for (Iterator<Category> iterator = category.iterator(); iterator.hasNext(); ) {
//            Category category1 = iterator.next();
//            CategoryProductDto categoryProductDto=new CategoryProductDto();
//            categoryProductDto.setId(category1.getId());
//            categoryProductDto.setName(category1.getName());
//            categoryProductDto.setCount(0);
//            for(Integer[] product1: product){
//                if(product1[1] == category1.getId()){
//                    System.out.println(product1[0]);
//                    categoryProductDto.setCount(product1[0]);
//                }
//            }
//            categoryProductDtos.add(categoryProductDto);
//        }
//        for(Product product1: productList){
//            CategoryProductDto categoryProductDto=new CategoryProductDto();
//            categoryProductDto.setProductId(product1.getId());
//            categoryProductDto.setProductName(product1.getName());
//            categoryProductDto.setQuantity(product1.getQuantity());
//            categoryProductDtos.add(categoryProductDto);
//        }
//
//        model.addAttribute("categoryProductDtos",categoryProductDtos);
////        model.addAttribute("product",product);
//        categoryRepository.count();
//        return "category";
//    }


}
