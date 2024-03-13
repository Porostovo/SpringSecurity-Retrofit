package com.gfa.springsecurity.controllers;

import com.gfa.springsecurity.models.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class MovieController {

    private List<Movie> movies = new ArrayList<>();

    @GetMapping("/movies")
    public List<Movie> movies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Shrek"));
        movies.add(new Movie("Shrek 2"));
        return movies;
    }

    @PostMapping("/movies")
    public Movie createMovie(@RequestBody Movie movie){
        movies.add(movie);
        return movie;
    }
}
