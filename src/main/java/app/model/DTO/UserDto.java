package app.model.DTO;

import app.model.User;

import java.sql.Date;
import java.util.Objects;

public class UserDto {
    private int user_id;
    private String name;
    private boolean gender;
    private Date dob;
    private String user_email;
    private String avatar;
    private String username;

    public UserDto() {
    }
    public UserDto(User user){
        this.user_id = user.getUser_id();
        this.name = user.getName();
        this.gender = user.isGender();
        this.dob = user.getDob();
        this.user_email = user.getUser_email();
        this.avatar = user.getAvatar();
        this.username = user.getUsername();
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", dob=" + dob +
                ", user_email='" + user_email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return user_id == userDto.user_id && gender == userDto.gender && Objects.equals(name, userDto.name) && Objects.equals(dob, userDto.dob) && Objects.equals(user_email, userDto.user_email) && Objects.equals(username, userDto.username);
    }

}
