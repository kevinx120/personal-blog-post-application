package com.example.blogproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity //this annotation includes more annotation if you hover over it---configuration is one of the important ones where when spring boot starts up just with our seed data it will pull in web security config object
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) //this will come into play more later on
public class WebSecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //i was getting an error of "There is no PasswordEncoder mapped for the id "null" but i think it was missing this
    }
    //also i think this is what its using to match up the passwords which not pain text to password but instead encrypted
/*
    private static final String[] WHITELIST = { //made and array
            "/register",    //i think this is getting what we want to then be able to access
            //"/login", this is getting taken care of in the http down below so im going to comment it
            "/h2-console/*",
            "/css/**.css/",
            "/"

    };

 */
    @Bean   //setup a java bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {  //build are http url's that were supporting or we want to setup to listen for in our application
        http
                .authorizeRequests()    //helper method
                .antMatchers("/register", "/h2-console/*", "/resources/**","/css/*.css", "/").permitAll() //this had whitelist in it before and thats it and put but the cpmment final thing open //any request to those url's or endpoints in that whitelist will be permitted for anyone to use
                .antMatchers(HttpMethod.GET,"/posts/*").permitAll() //this means this is another helper into antMatchers where any get request matching this /posts/* which means anything after /posts like for example /posts/1 or /posts/8 we want to permit anyone who comes to be able to view those
                .anyRequest().authenticated();

        //setup a login handler so spring security handles your authentication an authorization per missions itr basically creates a set of defaults interfaces that we have to remember we have to extend some of these configure and extends some of these interfaces in order to tell spring security howe to handle authentication and authorization in the apallicatioon
        http
                .formLogin() //because we have a form to login with
                .loginPage("/login")
                .loginProcessingUrl("/login") //the login form will submit too is /login
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)//after we login we want to redirect to the home page so the default access url is gonna be /
                .failureUrl("/login?error")//i had an multiple errors here when i forgot to put the "/" before login on mutiple ofn this page and it would not render
                .permitAll() //allow everyone to those url's in order to do the propper processing
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") //back to the login page once we login with the logout parameter that way we know its happening
                .and()
                .httpBasic();
    //don't need to define in login controller
        // need to add for h2-console access
        //TODO When you move away from h2 console you can remove these
        http.csrf().disable(); //we have to call on csrf ( Cross-site request forgery :is a type of malicious exploit of a website where unauthorized commands are submitted from a user that the web application trust)
        http.headers().frameOptions().disable();  // some of the headers that get http headers that get sent back by default in spring security we have to disable those because they interfere with the headers that h2 console uses its strange but it is what it is

        return http.build();
    }
}
