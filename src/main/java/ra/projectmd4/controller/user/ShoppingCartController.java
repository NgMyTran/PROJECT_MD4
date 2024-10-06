package ra.projectmd4.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.projectmd4.model.dto.response.UserInfo;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.model.entity.ShoppingCart;
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
        int totalQuantity = cartItems.size();

        if (cartItems.isEmpty()) {
            model.addAttribute("message", "Your cart is empty.");
        } else {
            // Lấy danh sách sản phẩm tương ứng với các sản phẩm trong giỏ hàng
            List<Product> products = cartItems.stream()
                    .map(item -> productService.findById(item.getProductId()))
                    .collect(Collectors.toList());

            // Tính tổng số tiền
            BigDecimal totalAmount = shoppingCartService.calculateTotalAmount(cartItems);
            DecimalFormat df = new DecimalFormat("#.##");
            String formattedTotal = df.format(totalAmount);

            // Cập nhật các thuộc tính cho model
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
            return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
        }

        // Lấy thông tin sản phẩm từ ID
        Product product = productService.findById(productId);
        if (product == null) {
            return "redirect:/products";
        }

        // Tạo ShoppingCart mới
        UserInfo u = (UserInfo) request.getSession().getAttribute("userLogin");
        ShoppingCart existingCartItem = shoppingCartService.findByUserIdAndProductId(u.getUserId(), productId);

        if (existingCartItem != null) {
            // Nếu sản phẩm đã có trong giỏ, tăng số lượng
            existingCartItem.setOrderQuantity(existingCartItem.getOrderQuantity() + 1);
            shoppingCartService.save(existingCartItem);
        } else {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(u.getUserId());
            shoppingCart.setProductId(productId);
            shoppingCart.setOrderQuantity(1);
            shoppingCartService.save(shoppingCart);
        }
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

    // Xóa sản phẩm khỏi giỏ hàng
    @PostMapping("/delete/{id}")
    public String deleteFromCart(@PathVariable("id") Integer id) {
        shoppingCartService.delete(id);
        return "redirect:/user/cart/cart-items";
    }

    @GetMapping("/shopping-cart")
    public String shoppingCart(@RequestParam(value = "items", required = false) String items,
                               Model model,
                               HttpSession session) {
        UserInfo u = (UserInfo) session.getAttribute("userLogin");
        if (u == null) {
            return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
        }
// Kiểm tra xem có các ID sản phẩm được chọn hay không
        if (items != null && !items.isEmpty()) {
            // Chuyển đổi các ID từ chuỗi thành danh sách Integer
            List<Integer> idList = Arrays.stream(items.split(",")) // Tách chuỗi và chuyển thành Stream
                    .map(Integer::parseInt) // Chuyển đổi từng ID từ String sang Integer
                    .collect(Collectors.toList()); // Lưu vào danh sách

            List<ShoppingCart> selectedItems = shoppingCartService.findByIds(idList);
            List<Product> products = selectedItems.stream()
                    .map(item -> productService.findById(item.getProductId()))
                    .collect(Collectors.toList());

            BigDecimal totalAmount = shoppingCartService.calculateTotalAmount(selectedItems);

            // Cập nhật các thuộc tính cho model
            model.addAttribute("products", products);
            model.addAttribute("selectedItems", selectedItems);
            model.addAttribute("totalAmount", totalAmount);
        } else {
            model.addAttribute("message", "No items selected for checkout.");
        }
        return "user/shopping_cart"; // Đến trang thanh toán
    }
}
