package app.controller;

import app.model.DTO.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    @GetMapping
    public String Logout(HttpSession session){
        String result = "";
        UserDto token = (UserDto) session.getAttribute("user");
        if(token != null){
            session.removeAttribute("user");
            session.invalidate();
        }
        result ="redirect:/login";
        return result;
    }
}
