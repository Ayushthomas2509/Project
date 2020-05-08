package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Cart;
import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.enums.InStock;
import ayushproject.ayushecommerce.repo.CartRepository;
import ayushproject.ayushecommerce.repo.ProductRepository;
import ayushproject.ayushecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class CartService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;

    public Iterable<Cart> allcarts(){ return cartRepository.findAll();}
    public Iterable<Cart> viewCart(Integer userid){return cartRepository.findByuserid(userid);}

    public String addToCart (Integer userId, Integer productID, Integer quantity){
        Iterator<Cart> cartIterator = cartRepository.findAll().iterator();

        Product product = productRepository.findById(productID).get();
        if (product.getInStock() == InStock.Yes){
            if (product.getQuantity()-quantity<0){
                return "Not In Stock="+product.getQuantity();
            }
            while (cartIterator.hasNext()){
                Cart currentCart=cartIterator.next();
                if (currentCart.getUserid()==userId&&currentCart.getProductPOSTid()==productID){
                    currentCart.setQuantity(currentCart.getQuantity()+quantity);
                    product.setQuantity(product.getQuantity()-quantity);
                    if (product.getQuantity()<= 0){
                        product.setInStock(InStock.No);
                    }
                    productRepository.save(product);
                    cartRepository.save(currentCart);

                    return "Quantity Increased to"+currentCart.getQuantity();

                }
            }
            Cart cart=new Cart();
            cart.setUserid(userId);
            cart.setProductid(productID);
            cart.setQuantity(quantity);
            product.setQuantity(product.getQuantity()-quantity);
            if (product.getQuantity()<=0){
                 product.setInStock(InStock.No);
            }
            productRepository.save(product);
            cartRepository.save(cart);

            return "Moved To Cart";

        }
        return "Not Available In Stock";
    }

    public String removeFromCart(Integer userid,Integer productId){
        String msg="Item Not in cart";
        Iterator<Cart> cartIterator = cartRepository.findAll().iterator();
        Product product = productRepository.findById(productId).get();
        msg = findCart(userid, productId, msg ,cartIterator, product);
        return msg;
    }

    private String findCart(Integer userid, Integer productId, String msg, Iterator<Cart> cartIterator, Product product) {
        while (cartIterator.hasNext()){
            Cart currentCart=cartIterator.next();
            if(currentCart.getUserid()==userid&&currentCart.getProductPOSTid()==productId) {
                msg = removeProduct(product, currentCart);
    }
    }
    return msg;

    }

    private String removeProduct(Product product,Cart currentCart){
        String msg;
        currentCart.setQuantity(currentCart.getQuantity() - 1);
        product.setQuantity(product.getQuantity() + 1);
        if (product.getQuantity()>0){
            product.setInStock(InStock.Yes);
        }
        if (currentCart.getQuantity()<0){
            cartRepository.delete(currentCart);
            productRepository.save(product);
            msg="Item removed";
        }
        else {
            cartRepository.save(currentCart);
        }
        productRepository.save(product);
        msg="Quantity decreased to"+currentCart.getQuantity();
        return  msg;
    }


}