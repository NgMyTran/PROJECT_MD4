package ra.projectmd4.dao.category;

import org.springframework.stereotype.Repository;
import ra.projectmd4.model.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class CategoryDaoImpl implements ICategoryDao {
@PersistenceContext
private EntityManager entityManager;

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
    public void delete(Long id) {
        Category cateDel = findById(id);
        if (cateDel != null) {
            cateDel.setStatus(false);
            entityManager.merge(cateDel);
        }
    }
}
