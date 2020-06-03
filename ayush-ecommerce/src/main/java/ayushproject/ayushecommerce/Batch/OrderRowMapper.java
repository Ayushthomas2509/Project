package ayushproject.ayushecommerce.Batch;

import ayushproject.ayushecommerce.entities.Orders;
import ayushproject.ayushecommerce.enums.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Orders> {
    @Override
    public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {
        Orders orders = new Orders(); //,  , , , ,
        orders.setOrderId(rs.getInt("order_id"));
        orders.setAmountPaid(rs.getInt("amount_paid"));
        orders.setAddressId(rs.getInt(" address_id"));
        orders.setCustomerId(rs.getInt("customer_id"));
        orders.setDateCreate(rs.getDate("date_create"));
        orders.setDateUpdated(rs.getDate("date_updated"));
        orders.setOrderStatus((Status) rs.getObject("order_status"));
        return orders;
    }
}
