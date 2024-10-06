package ra.projectmd4.dao.product;

import ra.projectmd4.dao.IGenericDao;
import ra.projectmd4.model.dto.response.UserResponse;
import ra.projectmd4.model.entity.Product;

import java.util.List;

public interface IProductDao extends IGenericDao<Product, Long> {
    List<Product> getProductsByCategoryId(Long categoryId);

    long getTotalElements(String key);
    List<Product> getListProducts(String key, int page, int size);
}