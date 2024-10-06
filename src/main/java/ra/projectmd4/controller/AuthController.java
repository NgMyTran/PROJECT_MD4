package ra.projectmd4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ra.projectmd4.exception.AuthenticationException;
import ra.projectmd4.model.dto.request.FormLogin;
import ra.projectmd4.model.dto.request.FormRegister;
import ra.projectmd4.model.dto.response.UserInfo;
import ra.projectmd4.service.user.IUserService;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("request", new FormLogin());
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(){
        return "auth/register";
    }

@GetMapping("/logout")
public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
}

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute FormRegister request) {
        try {
            userService.register(request);
            return "auth/login"; // Redirect to login after successful registration
        } catch (Exception e) {
            return "auth/register"; // Return to the registration page with an error
        }
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute FormLogin request, HttpSession session, Model model) {
        try {
            UserInfo userInfo = userService.login(request);
            session.setAttribute("userLogin", userInfo);

            if (!userInfo.isStatus()) { // Check if the user is blocked
                model.addAttribute("error", "Tài khoản của bạn đã bị khóa. Vui lòng liên hệ hỗ trợ.");
                return "auth/login";
            }
            System.out.println("Username: " + request.getUsername());

            if (userInfo.isRole()) {
                return "redirect:/admin"; // Redirect to admin if the user is an admin
            } else {
                return "redirect:/"; // Redirect to the home page if the user is a regular user
            }
        } catch (AuthenticationException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("request", request);
            return "auth/login"; // Return to the login page with an error
        }
    }

}
