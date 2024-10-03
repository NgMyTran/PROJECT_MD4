package ra.projectmd4.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectmd4.dao.product.IProductDao;
import ra.projectmd4.model.entity.Product;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductDao productDao;

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
            productDao.create(product);
        } else {
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
}
