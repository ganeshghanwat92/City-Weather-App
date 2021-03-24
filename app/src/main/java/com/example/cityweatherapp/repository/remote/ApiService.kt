package com.example.cityweatherapp.repository.remote

import com.example.cityweatherapp.repository.datamodel.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/data/2.5/weather")
     suspend fun getWeatherDetails(
        @Query("appid")appId : String,
        @Query("q")cityName : String) : Response<WeatherResponse>

}