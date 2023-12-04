package app.model;

import java.sql.Date;

public class User {
    private int user_id;
    private String name;
    private boolean gender;
    private Date dob;
    private String user_email;
    private String avatar;
    private String username;
    private String password;

    public User() {
    }

    public User(String name, boolean gender, Date dob, String user_email, String avatar, String username, String password) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.user_email = user_email;
        this.avatar = avatar;
        this.username = username;
        this.password = password;
    }

    public User(String name, boolean gender, Date dob, String user_email, String username, String password) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.user_email = user_email;
        this.username = username;
        this.password = password;
    }

    public User(String name,String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public User(int user_id, String name, boolean gender, Date dob, String user_email, String avatar, String username, String password) {
        this.user_id = user_id;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.user_email = user_email;
        this.avatar = avatar;
        this.username = username;
        this.password = password;
    }

    public User(int user_id, String name, boolean gender, Date dob, String user_email) {
        this.user_id = user_id;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.user_email = user_email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", dob=" + dob +
                ", user_email='" + user_email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
