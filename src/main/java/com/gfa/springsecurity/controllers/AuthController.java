package com.gfa.springsecurity.controllers;

import com.gfa.springsecurity.models.UserInfo;
import com.gfa.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home(){
        return "index2";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(){
        return "register";
    }

    @PostMapping("/register")
    public String showRegistrationForm(@ModelAttribute UserInfo userInfo){
        userService.userRegister(userInfo);
        return "login";
    }

//    @PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password){
//        if (userService.authenticate(username, password)) return "homePage";
//        else return "login";
//    }


    @GetMapping("/homePage")
    public String homePage(){
        return "homePage";
    }
}
