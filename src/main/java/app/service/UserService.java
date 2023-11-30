package app.service;

import app.dao.UserDaoIml;
import app.model.DTO.UserDto;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TransferQueue;


@Service("userService")
public class UserService {
    @Autowired
    private final UserDaoIml userDao;
    public UserService(UserDaoIml userDao){
        this.userDao = userDao;
    }

    public boolean CheckLogin(String username, String password){
        if(username != null && !(username.isEmpty())){
            if(password != null && !(password.isEmpty())){
                return true;
            }
        }
        return false;
    }
    public boolean CheckSignUp(String username, String password, String name, String email){
        if(username == null || (username.isEmpty())) return false;
        if(password == null || (password.isEmpty())) return false;
        if(name == null || (name.isEmpty())) return false;
        if(email == null || (email.isEmpty())) return false;
        return true;
    }
    public UserDto Login(String username, String password){
        UserDto result = null;
        if (CheckLogin(username, password)){
            User db_user = userDao.getByUsername(username);
            if (db_user != null){
                if (db_user.getPassword().equals(password)){
                    result = new UserDto(db_user);
                }
            }
        }
        return result;
    }

    public UserDto SignUp(User user){
        UserDto result = null;
        if (CheckSignUp(user.getUsername(), user.getPassword(), user.getName(), user.getUser_email())){
            User db_user = userDao.getByUsername(user.getUsername());
            if (db_user != null){
                result = null;
            }
            else {

                if(userDao.add(user)){
                    user = userDao.getByUsername(user.getUsername());
                    result = new UserDto(user);

                }
            }
        }
        return result;
    }

    public UserDto getProfile(int user_id){
        UserDto result = null;
        User db_user = userDao.getById(user_id);
        if (db_user != null){
            result = new UserDto(db_user);
        }
        return result;
    }

    public List<UserDto> getByName(String name){
        List<UserDto> result = null;
        List<User> db_users = userDao.getByName(name);
        if (db_users != null){
            for (User one: db_users) {
                result.add(new UserDto(one));
            }
        }
        return result;
    }
}
