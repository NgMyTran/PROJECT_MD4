package ra.projectmd4.service.shoppingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.projectmd4.dao.product.IProductDao;
import ra.projectmd4.dao.shoppingcart.IShoppingCartDao;
import ra.projectmd4.model.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ShoppingCartServiceImpl implements IShoppingCartService {
    @Autowired
    private IShoppingCartDao shoppingCartDao;

    @Override
    public List<ShoppingCart> findAll() {
        return shoppingCartDao.findAll();
    }

    @Override
    public ShoppingCart findById(Integer id) {
        return shoppingCartDao.findById(id);
    }

    @Override
    public void save(ShoppingCart shoppingCart) {
        if (shoppingCart.getShoppingCartId() == null) {
            shoppingCartDao.create(shoppingCart);
        }else {shoppingCartDao.update(shoppingCart);}
    }

    @Override
    public void delete(Integer id) {
        shoppingCartDao.delete(id);
    }

    @Override
    public BigDecimal calculateTotalAmount(List<ShoppingCart> shoppingCarts) {
        return shoppingCartDao.calculateTotalAmount(shoppingCarts);
    }
}
