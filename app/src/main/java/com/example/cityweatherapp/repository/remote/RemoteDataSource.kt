package com.example.cityweatherapp.repository.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: ApiService) : BaseDataSource() {

    suspend fun getWeatherDetails(apiKey : String, cityName : String) = getResult {
            apiService.getWeatherDetails(apiKey, cityName)
    }

}