package ra.projectmd4.dao.category;

import ra.projectmd4.dao.IGenericDao;
import ra.projectmd4.model.entity.Category;

import java.util.List;

public interface ICategoryDao extends IGenericDao<Category, Long> {
    List<Category> findActiveCategories();
}
