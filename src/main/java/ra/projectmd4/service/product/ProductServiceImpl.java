package ra.projectmd4.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import ra.projectmd4.dao.product.IProductDao;
import ra.projectmd4.model.dto.response.ProductResponse;
import ra.projectmd4.model.entity.Product;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductDao productDao;

    @Override
    public List<ProductResponse> getListProducts(String key, int page, int size) {
        return productDao.getListProducts(key,page,size).stream().map(ProductResponse::new).collect(Collectors.toList());
    }

//    @Override
//    public List<ProductResponse> getListProducts(String key) {
//        return productDao.getListProducts(key).stream().map(ProductResponse::new).collect(Collectors.toList());
//    }

    @Override
    public long getTotalElements(String key) {
        return productDao.getTotalElements(key);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productDao.findById(id);
    }

    @Override
    @Transactional
    public void save(Product product) {
        if (product.getId() == null) {
//            product.setCreatedAt(Date.valueOf(LocalDate.now()));
            product.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            productDao.create(product);
        } else {
            product.setCreatedAt(product.getCreatedAt());
            product.update();
            productDao.update(product);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productDao.delete(id);
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return productDao.getProductsByCategoryId(categoryId);
    }

    @Override
    public List<Product> findActiveProductsByCategoryId(Long categoryId) {
        return productDao.findActiveProductsByCategoryId(categoryId);
    }


    @Override
    public List<Product> findActiveProducts() {
        return productDao.findActiveProducts();
    }

    @Override
    @Transactional
    public void uploadImage(Product product, MultipartFile imageFile, ServletContext servletContext) throws IOException {
        String path = servletContext.getRealPath("/uploads");
        File uploadDir = new File(path);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Tạo thư mục uploads nếu nó không tồn tại
        }

        if (!imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            File destinationFile = new File(uploadDir, fileName);//
            File projectFile = new File("C:\\Users\\TRAN\\INTELLIJ\\projectMd4\\src\\main\\webapp\\uploads", fileName);

            // Sao chép tệp vào thư mục uploads
            FileCopyUtils.copy(imageFile.getBytes(), destinationFile);//sao chép tệp sang một vị trí mới hoặc thay đổi định dạng tệp trong quá trình sao chép
            imageFile.transferTo(projectFile);//chuyển đổi một đối tượng tệp (vd biểu mẫu upload) đến một vị trí tệp cụ thể trên máy chủ.

            product.setImage("/uploads/" + fileName);// Đặt đường dẫn hình ảnh trong sản phẩm
        } else {
            throw new IOException("Không có tệp nào được tải lên");
        }
    }
}
