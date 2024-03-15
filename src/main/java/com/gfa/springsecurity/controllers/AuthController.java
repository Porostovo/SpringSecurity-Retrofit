package com.gfa.springsecurity.controllers;

import com.gfa.springsecurity.models.UserInfo;
import com.gfa.springsecurity.services.MovieService;
import com.gfa.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final UserService userService;
    private final MovieService movieService;

    @Autowired
    public AuthController(UserService userService, MovieService movieService) {
        this.userService = userService;
        this.movieService = movieService;
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model, @RequestParam(required = false) String error){
        return "register";
    }

    @PostMapping("/register")
    public String showRegistrationForm(@ModelAttribute UserInfo userInfo){
        try {
            userService.userRegister(userInfo);
        }catch (Exception e){
            System.out.println("ex" + e);
            return "redirect:/register?error=true"; // throw new BadCredentialsException("user with this username already exists");
        }
        return "redirect:/register?success=true";
    }

}

