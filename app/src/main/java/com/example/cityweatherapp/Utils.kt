package com.example.cityweatherapp

object Utils {

    fun buildWeatherIconUrl(iconId : String) : String{

       return  "https://openweathermap.org/img/wn/$iconId@2x.png"

    }

}