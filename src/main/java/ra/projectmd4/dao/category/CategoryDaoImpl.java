package ra.projectmd4.dao.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.projectmd4.model.entity.Category;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.service.product.IProductService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Repository
public class CategoryDaoImpl implements ICategoryDao {

@PersistenceContext
private EntityManager entityManager;
@Autowired
private IProductService productService;

    @Override
    public List<Category> findAll() {
        TypedQuery<Category> type = entityManager.createQuery("from Category", Category.class);
        return type.getResultList();
    }

    @Override
    public List<Category> findActiveCategories() {
        TypedQuery<Category> type = entityManager.createQuery("FROM Category WHERE status = true", Category.class);
        return type.getResultList();
    }

    @Override
    public Category findById(Long id) {
        TypedQuery<Category> type = entityManager.createQuery("from Category where id = :id", Category.class);
        Category cate = type.setParameter("id", id).getSingleResult();
        return cate;
    }

    @Override
    public void create(Category category) {
        entityManager.persist(category);
    }

    @Override
    public void update(Category category) {
        entityManager.merge(category);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category cateDel = findById(id);
        if (cateDel != null) {
            List<Product> products = cateDel.getProducts();
            for (Product product : products) {
                productService.delete(product.getId());
            }
            cateDel.setStatus(false);
            entityManager.merge(cateDel);
        }
    }


    @Override
    public List<Category> getListCategories(String key) {
        TypedQuery<Category> query= entityManager.createQuery("from Category where name like :keyword or description like :keyword", Category.class);
        query.setParameter("keyword", "%"+key+"%");
        return query.getResultList();
    }
}
