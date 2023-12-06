package app.service;

import app.dao.FriendShipDaoIml;
import app.model.FriendShip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("friendShipService")
public class FriendShipService {
    @Autowired
    private FriendShipDaoIml friendShipDao;
    public boolean sendRequest(int user_id, int friend_id){
        if (user_id == friend_id){
            return false;
        }else {
            return friendShipDao.add(user_id,friend_id,0);
        }
    }

    public boolean acceptRequest(int user_id, int friend_id){
        boolean result = false;
        if (user_id != friend_id){
            if (friendShipDao.getStatus(friend_id,user_id) == 0){
                if (friendShipDao.setStatus(friend_id,user_id,1)){
                    result = friendShipDao.add(user_id,friend_id,1);
                }
            }
        }
        return result;
    }

    public boolean rejectRequest(int user_id, int friend_id){
        int db_status = friendShipDao.getStatus(friend_id,user_id);
        if (db_status == 0){
            return friendShipDao.delete(friend_id, user_id);
        }else {
            return false;
        }
    }
    public boolean unFriend(int user_id, int friend_id){
        int db_check = friendShipDao.getStatus(user_id,friend_id);
        if (db_check != 1 ) return false;
        if (user_id == friend_id){
            return false;
        }else {
            if (friendShipDao.delete(user_id,friend_id))
                return friendShipDao.delete(friend_id,user_id);
            else return false;
        }
    }

    public boolean CancelRequest(int user_id, int friend_id){
        int db_check = friendShipDao.getStatus(user_id,friend_id);
        if (db_check != 0 ) return false;
        if (user_id == friend_id){
            return false;
        }else {
            return friendShipDao.delete(user_id,friend_id);
        }
    }


    public int checkFriendStatus(int user_id, int friend_id){
        int result = -1;
        if (user_id == friend_id){
            return -2;
        }else {
            result =  friendShipDao.getStatus(user_id, friend_id);
            if (result == -1){
                if (friendShipDao.getStatus(friend_id, user_id) == 0) result = 2;
            }
            return result;
        }
    }
}
