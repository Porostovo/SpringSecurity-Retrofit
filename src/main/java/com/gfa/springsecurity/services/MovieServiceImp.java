package com.gfa.springsecurity.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.springsecurity.models.Movie;
import com.gfa.springsecurity.models.MovieDTO;
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


//    @Override
//    public void getMovies(Callback<List<Movie>> callback) {
//        Call<List<Movie>> getMovies = movieApi.getMovies("Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzYjI4MzhkZGQ4ZDFjMWFlZmUwODFmMzdiYzc3NzE3MCIsInN1YiI6IjY1ZjA0ZWIwN2YwNTQwMDE2NDg1ZDZmMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.2sO58bPqK8B43OdbVKhc8Widfo_Bm-FuEXWrKtQKok0");
////       try {
////           Response<List<Movie>> response = getMovies.execute();
////           if (response.isSuccessful() && response.body() != null) {
////               movieList = response.body();
////           }
////       } catch (Exception e) {
////           throw new RuntimeException(e)   }
////       return movieList;
//        //  }
//
//        getMovies.enqueue(new Callback<List<Movie>>() {
//            @Override
//            public void onResponse(Call<List<Movie>> call, retrofit2.Response<List<Movie>> response) {
//                if (response.isSuccessful()) {
//                    List<Movie> listOfMovies = response.body();
//                    callback.onResponse(call, response);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Movie>> call, Throwable t) {
//                callback.onFailure(call, t);
//            }
//        });
//    }

    @Override
    public Object getListOfMoviesAndSave() throws IOException {


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build();

        MovieService service = retrofit.create(MovieService.class);

        String authorizationToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzYjI4MzhkZGQ4ZDFjMWFlZmUwODFmMzdiYzc3NzE3MCIsInN1YiI6IjY1ZjA0ZWIwN2YwNTQwMDE2NDg1ZDZmMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.2sO58bPqK8B43OdbVKhc8Widfo_Bm-FuEXWrKtQKok0";
        String query = "duna"; // This is the value you want to use for the query parameter
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

//            Movie movie = new Movie();
//            movie.
//            movie.setTitle(movieDTO.getTitle());
//            movie.setOverview(movieDTO.getOverview());
//            movie.setReleaseDate(movieDTO.getReleaseDate());

        } catch (Exception e) {
            e.printStackTrace();
        }




        for (int i = 0; i < resultsArray.size(); i++) {
            Movie movie = new Movie();
            JsonObject resultObject = resultsArray.get(i).getAsJsonObject();
            String original_title = resultObject.get("original_title").getAsString();
            movie.setOriginal_title(original_title);
            movieRepository.save(movie);
        }
        return response;
    }
}
