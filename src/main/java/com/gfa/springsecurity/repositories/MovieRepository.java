package com.gfa.springsecurity.repositories;


import com.gfa.springsecurity.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Long> {
  //  List<Movie> findMoviesByOriginal_titleContainingIgnoreCase();
    Optional<Movie> findMovieByOriginalTitle(String title);

}

