package app.controller.api;

import app.model.DTO.CommentDto;
import app.model.DTO.UserDto;
import app.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/setAvatar")

    public String EditComment(@RequestParam(value = "imgFile", required = false) MultipartFile imgFile,
                                           HttpSession session) throws IOException {
        String result = "redirect:/profile";
        UserDto token = (UserDto) session.getAttribute("user");
        if (imgFile.isEmpty()){
            return "error.jsp";
        }
        if (token == null) {
            System.out.println("API, you are not authorized" );
            return "authorization.jsp";
        } else {
            System.out.println(imgFile.getOriginalFilename());
            int user_id = token.getUser_id();
            String path = userService.setAvatar(user_id, imgFile);
            System.out.println(path);
        }
        return result;
    }

}
