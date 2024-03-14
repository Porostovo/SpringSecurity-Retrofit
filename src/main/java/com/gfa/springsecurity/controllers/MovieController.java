package com.gfa.springsecurity.controllers;

import com.gfa.springsecurity.models.Movie;
import com.gfa.springsecurity.services.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class MovieController {
    private final MovieService movieService;


    private List<Movie> movies = new ArrayList<>();

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

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

    @GetMapping("/movies2")
    public Object listOfMovies() throws IOException {
        return movieService.getListOfMoviesAndSave();
    }
}
