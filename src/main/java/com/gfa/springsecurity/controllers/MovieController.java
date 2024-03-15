package com.gfa.springsecurity.controllers;

import com.gfa.springsecurity.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movieList")
    public String movieList(Model model, @RequestParam(required = false, defaultValue = "#$%^&**(") String search){
        // we should implement if there is no search word, default value is not a good solution..
        // also if we change user, the list remains from previous search
        model.addAttribute("movies", movieService.getMoviesBySearch(search));
        return "movies";
    }

    @PostMapping("/search")
    public String searchMovies(Model model, @RequestParam String search) throws IOException {
        model.addAttribute("movies",  movieService.getListOfMoviesAndSave(search));
        return "redirect:/movieList?search=" + search;
    }
}
