package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.entities.Address;
import ayushproject.ayushecommerce.entities.Customer;
import ayushproject.ayushecommerce.services.CustomerService;
import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

@GetMapping("/customer/profile")
    public Customer findByName(){
    userService.ensureCustomer();
    return customerService.myProfile();
}
    @GetMapping("customers/viewAddress")
    public List<Address> viewAddress()  {
     userService.ensureCustomer();
        return customerService.viewAdress();
    }

    @PutMapping("/customers/addAddress")
    public List<Address> addAddress(@Valid @RequestBody Address address) {
        userService.ensureCustomer();
        return customerService.addAddress(address);
    }

    @PutMapping("/customers/editAddress/{addressId}")
    public List<Address> editAddress(@Valid @PathVariable Integer addressId, @RequestBody Address address) {
    userService.ensureCustomer();
        return customerService.editAddress(address,addressId);
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
