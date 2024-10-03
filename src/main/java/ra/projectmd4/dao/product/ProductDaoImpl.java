package ra.projectmd4.dao.product;

import org.springframework.stereotype.Repository;
import ra.projectmd4.model.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Repository
public class ProductDaoImpl implements IProductDao {
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
}
