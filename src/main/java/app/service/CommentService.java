package app.service;

import app.dao.CommentDaoIml;
import app.dao.UserDaoIml;
import app.model.Comment;
import app.model.DTO.CommentDto;
import app.model.DTO.UserDto;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("commentService")
public class CommentService {
    @Autowired
    private CommentDaoIml commentDao;
    @Autowired
    private UserDaoIml userDao;

    private List<CommentDto> TransferDto(List<Comment> commentList){
        List<CommentDto> result = new ArrayList<>();
        for (Comment item : commentList) {
            result.add(new CommentDto(item, new UserDto(userDao.getById(item.getUser_id()))));
        }
        return result;
    }

    public List<CommentDto> getCommentOfPost(int post_id){
        List<CommentDto> result = null;
        List<Comment> commentList = commentDao.getByPost(post_id);
        if(commentList != null){
            result = TransferDto(commentList);
        }
        return result;
    }

    public CommentDto getCommentById( int comment_id){
        Comment db_comment = commentDao.getById(comment_id);
        if (db_comment == null) return null;
        return new CommentDto(db_comment, new UserDto(userDao.getById(db_comment.getUser_id())));
    }

    public List<CommentDto> PagingCommentOfPost(int post_id, int currentPage, int numPerPage){
        List<CommentDto> result = null;
        List<Comment> commentList = commentDao.getByPostWithPaging(post_id,currentPage,numPerPage);
        if (commentList != null){
            result = TransferDto(commentList);
        }
        return result;
    }

    public int getNumOfCommentByPost(int post_id){
        return commentDao.getCommentNumOfPost(post_id);
    }

    public CommentDto addComment(Comment comment){
        Comment db_comment = commentDao.add(comment);
        if (db_comment != null){
            return new CommentDto(db_comment,new UserDto(userDao.getById(db_comment.getUser_id())));
        }
        return null;
    }
    public CommentDto editComment(int comment_id, String content){
        CommentDto result = null;
        boolean wasEdit = commentDao.edit(comment_id, content);
        if (wasEdit){
            Comment db_comment = commentDao.getById(comment_id);
            User db_user = userDao.getById(db_comment.getUser_id());
            result = new CommentDto(db_comment, new UserDto(db_user));
        }
        return result;
    }

    public boolean deleteComment(int comment_id){
        return commentDao.delete(comment_id);
    }
    public boolean deleteAllFromPost(int post_id){

        return commentDao.deleteAllFromPost(post_id);
    }

}
