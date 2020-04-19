package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.entities.ParentCategory.Electronics;
import ayushproject.ayushecommerce.entities.ParentCategory.Fashion;
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
    public List<Product> perCategory(@PathVariable String category){
        return productService.perCategory(category);
    }

    @PostMapping("/add-products/fashion")
    public String addProduct(@RequestBody Fashion fashion) {
       // userService.ensureSeller();
        return productService.addProduct(fashion);
    }

    @PostMapping("/add-products/electronics")
    public String addProduct(@RequestBody Electronics electronics){
        userService.ensureSeller();
        return productService.addProduct(electronics);
    }

    @PostMapping("/edit-products/fashion")
    public String editProduct(@RequestBody Fashion fashion){
        userService.ensureSeller();
        return productService.editProduct(fashion);
    }

    @PostMapping("/edit-products/electronics")
    public String editProduct(@RequestBody Electronics electronics){
        userService.ensureSeller();
        return productService.editProduct(electronics);
    }

    @DeleteMapping("/remove-products/{productId}")
    public String removeProduct(@PathVariable Integer productId) {
        userService.ensureSeller();
        return productService.removeProduct(productId);
    }

    @PatchMapping("/edit-products/electronics/{id}")
    public String editElectronicsProduct(@PathVariable Integer id, @RequestBody Map<String, Object> fields){
        userService.ensureSeller();
        return productService.editElectronicsProduct(id,fields);
    }

    @PatchMapping("/edit-products/fashion/{id}")
    public String editFashionProduct(@PathVariable Integer id, @RequestBody Map<String, Object> fields){
        userService.ensureSeller();
        return productService.editFashionProduct(id,fields);
    }

    @GetMapping("/enable-product/{id}")
    public String enableProduct(@PathVariable Integer id){
        return productService.enableProduct(id);
    }


    @GetMapping("/disable-product/{id}")
    public String disableProduct(@PathVariable Integer id){
        return productService.disableProduct(id);}

    @PutMapping("/products/{productId}/add-variation/{variationProductId}")
    public String addProduct(@PathVariable Integer productId,@PathVariable Integer variationProductId) {
        userService.ensureSeller();
        return productService.addProductVariation(productId,variationProductId);
    }

    @GetMapping("/products/{productId}/products-variations")
    public List<Product> findAllProductVariations(@PathVariable Integer productId){
        return productService.findAllProductVariations(productId);
    }



}
