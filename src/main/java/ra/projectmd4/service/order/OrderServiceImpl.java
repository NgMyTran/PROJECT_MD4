package ra.projectmd4.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectmd4.dao.order.IOrderDao;
import ra.projectmd4.model.entity.Order;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao orderDao;

    @Override
    @Transactional
    public void createOrder(Order order) {
        orderDao.create(order);
    }

    @Override
    public Order findById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    @Transactional
    public void updateOrder(Order order) {
        orderDao.update(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        orderDao.delete(id);
    }
}

