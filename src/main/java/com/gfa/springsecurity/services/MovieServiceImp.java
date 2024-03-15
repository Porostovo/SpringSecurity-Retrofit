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
    public Object getListOfMoviesAndSave(String query) throws IOException {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build();

        MovieService service = retrofit.create(MovieService.class);

        String authorizationToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzYjI4MzhkZGQ4ZDFjMWFlZmUwODFmMzdiYzc3NzE3MCIsInN1YiI6IjY1ZjA0ZWIwN2YwNTQwMDE2NDg1ZDZmMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.2sO58bPqK8B43OdbVKhc8Widfo_Bm-FuEXWrKtQKok0";


        Call<Object> call = service.discoverMovies(authorizationToken, query);


        //take original title from recieved JSON format
        Object response = call.execute().body();

        Gson gson = new Gson();
        String jsonString = gson.toJson(response);
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonArray resultsArray = jsonObject.getAsJsonArray("results");

        ObjectMapper mapper = new ObjectMapper();
        try {
            MovieDTO movieDTO = mapper.readValue(jsonString, MovieDTO.class);
                System.out.println(movieDTO.getResults().get(2).getOriginal_title());
                System.out.println("Page: " + movieDTO.getPage());
                System.out.println("Total pages: " + movieDTO.getTotal_pages());
                System.out.println("Total results: " + movieDTO.getTotal_results());
                System.out.println("Movies:");
            for (MovieResultDTO movieResultDTO : movieDTO.getResults()) {

                //System.out.println("Title: " + movieResultDTO.getTitle());
//                System.out.println("Overview: " + movieResultDTO.getOverview());
//                System.out.println("Release date: " + movieResultDTO.getRelease_date());
//                System.out.println("Popularity: " + movieResultDTO.getPopularity());
                Movie movie = new Movie();
                if (movieRepository.findMovieByOriginalTitle(movieResultDTO.getTitle()).isEmpty()){
                    movie.setOriginalTitle(movieResultDTO.getTitle());
                    movie.setReleaseDate(movieResultDTO.getRelease_date());
                    movieRepository.save(movie);
                }
            }

//            Movie movie = new Movie();
//            movie.
//            movie.setTitle(movieDTO.getTitle());
//            movie.setOverview(movieDTO.getOverview());
//            movie.setReleaseDate(movieDTO.getReleaseDate());

        } catch (Exception e) {
            e.printStackTrace();
        }

//        for (int i = 0; i < resultsArray.size(); i++) {
//            Movie movie = new Movie();
//            JsonObject resultObject = resultsArray.get(i).getAsJsonObject();
//            if (movieRepository.findMovieByOriginalTitle(resultObject.get("original_title").getAsString()).isEmpty()){
//
//                movie.setOriginalTitle(resultObject.get("original_title").getAsString());
//                movie.setReleaseDate(resultObject.get("release_date").getAsString());
//                // movie.setOverview(resultObject.get("overview").getAsString());
//
//                movieRepository.save(movie);
//                System.out.println("data saved to database with name: "  + query);
//            }
//        }
        return response;
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
