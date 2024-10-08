package ra.projectmd4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ra.projectmd4.model.dto.response.UserInfo;
import ra.projectmd4.model.entity.Category;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.model.entity.ShoppingCart;
import ra.projectmd4.service.category.ICategoryService;
import ra.projectmd4.service.product.IProductService;
import ra.projectmd4.service.shoppingcart.IShoppingCartService;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private IProductService iProductService;
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private IShoppingCartService iShoppingCartService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        List<Category> categories = iCategoryService.findActiveCategories();
        List<Product> sellerProduct = new ArrayList<>();

        // Lấy sản phẩm theo danh mục và giới hạn
        for (Category category : categories) {
            List<Product> products = iProductService.findActiveProductsByCategoryId(category.getId())
                    .stream()
                    .filter(Product::isStatus) // Chỉ giữ lại sản phẩm có status = true
                    .limit(2) // Giới hạn số lượng sản phẩm tối đa là 2
                    .collect(Collectors.toList());
            sellerProduct.addAll(products);
        }
        Collections.shuffle(sellerProduct);
        UserInfo userInfo = (UserInfo) session.getAttribute("userLogin");
        int totalQuantity = 0;
        if (userInfo != null) {
            List<ShoppingCart> cartItems = iShoppingCartService.findByUserId(userInfo.getUserId());
            totalQuantity = cartItems.stream().mapToInt(ShoppingCart::getOrderQuantity).sum();
        }
        model.addAttribute("sellerProduct", sellerProduct); // Gửi danh sách sản phẩm
        model.addAttribute("totalQuantity", totalQuantity);
        return "/user/index";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin/dashboard";
    }


@GetMapping("/product-detail")
public String productDetail(Model model, HttpSession session) {
    List<Category> categories = iCategoryService.findActiveCategories();
//    List<Product> products = new ArrayList<>();
//    for (Category category : categories) {
//        List<Product> categoryProducts = iProductService.findActiveProductsByCategoryId(category.getId());
//        products.addAll(categoryProducts);
//    }
    List<Product> products = iProductService.findActiveProducts();
    UserInfo userInfo = (UserInfo) session.getAttribute("userLogin");
    int totalQuantity = 0;
    if (userInfo != null) {
        List<ShoppingCart> cartItems = iShoppingCartService.findByUserId(userInfo.getUserId());
        totalQuantity = cartItems.stream().mapToInt(ShoppingCart::getOrderQuantity).sum(); // Tính tổng số lượng
    }

    model.addAttribute("totalQuantity", totalQuantity);
    model.addAttribute("categories", categories);
    model.addAttribute("products", products);
    return "/user/product_detail";
}

}
