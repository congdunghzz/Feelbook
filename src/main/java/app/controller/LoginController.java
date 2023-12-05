package app.controller;

import app.model.DTO.UserDto;
import app.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;
    @GetMapping
    public String getView(HttpSession session){
        String result = "login.jsp";
        UserDto user = (UserDto) session.getAttribute("user");
        if (user != null) result = "redirect:/home";
        return result;
    }

    @PostMapping
    public String logIn(@RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password,
                        HttpSession session){
        String result = "";
        UserDto token = userService.Login(username, password);
        if (token != null) {

            result = "redirect:/home";
            session.setAttribute("user", token);

        }
        else result = "redirect:/login";

        return result;
    }
}
