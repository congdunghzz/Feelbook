package app.dao;

import app.model.Post;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Repository("likeDaoIml")
public class LikeDaoIml {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public LikeDaoIml(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public int getLikeOfPost(int post_id){
        String sql = "SELECT COUNT(*) FROM post_like WHERE post_id = ?";
        try{
            return jdbcTemplate.queryForObject(sql, Integer.class , post_id);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public boolean add(int post_id, int user_id){
        String sqlLike = "INSERT INTO post_like (post_id, user_id) VALUES (?, ?)";
        String sqlSetNum;
        Object[] args = {post_id, user_id};
        try{
            jdbcTemplate.update(sqlLike, args);

            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(int post_id, int user_id){
        String sql = "DELETE FROM post_like WHERE post_id = ? and user_id = ?";
        Object[] args = {post_id, user_id};
        try{
            jdbcTemplate.update(sql, args);
            return true;
        }catch (Exception e){
            System.out.println("error from unlike DAO "+e.getMessage());
            return false;
        }
    }

    public boolean deleteAllFromPost(int post_id){
        String sql = "DELETE FROM post_like WHERE post_id = ?";
        Object[] args = {post_id};
        try{
            jdbcTemplate.update(sql, args);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<User> getAllLikedUser(int post_id){
        String sql = "SELECT * FROM user WHERE user_id IN (SELECT user_id FROM post_like WHERE post_id = ?)";
        try{
            List<User> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), post_id);
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

}
