package app.controller.api;

import app.model.DTO.PostDto;
import app.model.DTO.UserDto;
import app.model.Post;
import app.model.User;
import app.service.PostService;
import app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/api/post")
public class PostApi {
    @Autowired
    private PostService postService;
    @GetMapping("/post_per_page")
    @ResponseBody
    public List<PostDto> getPost (@RequestParam(value = "key", required = false) String key,
                                  @RequestParam(value = "page", required = false) Long page, HttpSession session){
        if (key == null) key = "";
        if (page == null) page = 1L;
        int numOfItem = 3;
        /*if ((UserDto) session.getAttribute("user") == null) return null;*/
        List<PostDto> postList = postService.getByNameWithPerPage(key, page.intValue(), numOfItem);

        return postList;
    }

    @GetMapping("/user-post")
    @ResponseBody
    public List<PostDto> getPostByUser(@RequestParam(value = "user_id") Long user_id){

        if (user_id == null) user_id = 0L;
        List<PostDto> result = postService.getByUserId(user_id.intValue());
        return  result;
    }

    @GetMapping("/num-of-all")
    @ResponseBody
    public Map<String, Integer> getNum (@RequestParam(value = "key", required = false) String key){
        // response.setContentType("application/json");
        if (key == null) key = "";
        List<PostDto> postList = postService.getByName(key);

        Map<String, Integer> object = new TreeMap<>();
        object.put("length", (int)(Math.ceil((double)(postList.size()) / 3)));

        return object;
    }

    @PostMapping("/post-news")
    @ResponseBody
    public PostDto postNews(@RequestParam(value = "content") String content,
                            @RequestParam(value = "post_img", required = false) MultipartFile post_img,
                            HttpSession session){

        PostDto result;
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null) return null;

        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timeCreate = new Timestamp(currentTimeMillis);
        Post post = new Post(content,timeCreate,token.getUser_id());
        PostDto newPost = postService.PostNews(post);
        if (newPost != null){
            result = newPost;
            if (post_img != null){
                String path = postService.UpLoadImg(newPost.getPost(), post_img);
                System.out.printf(path);
            }
        }else{
            System.out.println("không tìm thấy file");
            result = null;
        }

        return result;
    }

    @DeleteMapping("/delete")
    public String DeletePost(@RequestParam("post_id") Long post_id, HttpSession session){
        String result = "error.jsp";
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null) return "login.jsp";
        PostDto post = postService.getById(post_id.intValue());

        if(token.getUser_id() == post.getUser().getUser_id()){
            if (postService.DeletePost(post_id.intValue())) result = "redirect:/profile";
        }
        return result;
    }

}
