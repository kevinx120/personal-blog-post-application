package com.example.blogproject.services;

import com.example.blogproject.models.Post;
import com.example.blogproject.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;  //pull in post repository interface

    public Optional<Post> getById(Long id) {    //get a single blog post with a given iud when we click onto one single post and return that id i think
        return postRepository.findById(id);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }//this method is to retrieve all blog posts in the database

    public Post save(Post post) {
        if (post.getId() == null) {
            post.setCreatedAt(LocalDateTime.now());
        }
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }//basic handler for saving a post if this method gets a post object it will perist it into the back

    public void delete(Post post) { postRepository.delete(post); }
}
// got the service to interface with repository
//we got the repository to interface with the database
//and the model that interfaces with the service directly