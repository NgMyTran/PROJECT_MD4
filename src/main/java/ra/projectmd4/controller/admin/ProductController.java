package ra.projectmd4.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.projectmd4.model.entity.Category;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.service.category.ICategoryService;
import ra.projectmd4.service.product.IProductService;

import javax.servlet.ServletContext;
import java.io.IOException;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IProductService productService;


    @GetMapping
    public String listProduct(Model model) {
        model.addAttribute("products", productService.findAll());
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
    product.setCreatedAt(oldProduct.getCreatedAt());
    product.update();

    Category category = categoryService.findById(categoryId);
    product.setCategory(category);

    try {
        // Chỉ upload ảnh mới nếu checkbox không được chọn
        if (imageFile != null && !imageFile.isEmpty() && !Boolean.TRUE.equals(keepCurrentImage)) {
            productService.uploadImage(product, imageFile, servletContext);
        } else if (Boolean.TRUE.equals(keepCurrentImage)) {
            // Giữ lại hình ảnh cũ
            product.setImage(oldProduct.getImage());
        }
    } catch (IOException e) {
        model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải ảnh cho sản phẩm. Vui lòng thử lại.");
        model.addAttribute("product", product);
        model.addAttribute("categoryList", categoryService.findAll());
        return "/admin/product/edit-product";
    }
    productService.save(product);
    return "redirect:/admin/product"; // Quay lại danh sách sản phẩm
}


    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/admin/product";
    }
}
