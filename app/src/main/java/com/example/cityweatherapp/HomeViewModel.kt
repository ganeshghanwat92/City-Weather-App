package com.example.cityweatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityweatherapp.repository.Repository
import com.example.cityweatherapp.repository.local.room.entity.CityWeather
import com.example.cityweatherapp.repository.remote.ResultWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(val repository: Repository, val dispatcherProvider: CoroutinesDispatcherProvider) : ViewModel() {

    private val _weatherLiveData  = MutableLiveData<ResultWrapper<CityWeather>?>()
    val weatherLiveData : MutableLiveData<ResultWrapper<CityWeather>?> = _weatherLiveData

    fun fetchWeatherDetails(apiKey : String, cityName : String){

        _weatherLiveData.value = ResultWrapper.Loading(true)

        CoroutineScope(dispatcherProvider.io).launch {

           val result =  repository.getWeatherDetails(apiKey, cityName)

            _weatherLiveData.postValue(result)

        }
    }

    // using kotlin flow
    fun fetchWeatherDetailsFlow(apiKey: String,cityName : String){

        _weatherLiveData.value = ResultWrapper.Loading(true)

        viewModelScope.launch(dispatcherProvider.main) {

            repository.getWeatherData_Flow(apiKey, cityName).collect{

                _weatherLiveData.value = it

            }

        }

    }
}