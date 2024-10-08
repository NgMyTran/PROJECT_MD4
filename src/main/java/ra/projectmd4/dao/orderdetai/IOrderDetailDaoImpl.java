package ra.projectmd4.dao.orderdetai;

import ra.projectmd4.model.entity.OrderDetail;

import java.util.List;

public interface IOrderDetailDaoImpl {
    List<OrderDetail> findAll();
    OrderDetail findById(Long id);
    void create(OrderDetail t);
//    void getSelectedProduct(Long id);
}
