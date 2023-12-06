package app.controller.api;

import app.model.DTO.UserDto;
import app.service.FriendShipService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/friend")
public class FriendShipApi {
    @Autowired
    private FriendShipService friendShipService;

    @PostMapping("send")
    @ResponseBody
    public Map<String, String> sendRequest(@RequestParam(value = "friend_id") Integer friend_id, HttpSession session) {
        Map<String, String> result = new HashMap<>();
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null) {
            result.put("message", "error");
            return result;
        }
        if (friendShipService.sendRequest(token.getUser_id(), friend_id)) {
            result.put("message", "successfully");
        } else result.put("message", "error");

        return result;
    }

    @PostMapping("reject")
    @ResponseBody
    public Map<String, String> rejectRequest(@RequestParam(value = "friend_id") Integer friend_id, HttpSession session) {
        Map<String, String> result = new HashMap<>();
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null) {
            result.put("message", "error");
            return result;
        }
        if (friendShipService.rejectRequest(token.getUser_id(), friend_id)) {
            result.put("message", "successfully");
        } else result.put("message", "error");

        return result;
    }

    @PostMapping("accept")
    @ResponseBody
    public Map<String, String> acceptRequest(@RequestParam(value = "friend_id") Integer friend_id, HttpSession session) {
        Map<String, String> result = new HashMap<>();
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null) {
            result.put("message", "error");
            return result;
        }
        if (friendShipService.acceptRequest(token.getUser_id(), friend_id)) {
            result.put("message", "successfully");
        } else result.put("message", "error");
        return result;
    }

    @PostMapping("unFriend")
    @ResponseBody
    public Map<String, String> unFriend(@RequestParam(value = "friend_id") Integer friend_id, HttpSession session) {
        Map<String, String> result = new HashMap<>();
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null) {
            result.put("message", "error");
            return result;
        }
        if (friendShipService.unFriend(token.getUser_id(), friend_id)) {
            result.put("message", "successfully");
        } else result.put("message", "error");

        return result;
    }

    @PostMapping("cancel")
    @ResponseBody
    public Map<String, String> Cancel(@RequestParam(value = "friend_id") Integer friend_id, HttpSession session) {
        Map<String, String> result = new HashMap<>();
        UserDto token = (UserDto) session.getAttribute("user");
        if (token == null) {
            result.put("message", "error");
            return result;
        }
        if (friendShipService.CancelRequest(token.getUser_id(), friend_id)) {
            result.put("message", "successfully");
        } else result.put("message", "error");

        return result;
    }
}
