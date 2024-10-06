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
import java.util.ArrayList;
import java.util.List;
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
        // Lấy tất cả các danh mục
        List<Category> categories = iCategoryService.findAll(); // Giả sử bạn có dịch vụ để lấy danh mục
        List<Product> sellerProduct = new ArrayList<>();

        // Duyệt qua từng danh mục và lấy sản phẩm
        for (Category category : categories) {
            List<Product> products = iProductService.findByCategoryId(category.getId());

            // Giới hạn số lượng sản phẩm hiển thị tối đa 2 cho mỗi danh mục
            if (products.size() > 2) {
                products = products.stream().limit(2).collect(Collectors.toList());
            }

            sellerProduct.addAll(products); // Thêm sản phẩm vào danh sách sellerProduct
        }

        UserInfo userInfo = (UserInfo) session.getAttribute("userLogin");
        int totalQuantity = 0;

        if (userInfo != null) {
            List<ShoppingCart> cartItems = iShoppingCartService.findByUserId(userInfo.getUserId());
            totalQuantity = cartItems.stream().mapToInt(ShoppingCart::getOrderQuantity).sum(); // Tính tổng số lượng
        }

        model.addAttribute("sellerProduct", sellerProduct);
        model.addAttribute("totalQuantity", totalQuantity);

        return "/user/index";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin/dashboard";
    }

}
