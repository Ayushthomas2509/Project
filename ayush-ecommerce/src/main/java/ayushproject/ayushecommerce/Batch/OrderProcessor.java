package ayushproject.ayushecommerce.Batch;

import ayushproject.ayushecommerce.entities.Orders;
import org.springframework.batch.item.ItemProcessor;

public class OrderProcessor implements ItemProcessor<Orders, Orders> {

    @Override
    public Orders process(Orders orders) throws Exception {
        return orders;
    }
}
