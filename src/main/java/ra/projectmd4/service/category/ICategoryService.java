package ra.projectmd4.service.category;

import ra.projectmd4.model.dto.response.ProductResponse;
import ra.projectmd4.model.entity.Category;
import ra.projectmd4.service.IGenericService;

import java.util.List;

public interface ICategoryService extends IGenericService<Category, Long> {
    List<Category> findActiveCategories();
    void deleteCategoryConfirm(Long id, boolean confirm);
    List<Category> getListCategories(String key);

}
