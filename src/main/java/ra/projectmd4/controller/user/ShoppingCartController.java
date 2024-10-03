package ra.projectmd4.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.model.entity.ShoppingCart;
import ra.projectmd4.service.product.IProductService;
import ra.projectmd4.service.shoppingcart.IShoppingCartService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user/cart")
public class ShoppingCartController {
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private IProductService productService;

    @GetMapping("/shopping-cart")
    public String getShoppingCart(Model model) {
        List<ShoppingCart> cartItems = shoppingCartService.findAll();
        List<Product> products = cartItems.stream()
                .map(item -> productService.findById(item.getProductId())) // Giả sử có phương thức findById trong IProductService
                .collect(Collectors.toList());
        BigDecimal totalAmount = shoppingCartService.calculateTotalAmount(cartItems);
        // Định dạng tổng tiền
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedTotal = df.format(totalAmount);

        // Phương thức này lấy tất cả các mục trong giỏ hàng
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("products", products);
        model.addAttribute("formattedTotal", formattedTotal);
        return "user/shopping_cart";
    }

    @GetMapping("/cart-items")
    public String getCartItems(Model model) {
        List<ShoppingCart> cartItems = shoppingCartService.findAll();
        List<Product> products = cartItems.stream()
                .map(item -> productService.findById(item.getProductId())) // Giả sử có phương thức findById trong IProductService
                .collect(Collectors.toList());
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("products", products);
        return "user/cart_items";
    }



    @PostMapping("/add")
    public String addToCart(@ModelAttribute ShoppingCart shoppingCart, HttpSession session) {
        if (session.getAttribute("userLogin") == null) {
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
        }
        shoppingCartService.save(shoppingCart);
        return "redirect:/user/shopping-cart";
    }

    @PostMapping("/update/{id}")
    public String updateQuantity(@PathVariable("id") Integer id, @RequestParam("quantity") int quantity) {
        ShoppingCart cartItem = shoppingCartService.findById(id); // Tìm sản phẩm trong giỏ hàng
        if (cartItem != null) {
            cartItem.setOrderQuantity(quantity);
            shoppingCartService.save(cartItem);
        }
        return "redirect:/user/shopping-cart";
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @PostMapping("/delete/{id}")
    public String deleteFromCart(@PathVariable("id") Integer id) {
        shoppingCartService.delete(id);
        return "redirect:/user/shopping-cart";
    }
}
