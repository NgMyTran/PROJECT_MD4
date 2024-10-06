package ra.projectmd4.service.product;

import org.springframework.web.multipart.MultipartFile;
import ra.projectmd4.model.dto.response.ProductResponse;
import ra.projectmd4.model.dto.response.UserResponse;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.service.IGenericService;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;

public interface IProductService extends IGenericService<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
     void uploadImage(Product product, MultipartFile imageFile, ServletContext servletContext) throws IOException;
     List<ProductResponse> getListProducts(String key, int page, int size);
     long getTotalElements(String key);
}
