package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.dto.ProductDto;
import ayushproject.ayushecommerce.dto.VariationDto;
import ayushproject.ayushecommerce.entities.Product;
//import ayushproject.ayushecommerce.entities.ParentCategory.Electronics;
//import ayushproject.ayushecommerce.entities.ParentCategory.Fashion;
import ayushproject.ayushecommerce.services.ProductService;
import ayushproject.ayushecommerce.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@RestController
public class ProductController  {

    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    private static final Logger logger= LoggerFactory.getLogger(UserController.class);


    @GetMapping("/products")
    public Iterable<Product> allProducts(){
        logger.info("Success");
        userService.ensureUser();
        return productService.allProducts();
    }


    @GetMapping("/products/{category}")
    public List<Product> perCategory(@PathVariable Integer category){
        userService.ensureUser();
        logger.info("product serach by category success");
        return productService.perCategory(category);
    }

    @PostMapping("/add-products")
    public String addProduct(@Valid @RequestBody Product product) {
        userService.ensureSeller();
        return productService.addProduct(product);
    }


    @PostMapping("/edit-products")
    public String editProduct(@Valid @RequestBody Product product){
        userService.ensureSeller();
        return productService.editProduct(product);
    }

    @CacheEvict(value = "product", allEntries=true)
    @DeleteMapping("/remove-products/{productId}")
    public String removeProduct(@PathVariable Integer productId) {
        userService.ensureSeller();
        return productService.removeProduct(productId);
    }

    @PatchMapping("/edit-products/{id}")
    public String editElectronicsProduct(@PathVariable Integer id, @RequestBody Product product){
        userService.ensureSeller();
        return productService.editElectronicsProduct(id,product);
    }


    @GetMapping("/enable-product/{id}")
    public String enableProduct(@PathVariable Integer id){
        userService.ensureSeller();
        return productService.enableProduct(id);
    }


    @GetMapping("/disable-product/{id}")
    public String disableProduct(@PathVariable Integer id){
        userService.ensureSeller();
        return productService.disableProduct(id);}

    @PostMapping("/products/{productId}/add-variation")
    public String addProduct(@PathVariable Integer productId,@RequestBody VariationDto variation) {
        userService.ensureSeller();
        return productService.addProductVariation(productId,variation);
    }

    @GetMapping("/products/{productId}/products-variations")
    public List<VariationDto> findAllProductVariations(@PathVariable Integer productId){
        userService.ensureUser();
        logger.info("Success");
        return productService.findAllProductVariations(productId);
    }

    @GetMapping("/products/{productId}/similar-products")
    public List<Product> findSimilarProduct(@PathVariable Integer productId){
        return productService.findSimilarProduct(productId);
    }

    @GetMapping("/productsDTO/{productId}")
    public ProductDto findProductDTO(@PathVariable Integer productId){
        return productService.findProductDTO(productId);
    }



}
