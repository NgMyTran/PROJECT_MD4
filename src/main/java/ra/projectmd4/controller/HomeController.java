package ra.projectmd4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ra.projectmd4.model.dto.response.UserInfo;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.model.entity.ShoppingCart;
import ra.projectmd4.service.product.IProductService;
import ra.projectmd4.service.shoppingcart.IShoppingCartService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IShoppingCartService iShoppingCartService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        long id = 1;
        List<Product> sellerProduct = iProductService.findByCategoryId(id);
        if (sellerProduct.size() > 2) {
            sellerProduct = sellerProduct.stream().limit(2).collect(Collectors.toList());
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
