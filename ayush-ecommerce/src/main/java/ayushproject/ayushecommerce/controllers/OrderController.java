package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.entities.Orders;
import ayushproject.ayushecommerce.enums.Status;
import ayushproject.ayushecommerce.services.OrderService;
import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

    @GetMapping("/orders")
    public Iterable<Orders> allOrders(){
        userService.ensureAdmin();
        return orderService.findAll();
    }

    @RequestMapping(value = "/{userid}/order", method = RequestMethod.POST)
    public String placeOrder(@PathVariable Integer userid, @RequestParam Integer address){
        userService.ensureCustomerOrAdmin();
        return orderService.placeOrder(userid,address);
    }

    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Integer orderId){
        userService.ensureCustomerOrAdmin();
        return orderService.cancelOrder(orderId);
    }

    @PostMapping("/return/{orderId}")
    public String returnOrder(@PathVariable Integer orderId){
        userService.ensureCustomer();
        return orderService.returnOrder(orderId);
    }
    @PostMapping("/respondOrder/{orderId}")
    public String respondOnOrder(@PathVariable Integer orderId, @RequestParam Status status){
        userService.ensureSeller();
        return orderService.respondOnOrder(orderId,status);
    }
    @PostMapping("/respondReturn/{orderId}")
    public String respondReturn(@PathVariable Integer orderId, @RequestParam Status status){
        userService.ensureSeller();
        return orderService.respondReturn(orderId,status);
    }
    @PostMapping("/changeStatus/{orderId}")
    public String changeStatus(@PathVariable Integer orderId, @RequestParam Status status){
        userService.ensureAdmin();
        return orderService.changeStatus(orderId,status);
    }
}
