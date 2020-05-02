package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.dto.ProductDTO;
import ayushproject.ayushecommerce.dto.VariationDto;
import ayushproject.ayushecommerce.entities.Product;
//import ayushproject.ayushecommerce.entities.ParentCategory.Electronics;
//import ayushproject.ayushecommerce.entities.ParentCategory.Fashion;
import ayushproject.ayushecommerce.services.ProductService;
import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    @GetMapping("/products")
    public Iterable<Product> allProducts(){
        return productService.allProducts();
    }

    @GetMapping("/products/{category}")
    public List<Product> perCategory(@PathVariable Integer category){
        return productService.perCategory(category);
    }

    @PostMapping("/add-products")
    public String addProduct(@RequestBody Product product) {
        userService.ensureSeller();
        return productService.addProduct(product);
    }


    @PostMapping("/edit-products")
    public String editProduct(@RequestBody Product product){
        userService.ensureSeller();
        return productService.editProduct(product);
    }


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
        return productService.findAllProductVariations(productId);
    }

    @GetMapping("/products/{productId}/similar-products")
    public List<Product> findSimilarProduct(@PathVariable Integer productId){
        return productService.findSimilarProduct(productId);
    }

    @GetMapping("/productsDTO/{productId}")
    public ProductDTO findProductDTO(@PathVariable Integer productId){
        return productService.findProductDTO(productId);
    }



}
