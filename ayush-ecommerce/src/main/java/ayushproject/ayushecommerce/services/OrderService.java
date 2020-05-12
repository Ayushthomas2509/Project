package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Cart;
import ayushproject.ayushecommerce.entities.Orders;
import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.enums.Status;
import ayushproject.ayushecommerce.rabbitmq.RabbitMQConfiguration;
import ayushproject.ayushecommerce.repo.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Component
public class OrderService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;

    public Iterable<Orders> findAll(){
        return orderRepository.findAll();
    }

    public String placeOrder(Integer userid,Integer address) {
        Orders order = new Orders();
        List<Product> productList = new LinkedList<>();
        List<Integer> quantityList = new LinkedList<>();
        order.setProducts(productList);
        order.setOrderdate(new Date());
        order.setQuantity(quantityList);
        order.setUser(customerRepository.findById(userid).get());
        Iterable<Cart> cartIterable = cartRepository.findAll();
        Iterator<Cart> cartIterator = cartIterable.iterator();
        if (!cartIterable.iterator().hasNext()) {
            return "Your Cart is Empty";
        }
        while (cartIterator.hasNext()){
            Cart currentCart=cartIterator.next();
            if (currentCart.getUserid().equals(userid)){
                productList.add(productRepository.findById(currentCart.getProductPOSTid()).get());
                quantityList.add(currentCart.getQuantity());
                cartRepository.deleteById(currentCart.getId());
            }
        }

        order.setAddress(customerRepository.findById(userid).get().getAddress().get(address));
        order.setOrderstatus(Status.Placed);
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.topicExchangeName,
                "message_routing_key", "Order is Placed"+order.getOrderstatus());
        System.out.println("Message sent successfully...");
        orderRepository.save(order);
        return "Order Placed";
    }

    public String cancelOrder(Integer orderId){
        Orders orders = orderRepository.findById(orderId).get();
        if (orders.getOrderstatus()!= Status.Accepted){
            return "Not Possible";
        }
        orders.setOrderstatus(Status.Cancelled);
        orderRepository.save(orders);
        return "Order Cancelled";
    }

    public String returnOrder(Integer orderId){
        Orders orders = orderRepository.findById(orderId).get();
        if (orders.getOrderstatus()!= Status.Delivered){
            return "Order Can Not Be Returned";
        }
        orders.setOrderstatus(Status.Return_Requested);
        orderRepository.save(orders);
        return "Request Generated";

    }

    public String respondOnOrder(Integer orderid, Status status){
        Orders orders= orderRepository.findById(orderid).get();
        if (orders.getOrderstatus()!= Status.Placed){
            return "NOT POSSIBLE ORDER PLACED";
        }
        orders.setOrderstatus(status);
        orderRepository.save(orders);
        return status.toString();
    }


    public String changeStatus(Integer orderId, Status status){
        Orders orders= orderRepository.findById(orderId).get();
        orders.setOrderstatus(status);
        orderRepository.save(orders);
        return status.toString();
    }

    public String respondReturn(Integer orderId, Status status){
        Orders orders= orderRepository.findById(orderId).get();
        if (orders.getOrderstatus()!= Status.Return_Requested){
            return "Not Possible";
        }
        orders.setOrderstatus(status);
        orderRepository.save(orders);
        return status.toString();

    }
}
