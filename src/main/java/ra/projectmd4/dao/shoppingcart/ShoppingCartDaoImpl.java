package ra.projectmd4.dao.shoppingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ra.projectmd4.dao.product.ProductDaoImpl;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.model.entity.ShoppingCart;
import ra.projectmd4.service.product.IProductService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;


@Repository
@Transactional
public class ShoppingCartDaoImpl implements IShoppingCartDao {
    @Autowired
    private IProductService iProductService;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ShoppingCart> findAll() {
        TypedQuery<ShoppingCart> query = entityManager.createQuery("SELECT sc FROM ShoppingCart sc", ShoppingCart.class);
        return query.getResultList();
    }

    @Override
    public ShoppingCart findById(Integer id) {
        return entityManager.find(ShoppingCart.class, id);
    }

    @Override
    public void create(ShoppingCart shoppingCart) {
            entityManager.persist(shoppingCart);
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        entityManager.merge(shoppingCart);
    }

    @Override
    public void delete(Integer id) {
        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, id);
        if (shoppingCart != null) {
            entityManager.remove(shoppingCart);
        }
    }

    @Override
    public BigDecimal calculateTotalAmount(List<ShoppingCart> shoppingCarts) {
        BigDecimal totalAmount = BigDecimal.ZERO; // Khởi tạo tổng bằng 0
        for (ShoppingCart cart : shoppingCarts) {
            Product product = iProductService.findById(cart.getProductId());
            if (product != null) {
                BigDecimal price = product.getPrice(); // Giá sản phẩm là BigDecimal
                totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(cart.getOrderQuantity()))); // Cộng dồn tổng
            }
        }
        return totalAmount;
    }


}
