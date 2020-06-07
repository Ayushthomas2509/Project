package ayushproject.ayushecommerce.rabbitmq;

import ayushproject.ayushecommerce.entities.Orders;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareBatchMessageListener;

import java.util.List;

import static ayushproject.ayushecommerce.rabbitmq.RabbitMQConfiguration.queueName;

public class JMSReceiver implements ChannelAwareBatchMessageListener {

     @Override
    public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
         System.out.println("Received <" + message + ">");

         byte[] byteArray = message.getBody();
         Orders orders = new Orders();
         System.out.println("orderStatus = " + orders.getOrderStatus());

     }

    @Override
    public void onMessageBatch(List<Message> messages, Channel channel) {

    }


    @RabbitListener(queues = queueName)
    public void receiveMessage(Orders orders){
        System.out.println("Received< "+orders.getOrderStatus()+" >");
    }
}
