//package ra.projectmd4.controller.admin;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import ra.projectmd4.model.dto.response.ProductResponse;
//import ra.projectmd4.model.entity.Category;
//import ra.projectmd4.model.entity.Product;
//import ra.projectmd4.service.category.ICategoryService;
//import ra.projectmd4.service.product.IProductService;
//
//import javax.servlet.ServletContext;
//import java.io.IOException;
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin/product")
//public class ProductController {
//    @Autowired
//    private ServletContext servletContext;
//
//    @Autowired
//    private ICategoryService categoryService;
//
//    @Autowired
//    private IProductService productService;
//
//    @GetMapping
//    public String list(@RequestParam(defaultValue = "") String keyword,
//                       @RequestParam(defaultValue = "0") int page,
//                       @RequestParam(defaultValue = "4") int size,
//                       Model model) {
//        long totalElements =productService.getTotalElements(keyword);
//        long nguyen = totalElements / size;
//        long du = totalElements % size;
//        long totalPages = (du == 0) ? nguyen : nguyen + 1;
//        List<ProductResponse> products = productService.getListProducts(keyword, page, size);
//        model.addAttribute("totalPages", totalPages);
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("page", page);
//        model.addAttribute("products", products);
//
//        return "/admin/product/list-product"; // Return the correct view
//    }
//
////    public String list(@RequestParam(defaultValue = "") String keyword,
////                       @RequestParam(defaultValue = "0") int page,
////                       @RequestParam(defaultValue = "4") int size,
////                       Model model) {
////        List<ProductResponse> products = productService.getListProducts(keyword, page, size);
//////        long totalElements = productService.getTotalElements(keyword);
////        long totalElements = keyword.isEmpty() ? productService.getTotalElements("") : productService.getTotalElements(keyword);
////        long totalPages = (totalElements > 0) ? (totalElements + size - 1) / size : 1;
////
////        model.addAttribute("totalPages", totalPages);
////        model.addAttribute("keyword", keyword);
////        model.addAttribute("page", page);
////        model.addAttribute("products", products);
////        return "/admin/product/list-product";
////    }
//
//    @GetMapping("/detail/{id}")
//    public String detailProducts(@PathVariable Long id, Model model) {
//        model.addAttribute("product", productService.findById(id));
//        return "/admin/product/detail-product";
//    }
//
//    @GetMapping("/add")
//    public String addProductForm(Model model) {
//        model.addAttribute("categoryList", categoryService.findActiveCategories());
//        return "/admin/product/add-product";
//    }
//
//    @PostMapping("/added")
//    public String addProduct(@ModelAttribute Product product,
//                             @RequestParam Long categoryId,
//                             @RequestParam MultipartFile imageFile) {
//        Category category = categoryService.findById(categoryId);
//        product.setCategory(category);
//        try {
//            productService.uploadImage(product, imageFile, servletContext);
//            productService.save(product);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "/admin/product/add-product"; // Redirect back to add product page on error
//        }
//        return "redirect:/admin/product";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editProductForm(@PathVariable Long id, Model model) {
//        model.addAttribute("product", productService.findById(id));
//        model.addAttribute("categoryList", categoryService.findAll());
//        return "/admin/product/edit-product";
//    }
//
//    @PostMapping("/update")
//    public String updateProduct(@ModelAttribute Product product,
//                                @RequestParam Long categoryId,
//                                @RequestParam(required = false) MultipartFile imageFile,
//                                @RequestParam(required = false) Boolean keepCurrentImage,
//                                Model model) {
//        Product oldProduct = productService.findById(product.getId());
//        product.setCreatedAt(oldProduct.getCreatedAt());
//        product.update();
//        Category category = categoryService.findById(categoryId);
//        product.setCategory(category);
//        try {
//            if (Boolean.TRUE.equals(keepCurrentImage)) {
//                product.setImage(oldProduct.getImage()); //Nếu chọn giữ lại img cũ, thì gán nó cho sản phẩm.
//            } else if (imageFile != null && !imageFile.isEmpty()) {
//                productService.uploadImage(product, imageFile, servletContext);
//            }
//            productService.save(product);
//        } catch (IOException e) {
//            model.addAttribute("errorMessage", "Error uploading image. Please try again.");
////            Thêm thông báo lỗi và thông tin sản phẩm vào model để gửi lại cho view.
//            model.addAttribute("product", product);
//            model.addAttribute("categoryList", categoryService.findAll());
//            return "/admin/product/edit-product";//Trả về view chỉnh sửa sản phẩm với thông báo lỗi.
//        }
//        productService.save(product);
//        return "redirect:/admin/product";
//    }
//
//    @PostMapping("/delete/{id}")
//    public String deleteProduct(@PathVariable Long id, @RequestParam boolean confirm) {
//        if (confirm) {
//            productService.delete(id);
//        }
//        return "redirect:/admin/product";
//    }
//}

