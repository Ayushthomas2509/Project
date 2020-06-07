package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.*;
import ayushproject.ayushecommerce.enums.Status;
import ayushproject.ayushecommerce.rabbitmq.RabbitMQConfiguration;
import ayushproject.ayushecommerce.repo.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
    @Autowired
    CartProductVariationRepository cartProductVariationRepository;
    @Autowired
    OrderProductRepo orderProductRepo;

    public Iterable<Orders> findAll(){
        return orderRepository.findAll();
    }

    public String placeOrder(Integer userid,Integer address) {
        Cart cart = cartRepository.findByuserid(userid);
        List<CartProductVariation> cartProductVariation = cartProductVariationRepository.findByCartId(cart.getId());
        Orders orders = new Orders();
        orders.setAddressId(address);
        orders.setCustomerId(userid);
        Orders order = orderRepository.save(orders);
        System.out.println(order.getOrderId());
        Integer amount = 0;
        if (cartProductVariation.isEmpty()) {
            return "Your Cart is Empty";
        }
        for(CartProductVariation cartProductVariation1: cartProductVariation){
            Optional<Product> product = productRepository.findById(cartProductVariation1.getVariationId());
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProductVariantId(cartProductVariation1.getVariationId());
            orderProduct.setQuantity(cartProductVariation1.getQuantity());
            orderProduct.setPrice(product.get().getPrice());
            orderProduct.setOrderId(order.getOrderId());
            orderProductRepo.save(orderProduct);
            amount=amount+product.get().getPrice();
            cartProductVariationRepository.deleteById(cartProductVariation1.getId());
        }
        order.setAmountPaid(amount);
        order.setOrderStatus(Status.Placed);
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.topicExchangeName,
                "message_routing_key", "Order is Placed"+order.getOrderStatus());
        System.out.println("Message sent successfully...");
        orderRepository.save(order);
        return "Order Placed";
    }

//    public String cancelOrder(Integer orderId){
//        Orders orders = orderRepository.findById(orderId).get();
//        if (orders.getOrderstatus()!= Status.Accepted){
//            return "Not Possible";
//        }
//        orders.setOrderstatus(Status.Cancelled);
//        orderRepository.save(orders);
//        return "Order Cancelled";
//    }
//
//    public String returnOrder(Integer orderId){
//        Orders orders = orderRepository.findById(orderId).get();
//        if (orders.getOrderstatus()!= Status.Delivered){
//            return "Order Can Not Be Returned";
//        }
//        orders.setOrderstatus(Status.Return_Requested);
//        orderRepository.save(orders);
//        return "Request Generated";
//
//    }
//
//    public String respondOnOrder(Integer orderid, Status status){
//        Orders orders= orderRepository.findById(orderid).get();
//        if (orders.getOrderstatus()!= Status.Placed){
//            return "NOT POSSIBLE ORDER PLACED";
//        }
//        orders.setOrderstatus(status);
//        orderRepository.save(orders);
//        return status.toString();
//    }
//
//
//    public String changeStatus(Integer orderId, Status status){
//        Orders orders= orderRepository.findById(orderId).get();
//        orders.setOrderstatus(status);
//        orderRepository.save(orders);
//        return status.toString();
//    }
//
//    public String respondReturn(Integer orderId, Status status){
//        Orders orders= orderRepository.findById(orderId).get();
//        if (orders.getOrderstatus()!= Status.Return_Requested){
//            return "Not Possible";
//        }
//        orders.setOrderstatus(status);
//        orderRepository.save(orders);
//        return status.toString();
//
//    }
}
