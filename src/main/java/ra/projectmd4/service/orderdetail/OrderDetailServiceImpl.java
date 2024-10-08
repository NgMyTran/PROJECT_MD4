//package ra.projectmd4.service.orderdetail;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import ra.projectmd4.dao.orderdetail.IOrderDetailDao;
//import ra.projectmd4.model.entity.OrderDetail;
//import java.util.List;
//
//@Service
//public class OrderDetailServiceImpl implements IOrderDetailService{
//
//    @Autowired
//    private IOrderDetailDao orderDetailDao;
//
//    @Override
//    public List<OrderDetail> findAll() {
//        return orderDetailDao.findAll();
//    }
//
//    @Override
//    public OrderDetail findById(Long id) {
//        return orderDetailDao.findById(id);
//    }
//
//    @Override
//    public void save(OrderDetail od) {
//        if(od.getOrderDetailId()==null){
//            orderDetailDao.create(od);
//        }else{
//            orderDetailDao.update(od);
//        }
//    }
//
//    @Override
//    public void delete(Long id) {
//        orderDetailDao.delete(id);
//    }
//}