package ra.projectmd4.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.projectmd4.model.dto.response.ProductResponse;
import ra.projectmd4.model.entity.Category;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.service.category.ICategoryService;
import ra.projectmd4.service.product.IProductService;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/product")

public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IProductService productService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "4") int size,
                       Model model) {
        productService.getListProducts(keyword, page, size);
        List<ProductResponse> products = productService.getListProducts(keyword, page, size);
        System.out.println("Danh sách sản phẩm:");
        for (ProductResponse product : products) {
            System.out.println(product);
        }
        long totalElements = keyword.isEmpty() ? productService.getTotalElements("") : productService.getTotalElements(keyword);
        long totalPages = totalElements > 0 ? (totalElements + size - 1) / size : 1;
//        model.addAttribute("totalPages", (productService.getTotalElements(keyword) + size - 1) / size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("products", products);
        return "/admin/product/list-product";
    }

    @GetMapping("/detail/{id}")
    public String detailProducts(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "/admin/product/detail-product";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        //categories
        model.addAttribute("categoryList", categoryService.findActiveCategories());
        model.addAttribute("productList", productService.findAll());
        return "/admin/product/add-product";
    }

    @PostMapping("/added")
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam Long categoryId,
                             @RequestParam MultipartFile imageFile) {
        Category category = categoryService.findById(categoryId);
        product.setCategory(category);
        try {
            productService.uploadImage(product, imageFile, servletContext);
        } catch (IOException e) {
            e.printStackTrace();
            return "/admin/product/add-product"; // Quay lại trang thêm sản phẩm khi có lỗi
        }
        productService.save(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categoryList", categoryService.findAll());
        return "/admin/product/edit-product";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product,
                                @RequestParam Long categoryId,
                                @RequestParam(required = false) MultipartFile imageFile,
                                @RequestParam(required = false) Boolean keepCurrentImage,
                                Model model) {
        Product oldProduct = productService.findById(product.getId());
        // Kiểm tra ảnh
        if (imageFile.isEmpty() && !Boolean.TRUE.equals(keepCurrentImage)) {
            model.addAttribute("errorMessage", "Bạn phải chọn một trong hai tùy chọn để cập nhật ảnh.");
            model.addAttribute("product", product);
            model.addAttribute("categoryList", categoryService.findAll());
            return "/admin/product/edit-product"; // Trả về trang chỉnh sửa với thông báo lỗi
        }
        // Cập nhật thông tin sản phẩm
        product.setCreatedAt(oldProduct.getCreatedAt());
        product.update();
        Category category = categoryService.findById(categoryId);
        product.setCategory(category);
        try {
            if (Boolean.TRUE.equals(keepCurrentImage)) {
                // Giữ lại hình ảnh cũ
                product.setImage(oldProduct.getImage());
            } else if (imageFile != null && !imageFile.isEmpty()) {
                // Upload ảnh mới
                productService.uploadImage(product, imageFile, servletContext);
            }
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải ảnh cho sản phẩm. Vui lòng thử lại.");
            model.addAttribute("product", product);
            model.addAttribute("categoryList", categoryService.findAll());
            return "/admin/product/edit-product";
        }

        productService.save(product);
        return "redirect:/admin/product";
    }


    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, @RequestParam boolean confirm) {
        if (confirm) {
            productService.delete(id);
        }
        return "redirect:/admin/product";
    }

}
