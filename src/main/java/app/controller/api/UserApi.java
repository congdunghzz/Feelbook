package app.controller.api;

import app.model.DTO.CommentDto;
import app.model.DTO.UserDto;
import app.model.User;
import app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @ResponseBody
    public List<UserDto> Search(@RequestParam("name") String name){
        if (name == null) name = "";
        return userService.getByName(name);
    }

    @PostMapping("/setAvatar")

    public String setAvatar(@RequestParam(value = "imgFile", required = false) MultipartFile imgFile,
                                           HttpSession session) throws IOException {
        String result = "redirect:/profile";
        UserDto token = (UserDto) session.getAttribute("user");
        if (imgFile.isEmpty()){
            return "error.jsp";
        }
        if (token == null) {
            return "authorization.jsp";
        } else {
            int user_id = token.getUser_id();
            String path = userService.setAvatar(user_id, imgFile);
        }
        return result;
    }

    @PostMapping("/edit")
    public String editInfor(@RequestParam(value = "email", required = false) String email,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "gender", required = false) boolean gender,
                            @RequestParam(value = "dob", required = false) String dob,
                            HttpSession session, Model model){
        UserDto token = (UserDto) session.getAttribute("user");
        Date dayOfBirth;

        if (token == null){
            return "redirect:/login";
        }else {
            if(dob != null){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                java.util.Date date;
                try {
                    date = format.parse(dob);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                dayOfBirth = new Date(date.getTime());
            }else {
                return "error.jsp";
            }
            UserDto newInfor = new UserDto(new User(token.getUser_id(), name, gender,dayOfBirth, email));
            String result = userService.updateInfor(newInfor);
            System.out.println(result);
            model.addAttribute("message", result);
            return "redirect:/profile";
        }
    }

    @GetMapping("friend")
    @ResponseBody
    public List<UserDto> getListFriend (@RequestParam(value = "user_id", required = false) Integer user_id,
                                        HttpSession session){

        List<UserDto> result;
        UserDto token = (UserDto) session.getAttribute("user");
        if (user_id == null) user_id = token.getUser_id();
        result = userService.getFriends(user_id);
        return result;
    }

    @GetMapping("requestFriend")
    @ResponseBody
    public List<UserDto> getListRequestFriend (HttpSession session, HttpServletResponse response) throws IOException {

        List<UserDto> result;
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null){
            Map<String, String> message = new HashMap<>();
            message.put("error", "you are not authorized");
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().println(mapper.writeValueAsString(message));
            return null;
        }
        result = userService.getFriendsWaitingForRequest(token.getUser_id());
        return result;
    }
}
