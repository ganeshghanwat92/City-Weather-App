package com.example.cityweatherapp.repository.remote

sealed class ResultWrapper<out T>{

    data class Loading(val boolean: Boolean) : ResultWrapper<Nothing>()
    data class Success<out T : Any>(val value: T) : ResultWrapper<T>()
    data class Error(val message : String, val ex : Exception) : ResultWrapper<Nothing>()

    fun getData(): T? = if(this is Success) this.value else null
}