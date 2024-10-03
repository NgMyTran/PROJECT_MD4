package ra.projectmd4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.service.product.IProductService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private IProductService iProductService;

//    @GetMapping
//    public String home() {
//        return "user/index";
//    }

    @GetMapping("/admin")
    public String admin(){
        return "admin/dashboard";
    }

    @GetMapping
    public String home(Model model) {
        long id = 1;
        List<Product> sellerProduct = iProductService.findByCategoryId(id);
        if (sellerProduct.size() > 2){
            sellerProduct = sellerProduct.stream().limit(2).collect(Collectors.toList());
        }
        model.addAttribute("sellerProduct", sellerProduct);
//        return "layout/home";
        return "user/index";
    }

//
//    @GetMapping("/contact")
//    public String contact(){
////        return "layout/contact";
//        return "user/index";
//
//    }
}
