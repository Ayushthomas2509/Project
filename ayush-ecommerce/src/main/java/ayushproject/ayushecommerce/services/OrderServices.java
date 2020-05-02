package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Cart;
import ayushproject.ayushecommerce.entities.Orders;
import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.enums.Status;
import ayushproject.ayushecommerce.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Component
public class OrderServices {
    @Autowired
    UserRepo userRepo;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    CartRepo cartRepo;
    @Autowired
    ProductRepo productRepo;

    public Iterable<Orders> findAll(){
        return orderRepo.findAll();
    }

    public String placeOrder(Integer userid,Integer address) {
        Orders order = new Orders();
        List<Product> productList = new LinkedList<>();
        List<Integer> quantityList = new LinkedList<>();
        order.setProducts(productList);
        order.setOrderdate(new Date());
        order.setQuantity(quantityList);
        order.setUser(customerRepo.findById(userid).get());
        Iterable<Cart> cartIterable = cartRepo.findAll();
        Iterator<Cart> cartIterator = cartIterable.iterator();
        if (!cartIterable.iterator().hasNext()) {
            return "Your Cart is Empty";
        }
        while (cartIterator.hasNext()){
            Cart currentCart=cartIterator.next();
            if (currentCart.getUserid().equals(userid)){
                productList.add(productRepo.findById(currentCart.getProductPOSTid()).get());
                quantityList.add(currentCart.getQuantity());
                cartRepo.deleteById(currentCart.getId());
            }
        }

        order.setAddress(customerRepo.findById(userid).get().getAddress().get(address));
        order.setOrderstatus(Status.Placed);
        orderRepo.save(order);
        return "Order Placed";
    }

    public String cancelOrder(Integer orderId){
        Orders orders = orderRepo.findById(orderId).get();
        if (orders.getOrderstatus()!= Status.Accepted){
            return "Not Possible";
        }
        orders.setOrderstatus(Status.Cancelled);
        orderRepo.save(orders);
        return "Order Cancelled";
    }

    public String returnOrder(Integer orderId){
        Orders orders = orderRepo.findById(orderId).get();
        if (orders.getOrderstatus()!= Status.Delivered){
            return "Order Can Not Be Returned";
        }
        orders.setOrderstatus(Status.Return_Requested);
        orderRepo.save(orders);
        return "Request Generated";

    }

    public String respondOnOrder(Integer orderid, Status status){
        Orders orders=orderRepo.findById(orderid).get();
        if (orders.getOrderstatus()!= Status.Placed){
            return "NOT POSSIBLE ORDER PLACED";
        }
        orders.setOrderstatus(status);
        orderRepo.save(orders);
        return status.toString();
    }


    public String changeStatus(Integer orderId, Status status){
        Orders orders=orderRepo.findById(orderId).get();
        orders.setOrderstatus(status);
        orderRepo.save(orders);
        return status.toString();
    }

    public String respondReturn(Integer orderId, Status status){
        Orders orders=orderRepo.findById(orderId).get();
        if (orders.getOrderstatus()!= Status.Return_Requested){
            return "Not Possible";
        }
        orders.setOrderstatus(status);
        orderRepo.save(orders);
        return status.toString();

    }
}
