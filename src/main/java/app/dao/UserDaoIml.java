package app.dao;

import app.model.DTO.UserDto;
import app.model.Post;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDaoIml")
public class UserDaoIml {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public UserDaoIml(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getByUsername(String username){
        String sql = "SELECT * FROM user WHERE username = ?";
        try{
            User result = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public User getById(int user_id){
        String sql = "SELECT * FROM user WHERE user_id = ?";
        try{
            User result = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), user_id);
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<User> getByName (String name){
        String sql = "SELECT * FROM user WHERE name LIKE ?";
        try{
            List<User> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), "%"+name+"%");
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }


    public boolean add(User user){
        String sql = "INSERT INTO user (name, gender, dob, user_email, username, password) VALUES (?,?,?,?,?,?)";
        try{
            Object[] args = {user.getName(), user.isGender(), user.getDob(), user.getUser_email(), user.getUsername(), user.getPassword()};
            jdbcTemplate.update(sql, args);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    public boolean setAvatar(int user_id, String path) {
        String sql = "UPDATE user SET avatar = ? WHERE user_id = ?";
        Object[] args = {path, user_id};
        try{
            jdbcTemplate.update(sql, args);
            return true;
        }catch (Exception e){
            System.err.println(e.getMessage());
            System.out.println(e.getCause());
            return false;
        }
    }

    public boolean editInformation(UserDto user){
        String sql = "UPDATE user SET name = ?, gender = ?, dob = ?, user_email = ? WHERE user_id = ?";
        Object[] args = {user.getName(), user.isGender(), user.getDob(),user.getUser_email(), user.getUser_id()};
        try {
            int i = jdbcTemplate.update(sql, args);
            System.out.println("DAO:  amount of record updated is " + i);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<User> getFriends (int user_id){
        String sql = "SELECT * FROM user WHERE user_id IN " +
                "(SELECT friend_id FROM friendship WHERE user_id = ? AND friendship_status = 1)";
        try{
            List<User> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), user_id);
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    public List<User> getFriendsLimit (int user_id){
        String sql = "SELECT * FROM user WHERE user_id IN " +
                "(SELECT friend_id FROM friendship WHERE user_id = ? AND friendship_status = 1) LIMIT 0, 6";
        try{
            List<User> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), user_id);
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }
    public List<User> getFriendsWaitingForRequest (int user_id){
        String sql = "SELECT * FROM user WHERE user_id IN " +
                "(SELECT user_id FROM friendship WHERE friend_id = ? AND friendship_status = 0)";
        try{
            List<User> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), user_id);
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

}
