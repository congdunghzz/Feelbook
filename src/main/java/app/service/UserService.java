package app.service;

import app.dao.UserDaoIml;
import app.model.DTO.UserDto;
import app.model.User;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TransferQueue;


@Service("userService")
public class UserService {
    @Autowired
    private final UserDaoIml userDao;
    @Autowired
    private ServletContext servletContext;
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
                    userDao.setAvatar(result.getUser_id(), "https://antimatter.vn/wp-content/uploads/2022/07/avatar-trang-fb-nam.jpeg");
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

    public static String getFileExtension(String path) {
        if (path == null) {
            return "";
        }

        int dotIndex = path.lastIndexOf('.');
        if (dotIndex > 0) {
            return path.substring(dotIndex);
        } else {
            return "";
        }
    }

    public String setAvatar(int user_id, MultipartFile inputFile){
        String result = null;
        String absolutePath = servletContext.getRealPath("/userResources/");
        File ImgFile = new File(absolutePath + user_id + "/Avatar");
        if (!ImgFile.exists()) {
            try {
                ImgFile.mkdirs();
                System.out.println("Created folder is name:" + ImgFile.getPath());
            }catch (Exception e){
                System.out.println(e.getMessage());
                throw new IllegalArgumentException ("lỗi nhập file");
            }
        }

        String fileExtension = getFileExtension(inputFile.getOriginalFilename());
        String filename = "avatar_" + user_id +"_"+ UUID.randomUUID().toString()+ fileExtension;
        Path path = Paths.get(ImgFile.getPath() + "/" + filename);

        try {
            Files.copy(inputFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        result = "/Feelbook/user-resources/" + user_id + "/Avatar/" + filename;
        if (!userDao.setAvatar(user_id,result)) result = "error";
        return result;
    }

    public String updateInfor(UserDto user){
         UserDto db_user = getProfile(user.getUser_id());
         if (db_user == null){
             return "Error, you ara not identified";
         }else {
             if(userDao.editInformation(user))
                return "Your information has been updated successfully";
             return "Error, you ara not identified";
         }
    }


    public List<UserDto> getFriends(int user_id){
        List<UserDto> result = new ArrayList<>();
        List<User> db_users = userDao.getFriends(user_id);
        if (db_users == null) return null;
        for (User one: db_users) {
            result.add(new UserDto(one));
        }
        return result;
    }

    public List<UserDto> getFriendsWaitingForRequest(int user_id){
        List<UserDto> result = new ArrayList<>();
        List<User> db_users = userDao.getFriendsWaitingForRequest(user_id);
        if (db_users == null) return null;
        for (User one: db_users) {
            result.add(new UserDto(one));
        }
        return result;
    }

}
