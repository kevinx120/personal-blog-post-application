package com.example.blogproject.contollers;

import com.example.blogproject.models.Post;
import com.example.blogproject.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private PostService postService; //using the newly created post service

    @GetMapping("/")    //home route url
    public String home(Model model) {   //will be making use of the ui model object that gets automatically injected into our controller handle in spring boot adn with that we will populate that model with all of our blog posts
        List<Post> posts = postService.getAll(); //do the same call in the seed runner data builder
        model.addAttribute("posts", posts); //as well i think this is the post list attribute posts in the first html thyme leaf i think//want to add that lists of posts into the model that get past out to the UI or template
        return "home"; //returning a string home and this home variable is what we need to define our template as
    }
}
