package ra.projectmd4.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.projectmd4.model.entity.Category;
import ra.projectmd4.model.entity.Product;
import ra.projectmd4.service.category.ICategoryService;
import ra.projectmd4.service.product.IProductService;

import javax.persistence.PersistenceContext;
import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IProductService productService;

    @GetMapping
    public String listCategory(Model model, @RequestParam(defaultValue = "") String keyword) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categories", categoryService.getListCategories(keyword));
        return "/admin/category/list-category";
    }

    @GetMapping("/add")
    public String addCategoryForm() {
        return "/admin/category/add-category";
    }
    @PostMapping("/added")
    public String addCategory(@ModelAttribute Category category) {
        category.setStatus(true);
        categoryService.save(category);
        return "redirect:/admin/category";
    }
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return"/admin/category/edit-category";
    }
    @PostMapping("/update")
    public String updateCategory( @ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, @RequestParam boolean confirm) {
        categoryService.deleteCategoryConfirm(id, confirm);
        return "redirect:/admin/category";
    }

}
