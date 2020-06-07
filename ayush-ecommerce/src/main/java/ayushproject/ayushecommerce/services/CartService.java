package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.*;
import ayushproject.ayushecommerce.enums.InStock;
import ayushproject.ayushecommerce.repo.CartProductVariationRepository;
import ayushproject.ayushecommerce.repo.CartRepository;
import ayushproject.ayushecommerce.repo.ProductRepository;
import ayushproject.ayushecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Optional;

@Component
public class CartService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartProductVariationRepository cartProductVariationRepository;

    public User getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetail = (User) authentication.getPrincipal();
        String username = userDetail.getUsername();
        User user = userRepository.findByname(username);
        return user;
    }

    public Iterable<Cart> allcarts(){ return cartRepository.findAll();}
    public Iterable<CartProductVariation> viewCart(){
        Cart cart=cartRepository.findByuserid(getLoggedInCustomer().getId());
        return cartProductVariationRepository.findByCartId(cart.getId());
    }

    public String addToCart (Integer productID, Integer quantity){
        Cart cart = cartRepository.findByuserid(getLoggedInCustomer().getId());
        Product product = productRepository.findById(productID).get();
        if (product.getInStock() == InStock.Yes){
            if (product.getQuantity()-quantity<0){
                return "Not In Stock="+product.getQuantity();
            }
            Optional<CartProductVariation> cartProductVariation = cartProductVariationRepository.findByCartIdAndProductId(cart.getId(),productID);
            
                if (cartProductVariation.isPresent()){
                    cartProductVariation.get().setQuantity(cartProductVariation.get().getQuantity()+quantity);
                    product.setQuantity(product.getQuantity()-quantity);
                    if (product.getQuantity()<= 0){
                        product.setInStock(InStock.No);
                    }
                    productRepository.save(product);
                    cartProductVariationRepository.save(cartProductVariation.get());

                    return "Quantity Increased to"+cartProductVariation.get().getQuantity();

                }
            }
            CartProductVariation cartProductVariation1=new CartProductVariation();
            cartProductVariation1.setCartId(cart.getId());
            cartProductVariation1.setVariationId(productID);
            cartProductVariation1.setQuantity(quantity);
            product.setQuantity(product.getQuantity()-quantity);
            if (product.getQuantity()<=0){
                 product.setInStock(InStock.No);
            }
            productRepository.save(product);
            cartProductVariationRepository.save(cartProductVariation1);
//            cartRepository.save(cart);

            return "Moved To Cart";
    }

    public String removeFromCart(Integer userid,Integer productId){
        Cart cart = cartRepository.findByuserid(userid);
        Optional<CartProductVariation> cartIterator = cartProductVariationRepository.findByCartIdAndProductId(cart.getId(),productId);
        if(cartIterator.get().getId()==null){
            return "Item Not in cart";
        }
        return "Item removed";
    }

}