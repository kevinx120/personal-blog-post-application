package com.example.blogproject.contollers;

import com.example.blogproject.models.Account;
import com.example.blogproject.models.Post;
import com.example.blogproject.services.AccountService;
import com.example.blogproject.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller

public class PostController {

    @Autowired //look up the post service
    private PostService postService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/posts/{id}") //get mapping of a singular post
    public String getPost(@PathVariable Long id, Model model) { //using a path variable spring boot will try to match the path that being requested and strip it out to match what we want in our function which in our case we want to use the path variable to pull out the id the post we are after and like any controller we have a model injected to use to populate the view for the browser
        //find the post by id
        Optional<Post> optionalPost = postService.getById(id);
        //if post exists, then shove it into the model
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post"; //view template for our individual posts
        } else {
            return "404"; //if we find something else
        }
    }

    @GetMapping("/posts/new")   //when we use the /posts/new
    public String createNewPost(Model model) {  //where gonna be attaching account of that first user account
        Optional<Account> optionalAccount = accountService.findByEmail("user.user@domain.com"); //we want to search use the account service to search the account able to anything that matches the email that will give in ot it
        if (optionalAccount.isPresent()) {
            Post post = new Post(); //brand new post
            post.setAccount(optionalAccount.get());
            model.addAttribute("post", post);//add attribute to the model
            return "post_new";
        } else {                 //if we dont find
            return "404";
        }
    }

    @PostMapping("/posts/new")
    //i had an error here with the mapping where i had a period instead of a / at the beginning of /posts
    public String saveNewPost(@ModelAttribute Post post) {
        postService.save(post); //using the post service to save it
        return "redirect:/posts/" + post.getId(); //redirect to the actual newly created post itself
    }

    @GetMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    //security helper annotation that it makes sure the request to this end point or post/id/edit is in fact authenticated
    public String getPostForEdit(@PathVariable Long id, Model model) {  //read path varaible of id make use of model in sprinf framework

        // find post by id
        Optional<Post> optionalPost = postService.getById(id);  //grab the post and make sure it exist
        // if post exist put it in model
        if (optionalPost.isPresent()) { //if its present in  the data base then retirvce it
            Post post = optionalPost.get();
            model.addAttribute("post", post);  //then add the attribute  to the model
            return "post_edit"; //then post view template
        } else {
            return "404";
        }
    }

    @PostMapping("/posts/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable Long id, Post post, BindingResult result, Model model) {

        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();

            existingPost.setTitle(post.getTitle());
            existingPost.setBody(post.getBody());

            postService.save(existingPost);
        }

        return "redirect:/posts/" + post.getId();   //redirect the /posts/ new update dpost id
    }

    @GetMapping("/posts/{id}/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deletePost(@PathVariable Long id) {

        // find post by id
        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            postService.delete(post);
            return "redirect:/";
        } else {
            return "404";
        }
    }
}