package app.controller;

import app.model.DTO.UserDto;
import app.model.User;
import app.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/sign-up")
public class SignUpController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String getSignUpPage(){

        String result = "";
        result = "register.jsp";
        return result;
    }

    @PostMapping
    public String SignUp(@RequestParam(value = "username") String username,
                         @RequestParam(value = "password") String password,
                         @RequestParam(value = "email") String email,
                         @RequestParam(value = "name") String name,
                         @RequestParam(value = "gender") boolean gender,
                         @RequestParam(value = "dob") String dob) {
        String result = "";
        Date dayOfBirth = null;
        if(dob != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            java.util.Date date;
            try {

                date = format.parse(dob);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            dayOfBirth = new Date(date.getTime());
        }

        User user = new User(name, gender,dayOfBirth,email,username,password);
        UserDto token = userService.SignUp(user);

        if (token != null){
            result = "redirect:/login";
        }
        else{
            System.out.println("lỗi");
            result = "redirect:/sign-up";
        }
        System.out.println(token);
        return result;

    }
}
