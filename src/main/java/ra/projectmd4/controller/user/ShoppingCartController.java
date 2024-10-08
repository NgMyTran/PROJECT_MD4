package ra.projectmd4.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.projectmd4.model.dto.response.UserInfo;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.model.entity.ShoppingCart;
import ra.projectmd4.service.order.IOrderService;
import ra.projectmd4.service.product.IProductService;
import ra.projectmd4.service.shoppingcart.IShoppingCartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Controller
@RequestMapping("/user/cart")
public class ShoppingCartController {
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private IProductService productService;

    @GetMapping("/cart-items")
    public String getShoppingCart(Model model, HttpSession session) {
        UserInfo u = (UserInfo) session.getAttribute("userLogin");
        if (u == null) {
            return "redirect:/login";
        }
        List<ShoppingCart> cartItems = shoppingCartService.findByUserId(u.getUserId());
        int totalQuantity = cartItems.stream().mapToInt(ShoppingCart::getOrderQuantity).sum();
        if (cartItems.isEmpty()) {
            model.addAttribute("message", "Your cart is empty.");
        } else {
            List<Product> products = shoppingCartService.getProductsFromCartItems(cartItems);
            BigDecimal totalAmount = shoppingCartService.calculateTotalAmount(cartItems);
            String formattedTotal = new DecimalFormat("#.##").format(totalAmount);

            model.addAttribute("totalQuantity", totalQuantity);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("products", products);
            model.addAttribute("formattedTotal", formattedTotal);
        }
        return "/user/cart_items";
    }

    @PostMapping("/add")
    public String addToCart(HttpServletRequest request,
                            @RequestParam("productId") Long productId,
                            HttpSession session) {
        if (session.getAttribute("userLogin") == null) {
            return "redirect:/login";
        }
        UserInfo u = (UserInfo) request.getSession().getAttribute("userLogin");
        Product product = productService.findById(productId);
        if (product == null) {
            return "redirect:/products";
        }
        shoppingCartService.saveShoppingCart(u, productId);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateQuantity(@PathVariable("id") Integer id, @RequestParam("orderQuantity") int quantity) {
        ShoppingCart cartItem = shoppingCartService.findById(id);
        if (cartItem != null) {
            cartItem.setOrderQuantity(quantity);
            shoppingCartService.save(cartItem);
        }
        return "redirect:/user/cart/cart-items";
    }

    @PostMapping("/delete/{id}")
    public String deleteFromCart(@PathVariable("id") Integer id) {
        shoppingCartService.delete(id);
        return "redirect:/user/cart/cart-items";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam(value = "items", required = false) String items,
                           Model model,
                           HttpSession session) {
        UserInfo u = (UserInfo) session.getAttribute("userLogin");
        if (u == null) {
            return "redirect:/login";
        }
        if (items != null && !items.isEmpty()) {
            List<Integer> idList = Arrays.stream(items.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            List<ShoppingCart> selectedItems = shoppingCartService.findByIds(idList);
            List<Product> products = shoppingCartService.getProductsFromCartItems(selectedItems);
            BigDecimal totalAmount = shoppingCartService.calculateTotalAmountFromCart(selectedItems);
            model.addAttribute("products", products);
            model.addAttribute("selectedItems", selectedItems);
            model.addAttribute("totalAmount", totalAmount);
            model.addAttribute("userId", u.getUserId());
        } else {
            model.addAttribute("message", "No items selected for checkout.");
        }
        return "user/shopping_cart";
    }
}
