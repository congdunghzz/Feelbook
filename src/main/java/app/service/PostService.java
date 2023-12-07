package app.service;

import app.dao.CommentDaoIml;
import app.dao.LikeDaoIml;
import app.dao.PostDaoIml;
import app.dao.UserDaoIml;
import app.model.DTO.PostDto;
import app.model.DTO.UserDto;
import app.model.Post;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service("postService")
public class PostService {
    @Autowired
    private PostDaoIml postDao;
    @Autowired
    private UserDaoIml userDao;
    @Autowired
    private LikeDaoIml likeDao;
    @Autowired
    private CommentDaoIml commentDao;
    @Autowired
    private ServletContext servletContext;

    public List<PostDto> getAll(){
        List<Post>postList = postDao.getAll();
        List<PostDto> result = TransferDto(postList);
        return result;
    }

    public PostDto getById (int id){
        PostDto result;
        Post db_post = postDao.getById(id);
        if (db_post != null){
            User db_user = userDao.getById(db_post.getUser_id());
            result = new PostDto(db_post, new UserDto(db_user));
        } else {
            result = null;
        }
        return result;
    }
    public List<PostDto> getByName(String name){
        List<Post> postListByName = postDao.getByName(name);
        if (postListByName == null) return null;
        List<PostDto> result = TransferDto(postListByName);
        return result;
    }

    public List<PostDto> getByUserId(int id){
        List<Post> db_PostList = postDao.getByUserId(id);
        if (db_PostList != null){
            List<PostDto> result = TransferDto(db_PostList);
            return result;
        }
        return null;
    }
    public int getNumOfPageBySearch(String name, int numOfItem){
        int num = postDao.getNum(name);

        int result = (int)(Math.ceil((double)(num) / numOfItem));

        return result;
    }
    public List<PostDto> getByNameWithPerPage(String name, int currentPage, int numOfItem){
        int start = (currentPage - 1) * numOfItem;
        List<Post> postListByName = postDao.getByNamePerPage(name, start, numOfItem);
        List<PostDto> result = TransferDto(postListByName);
        return result;
    }

    private List<PostDto> TransferDto(List<Post> postList){
        List<PostDto> result = new ArrayList<>();
        for (Post post: postList) {
            User db_User = userDao.getById(post.getUser_id());

                result.add(new PostDto(post, new UserDto(db_User)));

        }
        return result;
    }
    public String UpLoadImg(Post post, MultipartFile imgFile){

        String absolutePath = servletContext.getRealPath("/userResources/");
        String result = null;
        File ImgFile = new File(absolutePath + post.getUser_id() + "/post-img");

        if(!ImgFile.exists()){
            try{
                ImgFile.mkdirs();
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println(e.getCause());
                throw new IllegalArgumentException("Lỗi nhập file ");
            }
        }
        String filename = "post_" + post.getPost_id() + "_" + imgFile.getOriginalFilename();
        Path filePath = Paths.get(ImgFile.getPath() + "/" + filename);
        try {
            Files.copy(imgFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        result = "/Feelbook/user-resources/" + post.getUser_id() + "/post-img/" + filename;

        if(!postDao.setImg(post,result)) result = "error";

        return result;
    }

    public PostDto PostNews(Post post){
        PostDto postDto = null;
        boolean wasPosted = postDao.add(post);
        if (wasPosted){
            Post postedNew = postDao.getLatestPost();
            User db_user = userDao.getById(postedNew.getUser_id());
            postDto = new PostDto(postedNew, new UserDto(db_user));
        }
        return postDto;
    }

    public boolean DeletePost(int post_id){

        boolean result = false;
        commentDao.deleteAllFromPost(post_id);
        likeDao.deleteAllFromPost(post_id);
        if (postDao.Delete(post_id)) result = true;
        return result;
    }


    public PostDto EditPost(String content, int post_id){
        PostDto result = null;
        Post post = postDao.getById(post_id);
        post.setContent(content);
        boolean isUpdated = postDao.Update(post);
        if (isUpdated){
            UserDto user = new UserDto(userDao.getById(post.getUser_id()));
            result = new PostDto(postDao.getById(post_id), user);
        }
        return result;
    }


            // Like service
    public boolean LikePost(int post_id, int user_id){
        boolean result = false;
        Post post = postDao.getById(post_id);
        if ( post != null){
            if(likeDao.add(post_id,user_id)) {
                result = true;
            }
        }
        return result;
    }

    public boolean UnLike(int post_id, int user_id){
        boolean result = false;
        Post post = postDao.getById(post_id);
        if (post != null){
            if(likeDao.delete(post_id,user_id)) {
                result = true;
                System.out.println("Service = true");
            }
            System.out.println("Service = false");
        }
        return result;
    }

    public boolean deleteAllFromPost(int post_id){
        boolean result = false;
        Post post = postDao.getById(post_id);
        if (post != null){
            if(likeDao.deleteAllFromPost(post_id)) {
                result = true;
            }
        }
        return result;
    }

    public int getLikeNumOfPost(int post_id){
        return likeDao.getLikeOfPost(post_id);
    }

    public List<UserDto> getUserLikedPost (int post_id){
        List<User> user = likeDao.getAllLikedUser(post_id);
        List<UserDto> result = new ArrayList<>();
        for (User one : user) {
            result.add(new UserDto(one));
        }
        return result;
    }
}
