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
import java.util.Arrays;
import java.util.Collections;
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

    @Override
    public List<ShoppingCart> findByUserId(Long userId) {
        TypedQuery<ShoppingCart> query = entityManager.createQuery("SELECT sc FROM ShoppingCart sc WHERE sc.userId = :userId", ShoppingCart.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public ShoppingCart findByUserIdAndProductId(Long userId, Long productId) {
        TypedQuery<ShoppingCart> query = entityManager.createQuery(
                "SELECT sc FROM ShoppingCart sc WHERE sc.userId = :userId AND sc.productId = :productId",
                ShoppingCart.class);

        query.setParameter("userId", userId);
        query.setParameter("productId", productId);

        // Chỉ lấy một kết quả đầu tiên, nếu không tìm thấy sẽ trả về null
        List<ShoppingCart> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<ShoppingCart> findByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) { // Sử dụng isEmpty() để kiểm tra danh sách
            return Collections.emptyList(); // Trả về danh sách rỗng nếu không có sản phẩm nào
        }
        // Câu truy vấn JPQL
        String jpql = "SELECT sc FROM ShoppingCart sc WHERE sc.id IN :ids";
        return entityManager.createQuery(jpql, ShoppingCart.class)
                .setParameter("ids", ids) // Không cần chuyển đổi lại thành danh sách
                .getResultList();
    }

}

