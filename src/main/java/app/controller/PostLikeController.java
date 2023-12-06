package app.controller;

import app.model.DTO.UserDto;
import app.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.HtmlEscapeTag;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/post-like")
@ResponseBody
public class PostLikeController {
    @Autowired
    private PostService postService;
    @GetMapping("/like")
    public Map<String, String> Like(@RequestParam(value = "post_id", required = false) Long post_id, HttpSession session){
        Map<String, String> result = new HashMap<>();
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null || post_id == null){
            result.put("message", "error");
            return result;
        }
        if (postService.LikePost(post_id.intValue(), token.getUser_id())){
            result.put("message", "successfully");
        }else result.put("message", "error");
        return result;
    }

    @GetMapping("get-num-likes")
    public Map<String, Integer> getNumOfLike(@RequestParam(value = "post-id") Long post_id){
        Map<String, Integer> result = new HashMap<>();
        if (post_id == null){
            result.put("likes", -1);
            return result;
        }
        int likes = postService.getLikeNumOfPost(post_id.intValue());
        result.put("likes", likes);
        return result;
    }

    @GetMapping("/unlike")
    public Map<String, String> UnLike(@RequestParam(value = "post_id", required = false) Long post_id, HttpSession session){
        Map<String, String> result = new HashMap<>();
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null || post_id == null){
            result.put("message", "error");
            System.out.println(token);
            System.out.println(post_id);
            System.out.println("Controller: token not be found or post_id is null");
            return result;
        }
        if (postService.UnLike(post_id.intValue(), token.getUser_id())){
            System.out.println("controller : đang unlike");
            result.put("message", "successfully");
        }else{
            System.out.println("Controller: hàm unlike return false");
            result.put("message", "error");
        }
        return result;
    }
}
