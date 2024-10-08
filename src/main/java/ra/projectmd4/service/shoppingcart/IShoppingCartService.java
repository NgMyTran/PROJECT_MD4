package ra.projectmd4.service.shoppingcart;

import ra.projectmd4.model.dto.response.UserInfo;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.model.entity.ShoppingCart;
import ra.projectmd4.service.IGenericService;

import java.math.BigDecimal;
import java.util.List;

public interface IShoppingCartService extends IGenericService<ShoppingCart, Integer> {
    BigDecimal calculateTotalAmount(List<ShoppingCart> shoppingCarts);
    List<ShoppingCart> findByUserId(Long userId);
    ShoppingCart findByUserIdAndProductId(Long userId, Long productId);
    public List<ShoppingCart> findByIds(List<Integer> ids);
    List<Product> getProductsFromCartItems(List<ShoppingCart> cartItems);
    void saveShoppingCart(UserInfo userInfo, Long productId);
    BigDecimal calculateTotalAmountFromCart(List<ShoppingCart> cartItems);
}
