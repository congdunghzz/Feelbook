package app.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Post {
    private int post_id;
    private String content;
    private Timestamp create_at;


    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", content='" + content + '\'' +
                ", create_at=" + create_at +
                ", user_id=" + user_id +
                ", post_img='" + post_img + '\'' +
                '}';
    }

    private int user_id;


    private String post_img;



    public Post(int post_id, String content, Timestamp create_at, int user_id, String post_img) {
        this.post_id = post_id;
        this.content = content;
        this.create_at = create_at;
        this.user_id = user_id;
        this.post_img = post_img;
    }



    public Post(String content, Timestamp create_at, int user_id, String post_img) {
        this.content = content;
        this.create_at = create_at;
        this.user_id = user_id;
        this.post_img = post_img;
    }



    public String getPost_img() {
        return post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Timestamp create_at) {
        this.create_at = create_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Post(String content, Timestamp create_at, int user_id) {
        this.content = content;
        this.create_at = create_at;
        this.user_id = user_id;
    }

    public Post(int post_id, String content, Timestamp create_at, int user_id) {
        this.post_id = post_id;
        this.content = content;
        this.create_at = create_at;
        this.user_id = user_id;
    }

    public Post() {
    }
}
