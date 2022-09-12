package com.example.blogproject.config;


import com.example.blogproject.models.Account;
import com.example.blogproject.models.Authority;
import com.example.blogproject.models.Post;
import com.example.blogproject.repositories.AuthorityRepository;
import com.example.blogproject.services.AccountService;
import com.example.blogproject.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component //this tells the spring framework we want it too to work with this object and register it once it spins up all of the other objects within thew application
public class SeedData implements CommandLineRunner {
        @Autowired
        private PostService postService; //bring the post service we created

        @Autowired
        private AccountService accountService;

        @Autowired
        private AuthorityRepository authorityRepository;

        @Override //main method we have to override from the command line runner which is command run
        public void run(String... args) throws Exception {
            List<Post> posts = postService.getAll(); //grab a list of all posts from the database

            if (posts.size() == 0) { //if the size of our posts lists is zero then that means we have a brand new created database

                Authority user = new Authority();
                user.setName("ROLE_USER");
                authorityRepository.save(user);

                Authority admin = new Authority();
                admin.setName("ROLE_ADMIN");
                authorityRepository.save(admin);

                //These are being brought in new from account model stuff
                Account  account1 = new Account();
                Account  account2 = new Account();

                account1.setFirstName("user");
                account1.setLastName("user");
                account1.setEmail("user.user@domain.com");
                account1.setPassword("password");
                Set<Authority> authorities1 = new HashSet<>();
                authorityRepository.findById("ROLE_USER").ifPresent(authorities1::add);
                account1.setAuthorities(authorities1);
//a new hash set

                account2.setFirstName("admin");
                account2.setLastName("admin");
                account2.setEmail("admin.admin@domain.com");
                account2.setPassword("password");
                Set<Authority> authorities2 = new HashSet<>(); //a new hash set
                authorityRepository.findById("ROLE_USER").ifPresent(authorities2::add);
                authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities2::add);
                account2.setAuthorities(authorities2);

                accountService.save(account1);
                accountService.save(account2);

                Post post1 = new Post();
                post1.setTitle("About me");  //this is the display for the home blog
                post1.setBody("I'm a 20 years old with a Hispanic Latino background. I really like to go to concerts and enjoy activities that are active and maybe a bit risky." +
                        " I grew up and was born in Chicago IL, and moved to arizona for the retirement of my parents. My dream is too maybe move to Seattle Washington where the weather " +
                        "is Rainy and a beautiful landscape as well as a good environment for tech related companies to improve my skills as a developer");
                post1.setAccount(account1); //updated

                Post post2 = new Post();
                post2.setTitle("Kevin Personal Blog ");
                post2.setBody("These last 2 months i have worked hard and completed my full stack Java Course learning many programming languages that will help me better at web and software developing." +
                        " I had to do a lot of studying outside of class to even get a grasp of what i was learning but with practice from some outside resources and google i was able to overcome" +
                        " the transitions to new languages and understand a good bit of it." + " I still have much to learn but that's for the future and me to decide!");
                post2.setAccount(account2); //updated

                postService.save(post1);
                postService.save(post2);
            }
        }
    }

