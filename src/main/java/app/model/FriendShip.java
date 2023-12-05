package app.model;

public class FriendShip {
    private int user_id;
    private int friend_id;
    private int status;

    public FriendShip(int user_id, int friend_id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
    }

    public FriendShip(int user_id, int friend_id, int status) {
        this.user_id = user_id;
        this.friend_id = friend_id;
        this.status = status;
    }

    public FriendShip() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
