package com.gfa.springsecurity.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.springsecurity.models.Movie;
import com.gfa.springsecurity.models.MovieDTO;
import com.gfa.springsecurity.models.MovieResultDTO;
import com.gfa.springsecurity.repositories.MovieRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;


@Service
@Component
public class MovieServiceImp implements MovieService {

    //private Retrofit retrofit;
    //private MovieApi movieApi;
    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImp(MovieRepository movieRepository) {

        this.movieRepository = movieRepository;

        //retrofit = RetrofitUtil.getRetrofitInstance();
        //movieApi = retrofit.create(MovieApi.class);
    }

    @Override
    public Call<Object> discoverMovies(String authorization, String query) {
        return null;
    }

    @Override
    public List<Movie> getListOfMoviesAndSave(String search) throws IOException {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build();

        System.out.println("TEST PRINT retrofit.baseUrl():" + retrofit.baseUrl());

        MovieService service = retrofit.create(MovieService.class);

        String authorizationToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzYjI4MzhkZGQ4ZDFjMWFlZmUwODFmMzdiYzc3Nz" +
                "E3MCIsInN1YiI6IjY1ZjA0ZWIwN2YwNTQwMDE2NDg1ZDZmMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.2s" +
                "O58bPqK8B43OdbVKhc8Widfo_Bm-FuEXWrKtQKok0";

        Call<Object> call = service.discoverMovies(authorizationToken, search);

        //take original title from recieved JSON format
        Object response = call.execute().body();

        // Convert the response object to a JSON string
        String jsonString = new Gson().toJson(response);

        ObjectMapper mapper = new ObjectMapper();
        try {
            MovieDTO movieDTO = mapper.readValue(jsonString, MovieDTO.class);

            for (MovieResultDTO movieResultDTO : movieDTO.getResults()) {
                Movie movie = new Movie();
                if (movieRepository.findMovieByOriginalTitle(movieResultDTO.getTitle()).isEmpty()) {
                    movie.setOriginalTitle(movieResultDTO.getTitle());
                    movie.setReleaseDate(movieResultDTO.getRelease_date());
                    movieRepository.save(movie);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieRepository.findMovieByOriginalTitle(search);
    }

    @Override
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> getMoviesBySearch(String search) {
        return movieRepository.findAll().stream()
                .filter(movie -> (movie.getOriginalTitle()).toLowerCase().contains(search.toLowerCase())).toList();
    }
}
