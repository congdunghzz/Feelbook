package app.controller.api;

import app.model.Comment;
import app.model.DTO.CommentDto;
import app.model.DTO.UserDto;
import app.service.CommentService;
import app.service.PostService;
import app.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/post")
    public List<CommentDto> getCommentsOfPost(@RequestParam("post_id") Long post_id){
        return null;
    }

    @PostMapping("/create")
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

    @PostMapping("/delete")
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
