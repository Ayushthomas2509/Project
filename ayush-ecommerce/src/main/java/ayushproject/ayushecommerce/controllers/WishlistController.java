package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.entities.Wishlist;
import ayushproject.ayushecommerce.services.UserService;
import ayushproject.ayushecommerce.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WishlistController {
    @Autowired
    UserService userService;
    @Autowired
    WishlistService wishlistService;

    @GetMapping(value = "/wishlist/{userId}")
    public Iterable<Wishlist> viewWishlist(@PathVariable Integer userId)
    {
        userService.ensureCustomerOrAdmin();
        return wishlistService.viewWishList(userId);
    }
    @GetMapping(value = "/wishlist")
    public Iterable<Wishlist> allWishlist()
    {
        userService.ensureAdmin();
        return wishlistService.allWishlist();
    }
    @PostMapping(value = "/{userId}/addToWishlist/products/{productId}")
    public String addToWishlist(@PathVariable Integer userId, @PathVariable Integer productId){
        userService.ensureCustomer();
        return wishlistService.addToWishlist(userId,productId);
    }
    @PostMapping(value = "/{userId}/removeFromWishlist/products/{productId}")
    public String removeFromWishlist(@PathVariable Integer userId, @PathVariable Integer productId){
        userService.ensureCustomer();
        return wishlistService.removeFromWishlist(userId,productId);
    }

    @PostMapping(value = "/{userId}/moveToCart/products/{productId}")
    public String moveToCart(@PathVariable Integer userId, @PathVariable Integer productId,@RequestParam Integer quantity){
        userService.ensureCustomer();
        return wishlistService.moveToCart(userId,productId,quantity);
    }
}
