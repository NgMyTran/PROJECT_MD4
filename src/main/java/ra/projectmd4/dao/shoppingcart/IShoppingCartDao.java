package ra.projectmd4.dao.shoppingcart;

import ra.projectmd4.dao.IGenericDao;
import ra.projectmd4.model.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;

public interface IShoppingCartDao extends IGenericDao<ShoppingCart, Integer> {
    BigDecimal calculateTotalAmount(List<ShoppingCart> shoppingCarts);
}
