package com.example.blogproject.contollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

        @GetMapping("/login") //this will return the login page
        public String getLoginPage(){
            return "login";
        }
    }

