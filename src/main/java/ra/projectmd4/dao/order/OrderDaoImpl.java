package ra.projectmd4.dao.order;

import org.springframework.stereotype.Repository;
import ra.projectmd4.model.entity.Order;
import ra.projectmd4.model.entity.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public class OrderDaoImpl implements IOrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(Order order) {
        entityManager.persist(order);
    }

    @Override
    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public List<Order> findAll() {
        TypedQuery<Order> query = entityManager.createQuery("FROM Order", Order.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Order order) {
        entityManager.merge(order);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Order order = findById(id);
        if (order != null) {
            entityManager.remove(order);
        }
    }
}
