package app.dao;

import app.model.Comment;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("commentDaoIml")
public class CommentDaoIml {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CommentDaoIml(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Comment> getByPost(int post_id) {
        String sql = "SELECT * FROM comment WHERE post_id = ? ";

        try {
            Object[] args = {post_id};
            List<Comment> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Comment.class), args);
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Comment getById(int comment_id) {
        String sql = "SELECT * FROM comment WHERE comment_id = ?";
        try {
            Comment result = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Comment.class), comment_id);

            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Comment> getByPostWithPaging(int post_id, int start, int perPage) {
        String sql = "SELECT * FROM comment WHERE post_id = ? ORDER BY create_at DESC LIMIT ?, ?";

        try {
            Object[] args = {post_id, start, perPage};
            List<Comment> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Comment.class), args);
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public int getCommentNumOfPost(int post_id){
        String sql = "SELECT COUNT(*) FROM comment WHERE post_id = ?";
        try{
            return jdbcTemplate.queryForObject(sql, Integer.class , post_id);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            return -1;
        }
    }

                        // Post

    public Comment add(Comment comment) {
        String sql = "INSERT INTO comment (content, create_at, user_id, post_id) VALUES (?,?,?,?)";
        String getCommentSql = "SELECT * FROM comment WHERE comment_id = (SELECT MAX(comment_id) FROM comment)";
        try {
            Object[] args = {comment.getContent(), comment.getCreate_at(), comment.getUser_id(), comment.getPost_id()};
            jdbcTemplate.update(sql, args);
            return jdbcTemplate.queryForObject(getCommentSql, new BeanPropertyRowMapper<>(Comment.class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

                        // Delete

    public boolean delete(int comment_id) {
        String sql = "DELETE FROM comment WHERE comment_id = ?";
        try {
            Object[] args = {comment_id};
            jdbcTemplate.update(sql, args);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean deleteAllFromPost(int post_id) {
        String sql = "DELETE FROM comment WHERE post_id = ?";
        try {
            Object[] args = {post_id};
            jdbcTemplate.update(sql, args);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

                        // Update
    public boolean edit(int comment_id,String content) {
        String sql = "UPDATE comment SET content = ? WHERE comment_id = ?";
        try {
            Object[] args = {content, comment_id};
            jdbcTemplate.update(sql, args);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
