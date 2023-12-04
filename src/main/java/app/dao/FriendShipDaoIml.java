package app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("friendShipDaoIml")
public class FriendShipDaoIml {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean add (int user_id, int friend_id, int status){
        String sql = "INSERT INTO friendship (user_id, friend_id, status) VALUES (?,?,?)";
        Object[] args = {user_id, friend_id, status};
        try {
            jdbcTemplate.update(sql,args);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete (int user_id, int friend_id){
        String sql = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";
        Object[] args = {user_id, friend_id};
        try {
            jdbcTemplate.update(sql,args);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean setStatus(int user_id, int friend_id, int status){
        String sql = "UPDATE friendship SET status = ? WHERE user_id = ? AND friend_id = ?";
        Object[] args = {status, user_id, friend_id};
        try {
            jdbcTemplate.update(sql,args);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }


}
