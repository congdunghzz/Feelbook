package app.model;

import java.sql.Timestamp;

public class Comment {
    private int comment_id;
    private String content;
    private Timestamp create_at;
    private int user_id;
    private int post_id;

    public Comment() {
    }

    public Comment(String content, Timestamp create_at, int user_id, int post_id) {
        this.content = content;
        this.create_at = create_at;
        this.user_id = user_id;
        this.post_id = post_id;
    }

    public Comment(int comment_id, String content, Timestamp create_at, int user_id, int post_id) {
        this.comment_id = comment_id;
        this.content = content;
        this.create_at = create_at;
        this.user_id = user_id;
        this.post_id = post_id;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
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

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
}
