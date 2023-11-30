package app.dao;

import app.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("postDaoIml")
public class PostDaoIml{
    @Autowired
    private final JdbcTemplate jdbcTemplate;
    public PostDaoIml(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Post> getAll(){
        String sql = "SELECT * FROM post ORDER BY create_at DESC;";

        try{
            List<Post> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class));
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());

            return null;
        }
    }


    public Post getById(int id){
        String sql = "SELECT * FROM post WHERE post_id = ?";
        Post result;
        try{
            result = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Post.class), id);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            result = null;
        }
        return result;
    }



    public List<Post> getByName(String name) {
        String sql = "SELECT * FROM post WHERE content LIKE ? ORDER BY create_at DESC";
        try{
            List<Post> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class), "%" + name + "%");
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            return null;
        }
    }

    public List<Post> getByUserId(int user_id) {
        String sql = "SELECT * FROM post WHERE user_id = ? ORDER BY create_at DESC";
        try{
            List<Post> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class), user_id);
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            return null;
        }
    }

    public List<Post> getByNamePerPage(String name, int currentPage, int numPerPage) {
        String sql = "SELECT * FROM post WHERE content LIKE ? ORDER BY create_at DESC LIMIT ?, ? ";
        try{
            Object[] args = {"%" + name + "%", currentPage, numPerPage};
            List<Post> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class), args);
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            return null;
        }
    }


    public boolean setImg(Post post, String path) {
        String sql = "UPDATE post SET post_img = ? WHERE post_id = ?";
        Object[] args = {path, post.getPost_id()};
        try{
            System.out.println(sql + " " + args[0] + " " + args[1]);
            jdbcTemplate.update(sql, args);
            return true;
        }catch (Exception e){
            System.err.println(e.getMessage());
            System.out.println(e.getCause());
            return false;
        }
    }


    public boolean add(Post post) {
        String sql = "INSERT INTO post (content, create_at, user_id, post_img) VALUES (?,?,?,?)";
        Object[] args = {post.getContent(), post.getCreate_at(), post.getUser_id(), post.getPost_img()};
        try{
            jdbcTemplate.update(sql,args);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }
    public Post getLatestPost(){
        String sql = "SELECT * FROM post WHERE post_id = (SELECT MAX(post_id) FROM post)";
        try{
            Post result = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Post.class));
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public int getNum(String name) {
        String sql = "SELECT COUNT(*) FROM post WHERE content LIKE ?";
        try{
            return jdbcTemplate.queryForObject(sql, Integer.class , "%" +name + "%");
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public boolean Delete(int post_id){
        String sql = "DELETE FROM post WHERE post_id = ?";
        try {
            jdbcTemplate.update(sql, post_id);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean Update(Post post){
        String sql = "UPDATE post SET content = ? WHERE post_id = ?";
        try{
            Object[] args = {post.getContent(), post.getPost_id()};
            jdbcTemplate.update(sql,args);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }


}
