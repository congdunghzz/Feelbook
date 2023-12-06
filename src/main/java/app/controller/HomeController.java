package app.controller;

import app.model.DTO.PostDto;
import app.model.DTO.UserDto;
import app.model.Post;
import app.service.PostService;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping(value = "/home")
public class HomeController {
    @Autowired
    private PostService service;
    public HomeController(PostService service){
        this.service = service;
    }
    @GetMapping
    public String getHome(HttpSession session, Model model){
        UserDto user = (UserDto) session.getAttribute("user");
        String result = "";
        if(user != null){
            model.addAttribute("user", user);
            result = "homepage.jsp";
        }else {
            result = "redirect:/login";
        }

        return result;
    }

    @GetMapping("/search")
    public String getHomeSearch(@RequestParam(value = "key") String key,
                                @RequestParam(value = "page", required = false) Long page,
                                Model model){

        if (page == null) page = 1L;
        int numOfItem =2;
        List<PostDto> postList = service.getByNameWithPerPage(key, page.intValue(), numOfItem);
        int numOfPage = service.getNumOfPageBySearch(key, numOfItem);
        model.addAttribute("postList", postList);
        model.addAttribute("numOfPage",numOfPage);
        model.addAttribute("searchKey", key);

        return "HomeSearch.jsp";
    }



}
