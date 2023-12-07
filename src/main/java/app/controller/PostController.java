package app.controller;

import app.model.DTO.PostDto;
import app.model.DTO.UserDto;
import app.model.Post;
import app.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public String getHome(HttpSession session){
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null){
            return "login.jsp";
        }else return "redirect:/home";
    }

    @PostMapping()
    public String postNews(@RequestParam(value = "content") String content,
                            @RequestParam(value = "post-img", required = false) MultipartFile post_img,
                            HttpSession session){

        String result = "redirect:/profile";
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null) return "authorization_error.jsp";

        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timeCreate = new Timestamp(currentTimeMillis);
        Post post = new Post(content,timeCreate,token.getUser_id());
        PostDto newPost = postService.PostNews(post);
        if (newPost != null){

            if (!post_img.isEmpty()){
                System.out.println(post_img);
                String path = postService.UpLoadImg(newPost.getPost(), post_img);

            }else{
                System.out.println("Controller: không tìm thấy file");
            }
        }

        return result;
    }

    @GetMapping("/delete")
    public String DeletePost(@RequestParam("post_id") Long post_id, HttpSession session){
        String result = "error.jsp";
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null) return "authorization_error.jsp";
        PostDto post = postService.getById(post_id.intValue());

        if(token.getUser_id() == post.getUser().getUser_id()){
            if (postService.DeletePost(post_id.intValue())) result = "redirect:/profile";
        }
        return result;
    }



    @GetMapping("/edit")
    public String getNeededPost(@RequestParam(value ="post_id") Long post_id, HttpSession session, Model model){
        String result = "authorization_error.jsp";
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null) return result;
        PostDto post = postService.getById(post_id.intValue());

        if(token.getUser_id() == post.getUser().getUser_id()){
            result = "edit_post.jsp";
            model.addAttribute("post", post);
        }
        return result;
    }

    @PostMapping("/edit")
    public String EditPost(@RequestParam(value = "post_id") Long post_id,
                           @RequestParam(value = "content") String content, HttpSession session){
        String result = "error.jsp";
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null) return "authorization_error.jsp";
        PostDto post = postService.getById(post_id.intValue());
        if(token.getUser_id() == post.getUser().getUser_id()){
            PostDto editedPost = postService.EditPost(content, post.getPost().getPost_id());
            if (editedPost != null) result = "redirect:/profile";
        }else result = "authorization_error.jsp";
        return result;
    }
}
