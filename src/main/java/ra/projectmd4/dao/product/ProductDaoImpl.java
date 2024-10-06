package ra.projectmd4.dao.product;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ra.projectmd4.model.dto.response.UserResponse;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.model.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Repository
public class ProductDaoImpl implements IProductDao {
//    private static final Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("from Product ", Product.class).getResultList();
    }

    @Override
    public Product findById(Long id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    @Transactional
    public void create(Product product) {
        entityManager.persist(product);
    }

    @Override
    @Transactional
    public void update(Product product) {
        entityManager.merge(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entityManager.remove(findById(id));
    }
    @Override
    public List<Product> getProductsByCategoryId(Long categoryId) {
        String query = "SELECT p FROM Product p WHERE p.category.id = :categoryId";
        TypedQuery<Product> typedQuery = entityManager.createQuery(query, Product.class);
        typedQuery.setParameter("categoryId", categoryId);
        return typedQuery.getResultList();
    }

    @Override
    public List<Product> getListProducts(String keyword, int page, int pageSize) {
        TypedQuery<Product> query= entityManager.createQuery("from Product where productName like :keyword or sku like :keyword or category.name like :keyword", Product.class);
        query.setParameter("keyword", "%"+keyword+"%");
        query.setFirstResult(page * pageSize).setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public long getTotalElements(String key) {
        TypedQuery<Long> count = entityManager.createQuery("select count(U) from User U where isDeleted = false and (fullName like :keyword or username like :keyword or email like :keyword)", Long.class);
        count.setParameter("keyword", "%"+key+"%");
        return count.getSingleResult();
    }
}
