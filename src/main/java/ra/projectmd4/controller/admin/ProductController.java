package ra.projectmd4.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.projectmd4.model.entity.Category;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.service.category.ICategoryService;
import ra.projectmd4.service.product.IProductService;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

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
    public String addProduct(@ModelAttribute Product product, @RequestParam Long categoryId, @RequestParam MultipartFile imageFile) {
        Category category = categoryService.findById(categoryId);
        product.setCategory(category);
        LocalDate now = LocalDate.now();
        product.setCreatedAt(now);
        product.setUpdatedAt(now);

        // Định nghĩa đường dẫn cho thư mục uploads
        String path = servletContext.getRealPath("/uploads");
        File uploadDir = new File(path);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();// Tạo thư mục uploads nếu nó không tồn tại
        }
        if (!imageFile.isEmpty()) {
            try {
                String fileName = imageFile.getOriginalFilename();
                File destinationFile = new File(uploadDir, fileName);
                // Sao chép tệp vào thư mục uploads
                imageFile.transferTo(destinationFile);
                product.setImage("/uploads/" + fileName);// Đặt đường dẫn hình ảnh trong sản phẩm
            } catch (IOException e) {
                e.printStackTrace();
                return "/admin/product/add-product";// Quay lại trang thêm sản phẩm khi có lỗi
            }
        } else {
            // Handle case where the file doesn't exist
            return "không có tệp nào được tải lên";
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

//        @PostMapping("/update")
//    public String updateProduct(@ModelAttribute Product product, @RequestParam Long categoryId) {
//        Category category = categoryService.findById(categoryId);
//        product.setCategory(category);
//            product.setUpdatedAt(LocalDate.now());
//        productService.save(product);
//        return "redirect:/admin/product";
//    }
@PostMapping("/update")
public String updateProduct(@ModelAttribute Product product,
                            @RequestParam Long categoryId,
                            @RequestParam(required = false) MultipartFile imageFile) {
    Category category = categoryService.findById(categoryId);
    product.setCategory(category);
    product.setCreatedAt(product.getUpdatedAt());
    product.setUpdatedAt(LocalDate.now());

    String path = servletContext.getRealPath("/uploads");
    File uploadDir = new File(path);
    if (!uploadDir.exists()) {
        uploadDir.mkdirs(); // Tạo thư mục uploads nếu nó không tồn tại
    }

    if (imageFile != null && !imageFile.isEmpty()) {
        try {
            String fileName = imageFile.getOriginalFilename();
            assert fileName != null;
            File destinationFile = new File(uploadDir, fileName);
            // Sao chép tệp vào thư mục uploads
            imageFile.transferTo(destinationFile);
            product.setImage("/uploads/" + fileName); // Cập nhật đường dẫn hình ảnh trong sản phẩm
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/admin/product/edit/" + product.getId(); // Quay lại trang chỉnh sửa sản phẩm khi có lỗi
        }
    }

    productService.save(product);
    return "redirect:/admin/product"; // Quay lại danh sách sản phẩm
}


    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
//        model.addAttribute("product", productService.findById(id));
        productService.delete(id);
        return "redirect:/admin/product";
    }
}
