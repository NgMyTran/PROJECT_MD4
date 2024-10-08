package ra.projectmd4.service.order;

import ra.projectmd4.model.entity.Order;

import java.util.List;

public interface IOrderService {
    void createOrder(Order order);
    Order findById(Long id);
    List<Order> findAll();
    void updateOrder(Order order);
    void deleteOrder(Long id);
}
