package ra.projectmd4.service.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectmd4.dao.category.ICategoryDao;
import ra.projectmd4.dao.product.IProductDao;
import ra.projectmd4.model.entity.Category;
import ra.projectmd4.model.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @PersistenceContext
    private EntityManager entityManager;
   @Autowired
   private ICategoryDao categoryDao;
   @Autowired
   private IProductDao productDao;

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(Long id) {
     return categoryDao.findById(id);
    }

    @Override
    @Transactional
    public void save(Category category) {
        if (category.getId() == null) {
            categoryDao.create(category);
        } else {
            categoryDao.update(category);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        categoryDao.delete(id);
    }

    @Override
    public List<Category> findActiveCategories() {
        return categoryDao.findActiveCategories();
    }

    @Override
    @Transactional
    public void deleteCategoryConfirm(Long id, boolean confirm) {
        Category cateDel = categoryDao.findById(id);
        if (cateDel != null) {
            //có sp thuộc category
            if (!cateDel.getProducts().isEmpty() && confirm) {
                for (Product pro : cateDel.getProducts()) {
                    productDao.delete(pro.getId()); // Cập nhật sản phẩm
                }
            }
            categoryDao.delete(id);
        }
    }

    @Override
    public List<Category> getListCategories(String key) {
        return categoryDao.getListCategories(key);
    }
}
