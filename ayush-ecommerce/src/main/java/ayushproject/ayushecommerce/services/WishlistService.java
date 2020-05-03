package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Wishlist;
import ayushproject.ayushecommerce.repo.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class WishlistService {
    @Autowired
    CartService cartService;
    @Autowired
    WishlistRepository wishlistRepository;

    public Iterable<Wishlist> allWishlist(){return wishlistRepository.findAll();}
    public Iterable<Wishlist> viewWishList(Integer userId){return wishlistRepository.findByUserId(userId);}

    public String addToWishlist(Integer userId, Integer productId) {
        Iterator<Wishlist> wishlistIterator= wishlistRepository.findAll().iterator();
        while (wishlistIterator.hasNext()){
            Wishlist currentWishlist=wishlistIterator.next();
            if(currentWishlist.getUserId()==userId&&currentWishlist.getProductId()==productId) {
                return "Already in Wishlist";
            }
        }
        Wishlist wishlist=new Wishlist();
        wishlist.setUserId(userId);
        wishlist.setProductId(productId);
        wishlistRepository.save(wishlist);
        return "Added to Wishlist";

    }

    public String removeFromWishlist(Integer userId, Integer productId) {
        String msg="Item Not in Wishlist";
        Iterator<Wishlist> wishlistIterator= wishlistRepository.findAll().iterator();
        while (wishlistIterator.hasNext()){
            Wishlist currentWishlist=wishlistIterator.next();
            if(currentWishlist.getUserId()==userId&&currentWishlist.getProductId()==productId) {
                wishlistRepository.delete(currentWishlist);
                msg="Item Removed ";
            }
        }
        return msg;
    }


    public String moveToCart(Integer userId, Integer productId, Integer quantity) {
        cartService.addToCart(userId,productId,quantity);
        removeFromWishlist(userId,productId);
        return "Moved to Cart";
    }
}
