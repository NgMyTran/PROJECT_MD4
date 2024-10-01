package ra.projectmd4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String home() {
        return "user/index";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin/user";
    }
}
