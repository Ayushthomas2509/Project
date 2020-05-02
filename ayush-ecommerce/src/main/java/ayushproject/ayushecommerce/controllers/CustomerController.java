package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.entities.Address;
import ayushproject.ayushecommerce.entities.Customer;
import ayushproject.ayushecommerce.services.CustomerService;
import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
@Autowired
UserService userService;
@Autowired
CustomerService customerService;

@GetMapping("/customers")
    public Iterable<Customer> viewAll(){
    userService.ensureAdmin();
    return customerService.allCustomers();
}

@GetMapping("/customers/{name}")
    public Customer findByName(String name){
    userService.ensureCustomer();
    return customerService.myProfile(name);
}
    @GetMapping("customers/{name}/viewAddress")
    public List<Address> viewAddress(@PathVariable String name)  {
     userService.ensureCustomer();
        return customerService.viewAdress(name);
    }

    @PutMapping("/customers/{name}/addAddress")
    public List<Address> addAddress(@PathVariable String name,@RequestBody Address address) {
        userService.ensureCustomer();
        return customerService.addAddress(address,name);
    }

    @PutMapping("/customers/{name}/editAddress/{addressId}")
    public List<Address> editAddress(@PathVariable String name, @PathVariable Integer addressId, @RequestBody Address address) {
    userService.ensureCustomer();
        return customerService.editAddress(address,addressId,name);
    }

    @DeleteMapping("/customers/{name}/deleteAddress/{addressId}")
    public List<Address> deleteAddress(@PathVariable String name,@PathVariable Integer addressId) {
    userService.ensureCustomer();
    return customerService.deleteAddress(addressId,name);
    }

    @PutMapping("/customers/{name}/updatePassword")
    public String updatePassword(@PathVariable String name,@RequestParam String newPassword,@RequestParam String oldPassword) {
    userService.ensureCustomer();
    return customerService.updatePassword(name,newPassword,oldPassword);
    }

}
