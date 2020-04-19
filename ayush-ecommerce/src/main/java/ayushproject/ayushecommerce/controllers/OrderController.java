package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.entities.Orders;
import ayushproject.ayushecommerce.enums.STATUS;
import ayushproject.ayushecommerce.services.OrderServices;
import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    OrderServices orderServices;
    @Autowired
    UserService userService;

    @GetMapping("/orders")
    public Iterable<Orders> allOrders(){
        userService.ensureAdmin();
        return orderServices.findAll();
    }

    @PostMapping("/{userId}/order")
    public String placeOrder(@PathVariable Integer userid, @RequestParam Integer address){
        userService.ensureCustomerOrAdmin();
        return orderServices.placeOrder(userid,address);
    }

    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Integer orderId){
        userService.ensureCustomerOrAdmin();
        return orderServices.cancelOrder(orderId);
    }

    @PostMapping("/return/{orderId}")
    public String returnOrder(@PathVariable Integer orderId){
        userService.ensureCustomer();
        return orderServices.returnOrder(orderId);
    }
    @PostMapping("/respondOrder/{orderId}")
    public String respondOnOrder(@PathVariable Integer orderId, @RequestParam STATUS status){
        //userService.ensureSeller();
        return orderServices.respondOnOrder(orderId,status);
    }
    @PostMapping("/respondReturn/{orderId}")
    public String respondReturn(@PathVariable Integer orderId, @RequestParam STATUS status){
        //userService.ensureSeller();
        return orderServices.respondReturn(orderId,status);
    }
    @PostMapping("/changeStatus/{orderId}")
    public String changeStatus(@PathVariable Integer orderId, @RequestParam STATUS status){
        //userService.ensureAdmin();
        return orderServices.changeStatus(orderId,status);
    }
}
