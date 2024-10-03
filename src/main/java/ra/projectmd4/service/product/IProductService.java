package ra.projectmd4.service.product;

import ra.projectmd4.model.entity.Product;
import ra.projectmd4.service.IGenericService;

import java.util.List;

public interface IProductService extends IGenericService<Product, Long> {
     public List<Product> findByCategoryId(Long categoryId);

}
