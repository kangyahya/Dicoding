package com.rtikcirebonkota.thecataloguemovie.api;

import com.rtikcirebonkota.thecataloguemovie.model.ResponseMovie;
import com.rtikcirebonkota.thecataloguemovie.model.ResponseTv;
import com.rtikcirebonkota.thecataloguemovie.model.TvResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @GET("movie/now_playing")
    Call<ResponseMovie> getMovie(@Query("api_key") String apiKey, @Query("language") String language);
    @GET("movie/upcoming")
    Call<ResponseMovie> getUpcomingMovie(@Query("api_key") String apiKey);
    @GET("discover/movie")
    Call<ResponseMovie> getUpcoming(@Query("api_key") String apiKey, @Query("primary_release_date.gte") String releaseGte, @Query("primary_release_date.lte") String releaseLte);
    @GET("search/movie/")
    Call<ResponseMovie> getMovieBySearch(@Query("query") String q, @Query("api_key") String apiKey);


    @GET("tv/popular ")
    Call<ResponseTv> getTv(@Query("api_key") String apiKey);
    @GET("search/tv/")
    Call<ResponseTv> getTvBySearch(@Query("query") String q, @Query("api_key") String apiKey);
}
