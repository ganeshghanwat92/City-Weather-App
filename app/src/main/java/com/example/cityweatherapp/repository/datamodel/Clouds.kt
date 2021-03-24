package com.example.cityweatherapp.repository.datamodel


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)