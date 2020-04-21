package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.entities.Cart;
import ayushproject.ayushecommerce.services.CartService;
import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;

    @GetMapping(value = "/cart/{userid}")
    public Iterable<Cart> viewCart(@PathVariable Integer userid) {
        return cartService.viewCart(userid);
    }

    @GetMapping(value = "/carts")
    public Iterable<Cart> allcarts() {
        return cartService.allcarts();
    }

    @PostMapping(value = "/{userId}/addToCart/products/{productId}")
    public String addToCart(@PathVariable Integer userId, @PathVariable Integer productId, @RequestParam Integer quantity) {
        userService.ensureCustomerOrAdmin();
        return cartService.addToCart(userId, productId, quantity);
    }

    @PostMapping(value = "/{userId}/removeFromCart/products/{productId}")
    public String removeFromCart(@PathVariable Integer userid, @PathVariable Integer productId, @RequestParam Integer quantity) {
        //userService.ensureBuyer();
        return cartService.removeFromCart(userid, productId);
    }
}
