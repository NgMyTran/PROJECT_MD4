package ra.projectmd4.service.orderdetail;

import ra.projectmd4.model.entity.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    List<OrderDetail> findAll();
    OrderDetail findById(Long id);
    void save(OrderDetail od);
    void delete(Long id);
}
