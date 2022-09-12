package com.example.blogproject.contollers;


import com.example.blogproject.models.Account;
import com.example.blogproject.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
    public class RegisterController {

        @Autowired
        private AccountService accountService; //need a reference to account service
        @GetMapping("/register") //when we use the get request to /register we are returning the register page
        public String getRegisterPage(Model model){ //have to make use of the UI model that spring framework injects
            Account account = new Account();
            model.addAttribute("account", account); //weve now added the account to the template where it says th:object=${account
            return "register";
        }

        @PostMapping("/register") //it will contain the data of the form
        public String registerNewUser(@ModelAttribute Account account) { //create a function that takes in a model attribute account which is the new account we want to create and use the account service to save that account and will return a redirect
            accountService.save(account);

            return "redirect:/"; //this :/ string will after we post our new account form data to the register end point will then redirect the user back to the home page or root or back to /
        }
    }

