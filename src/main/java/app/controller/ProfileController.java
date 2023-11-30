package app.controller;

import app.model.DTO.UserDto;
import app.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;
    @GetMapping
    public String getProfile(@RequestParam(value = "user_id", required = false) Long user_id, HttpSession session, Model model){
        String result = "";
        if (user_id == null) user_id = 0L;
        int id = user_id.intValue();
        UserDto token = (UserDto) session.getAttribute("user");
        if(token == null) result = "login.jsp";
        else {
            boolean isAutho = false;
            if (id == 0 || id == token.getUser_id()){
                id = token.getUser_id();
                isAutho = true;
            }
            UserDto user = userService.getProfile(id);
            model.addAttribute("user", user);
            model.addAttribute("autho", isAutho);
            result = "profile.jsp";
        }
        return result;
    }
}
