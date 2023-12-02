package app.controller.api;

import app.model.Comment;
import app.model.DTO.CommentDto;
import app.model.DTO.UserDto;
import app.service.CommentService;
import app.service.PostService;
import app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/comment")
public class CommentApi {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @GetMapping
    @ResponseBody
    public List<CommentDto> getCommentsOfPost(@RequestParam("post_id") Long post_id, HttpSession session){
        List<CommentDto> result;
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null) return null;
        result = commentService.getCommentOfPost(post_id.intValue());
        return result;
    }

    @PostMapping("/create")
    @ResponseBody
    public CommentDto makeAComment(@RequestParam("content") String content,
                                   @RequestParam("post_id") Long post_id,
                                   HttpSession session){
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null) return null;
        if (content == null) return null;
        if (post_id == null) return null;
        Timestamp create_at = new Timestamp(System.currentTimeMillis());
        Comment comment = new Comment(content,create_at,token.getUser_id(),post_id.intValue());
        CommentDto result = commentService.addComment(comment);
        return result;
    }

    @PutMapping("/edit")
    @ResponseBody
    public CommentDto EditComment(@RequestParam("content") String content,
                                  @RequestParam("comment_id") Integer comment_id,
                                  HttpSession session, HttpServletResponse response) throws IOException {
        UserDto token = (UserDto) session.getAttribute("user");
        CommentDto comment = commentService.getCommentById(comment_id);
        if (token == null || token.getUser_id() != comment.getComment().getUser_id()){
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> message = new HashMap<>();
            message.put("message", "Not Authorized");
            response.getWriter().println(mapper.writeValueAsString(message));
            return null;
        }else {
            return commentService.editComment(comment_id,content);
        }


    }

    @DeleteMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteComment(@RequestParam("comment_id") Long comment_id, HttpSession session){
        Map<String, Object> result = new HashMap<>();
        UserDto token = (UserDto) session.getAttribute("user");
        CommentDto comment = commentService.getCommentById(comment_id.intValue());
        if (token == null || token.getUser_id() != comment.getComment().getUser_id()){
            result.put("message", "error");
        }else {
            commentService.deleteComment(comment_id.intValue());
            result.put("message", "successfully");
        }
        return result;
    }
}
