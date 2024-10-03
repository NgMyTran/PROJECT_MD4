package ra.projectmd4.service.shoppingcart;

import ra.projectmd4.model.entity.ShoppingCart;
import ra.projectmd4.service.IGenericService;

import java.math.BigDecimal;
import java.util.List;

public interface IShoppingCartService extends IGenericService<ShoppingCart, Integer> {
    BigDecimal calculateTotalAmount(List<ShoppingCart> shoppingCarts);

}
