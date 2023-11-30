package app.model.DTO;

import app.model.Comment;


public class CommentDto {
    Comment comment;
    UserDto user;

    public CommentDto(Comment comment, UserDto user) {
        this.user = user;
        this.comment = comment;
    }


    public CommentDto() {
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
