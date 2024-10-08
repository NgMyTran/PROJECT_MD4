package ra.projectmd4.service.shoppingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.projectmd4.dao.shoppingcart.IShoppingCartDao;
import ra.projectmd4.model.dto.response.UserInfo;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.model.entity.ShoppingCart;
import ra.projectmd4.service.product.IProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

//
//@Service
//@Transactional
//public class ShoppingCartServiceImpl implements IShoppingCartService {
//    @Autowired
//    private IShoppingCartDao shoppingCartDao;
//
//    @Override
//    public List<ShoppingCart> findAll() {
//        return shoppingCartDao.findAll();
//    }
//
//    @Override
//    public ShoppingCart findById(Integer id) {
//        return shoppingCartDao.findById(id);
//    }
//
//    @Override
//    public void save(ShoppingCart shoppingCart) {
//        if (shoppingCart.getShoppingCartId() == null) {
//            shoppingCartDao.create(shoppingCart);
//        }else {shoppingCartDao.update(shoppingCart);}
//    }
//
//    @Override
//    public void delete(Integer id) {
//        shoppingCartDao.delete(id);
//    }
//
//    @Override
//    public BigDecimal calculateTotalAmount(List<ShoppingCart> shoppingCarts) {
//        return shoppingCartDao.calculateTotalAmount(shoppingCarts);
//    }
//
//    @Override
//    public List<ShoppingCart> findByUserId(Long userId) {
//        return shoppingCartDao.findByUserId(userId);
//    }
//
//    @Override
//    public ShoppingCart findByUserIdAndProductId(Long userId, Long productId) {
//        return shoppingCartDao.findByUserIdAndProductId(userId, productId);
//    }
//
//    @Override
//    public List<ShoppingCart> findByIds(List<Integer> ids) {
//        return shoppingCartDao.findByIds(ids);
//    }
//}
@Service
@Transactional
public class ShoppingCartServiceImpl implements IShoppingCartService {
    @Autowired
    private IShoppingCartDao shoppingCartDao;

    @Autowired
    private IProductService productService;

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
        } else {
            shoppingCartDao.update(shoppingCart);
        }
    }

    @Override
    public void delete(Integer id) {
        shoppingCartDao.delete(id);
    }

    @Override
    public BigDecimal calculateTotalAmount(List<ShoppingCart> shoppingCarts) {
        return shoppingCartDao.calculateTotalAmount(shoppingCarts);
    }

    @Override
    public List<ShoppingCart> findByUserId(Long userId) {
        return shoppingCartDao.findByUserId(userId);
    }

    @Override
    public ShoppingCart findByUserIdAndProductId(Long userId, Long productId) {
        return shoppingCartDao.findByUserIdAndProductId(userId, productId);
    }

    @Override
    public List<ShoppingCart> findByIds(List<Integer> ids) {
        return shoppingCartDao.findByIds(ids);
    }

    public void saveShoppingCart(UserInfo userInfo, Long productId) {
        ShoppingCart existingCartItem = findByUserIdAndProductId(userInfo.getUserId(), productId);
        if (existingCartItem != null) {
            existingCartItem.setOrderQuantity(existingCartItem.getOrderQuantity() + 1);
            save(existingCartItem);
        } else {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(userInfo.getUserId());
            shoppingCart.setProductId(productId);
            shoppingCart.setOrderQuantity(1);
            save(shoppingCart);
        }
    }

    public List<Product> getProductsFromCartItems(List<ShoppingCart> cartItems) {
        return cartItems.stream()
                .map(item -> productService.findById(item.getProductId()))
                .collect(Collectors.toList());
    }

    public BigDecimal calculateTotalAmountFromCart(List<ShoppingCart> cartItems) {
        return calculateTotalAmount(cartItems);
    }
}
