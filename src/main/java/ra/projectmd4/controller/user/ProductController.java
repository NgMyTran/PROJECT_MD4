//package ra.projectmd4.controller.user;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import ra.projectmd4.service.product.IProductService;
//
//@Controller
//@RequestMapping("/user/product")
//public class ProductController {
//@Autowired
//private IProductService productService;
//
//    @GetMapping
//    public String listProducts(Model model, @RequestParam(defaultValue = "") String keyword) {
//        model.addAttribute("products", productService.findAll());
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("products", productService.getListProducts(keyword));
//        return "/admin/product/list-product";
//    }
//}
