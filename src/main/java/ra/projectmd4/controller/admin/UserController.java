package ra.projectmd4.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.projectmd4.exception.AuthenticationException;
import ra.projectmd4.service.user.IUserService;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "") String keyword
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "2") int size
            , Model model) {
        long totalElements = userService.getTotalElements(keyword);
        long nguyen = totalElements / size;
        long du = totalElements % size;
        long totalPages = du == 0 ? nguyen : nguyen + 1;
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("users", userService.getListUsers(keyword, page, size));
        return "admin/user";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        userService.delete(id);
        return "redirect:/admin/user";
    }

    @PostMapping("/block/{id}")
    public String block(@PathVariable long id, RedirectAttributes redirectAttributes, @RequestParam int page) {
        try {
            userService.block(id);
            redirectAttributes.addFlashAttribute("success", "User blocked successfully.");
        } catch (AuthenticationException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/user?page=" +page;
    }

    @PostMapping("/unblock/{id}")
    public String unBlock(@PathVariable long id, @RequestParam int page) {
        userService.unBlock(id);
        return "redirect:/admin/user?page=" +page;
    }
}
