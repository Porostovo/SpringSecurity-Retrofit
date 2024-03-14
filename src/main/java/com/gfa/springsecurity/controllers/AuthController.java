package com.gfa.springsecurity.controllers;

import com.gfa.springsecurity.models.UserInfo;
import com.gfa.springsecurity.services.MovieService;
import com.gfa.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

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
        return "index2";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("error", "user with this username already exists");
        return "register";
    }

    @PostMapping("/register")
    public String showRegistrationForm(@ModelAttribute UserInfo userInfo){
        try {
            userService.userRegister(userInfo);
        }catch (Exception e){
            return "redirect:/register"; // throw new BadCredentialsException("user with this username already exists");
        }
        return "login";
    }

    @GetMapping("/homePage")
    public String homePage(){
        return "homePage";
    }

    @GetMapping("/movieList")
    public String movieList(Model model, @RequestParam (required = false) String search){
        System.out.println(search);
        model.addAttribute("movies", movieService.getMoviesBySearch(search));
        return "movies";
    }

    @PostMapping("/search")
    public String searchMovies(Model model, @RequestParam String search) throws IOException {
        model.addAttribute("movies",  movieService.getListOfMoviesAndSave(search));
        return "redirect:/movieList?search=" + search;
    }
}

