package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.entities.Address;
import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.entities.Seller;
import ayushproject.ayushecommerce.services.SellerService;
import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerController {
    @Autowired
    UserService userService;
    @Autowired
    SellerService sellerService;

    @GetMapping("sellers")
    public Iterable<Seller> viewAll(){
        userService.ensureSeller();
        return sellerService.viewAll();
    }

    @GetMapping("/seller/{name}")
    public Seller findByName(@PathVariable String name){
        userService.ensureSeller();
         return sellerService.findByName(name);

    }

    @PutMapping("/seller/{name}/editAddress")
    public String editAddress(@PathVariable String name, @RequestBody Address address){
        userService.ensureSeller();
        return sellerService.editAddress(address,name);
    }

    @PutMapping("/seller/{name}/updatePassword")
    public String updatePassword(@PathVariable String name,@RequestParam String newPassword,@RequestParam String oldPassword){
        userService.ensureSeller();
        return sellerService.updatePassword(newPassword,oldPassword,name);
    }

    @GetMapping("/seller/{name}/myProducts")
    public List<Product> myProducts(@PathVariable String name){
        userService.ensureSeller();
        return sellerService.myProducts(name);
    }

}
