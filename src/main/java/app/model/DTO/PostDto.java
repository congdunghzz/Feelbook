package app.model.DTO;

import app.model.Comment;
import app.model.Post;
import app.model.User;

import java.util.List;

public class PostDto {
    private Post post;
    private UserDto user;

    public PostDto() {
    }

    public PostDto(Post post, UserDto user) {
        this.post = post;
        this.user = user;

    }


    public PostDto(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
