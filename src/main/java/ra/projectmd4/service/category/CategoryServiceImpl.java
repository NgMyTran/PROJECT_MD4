package ra.projectmd4.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectmd4.dao.category.ICategoryDao;
import ra.projectmd4.dao.product.IProductDao;
import ra.projectmd4.model.entity.Category;
import ra.projectmd4.model.entity.Product;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
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
        Category categoryDel = categoryDao.findById(id);
        if(categoryDel != null) {
            if(!categoryDel.getProducts().isEmpty() && confirm) {
                for(Product pro : categoryDel.getProducts()) {
                    productDao.delete(pro.getId());
                }
            }
            categoryDao.delete(id);
        }
    }
}
