package app.controller.api;

import app.model.DTO.UserDto;
import app.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/user")
public class UserApi {
    @Autowired
    private UserService userService;
    @GetMapping("by_id")
    @ResponseBody
    public UserDto getUserProfile(@RequestParam("user_id") Long user_id, HttpSession session){
        if (user_id == null) return null;
        UserDto user = userService.getProfile(user_id.intValue());
        return user;
    }

    @GetMapping
    public List<UserDto> Search(@RequestParam("name") String name){
        if (name == null) name = "";
        return userService.getByName(name);
    }


}
