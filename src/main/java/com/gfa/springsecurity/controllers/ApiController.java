package com.gfa.springsecurity.controllers;

import com.gfa.springsecurity.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ApiController {
    private final MovieService movieService;


    @Autowired
    public ApiController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/moviesAPI")
    public ResponseEntity<?> listOfMovies(@RequestParam (required = false) String search){
        if (search == null) {
            return ResponseEntity.status(200).body(movieService.getMovies());
        } else return ResponseEntity.status(200).body(movieService.getMoviesBySearch(search));
    }
}
