package ra.projectmd4.dao.orderdetai;

import org.springframework.stereotype.Repository;
import ra.projectmd4.model.entity.OrderDetail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Repository
public class OrderDetailDaoImpl implements IOrderDetailDaoImpl{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<OrderDetail> findAll() {
        return em.createQuery("select o from OrderDetail o", OrderDetail.class).getResultList();
    }

    @Override
    public OrderDetail findById(Long id) {
        return em.createQuery("select o from OrderDetail o where o.id = :id", OrderDetail.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public void create(OrderDetail t) {
        em.persist(t);
    }

}
