package com.example.cityweatherapp.repository

import android.app.Application
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.cityweatherapp.repository.local.LocalDataSource
import com.example.cityweatherapp.repository.local.room.entity.CityWeather
import com.example.cityweatherapp.repository.remote.RemoteDataSource
import com.example.cityweatherapp.repository.remote.ResultWrapper
import com.example.cityweatherapp.wm.DeleteOldWeatherRecordsWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Repository @Inject constructor(val application: Application, val remoteDataSource: RemoteDataSource, val localDataSource: LocalDataSource) {

    val TAG = "Repository"

   suspend fun getWeatherDetails(apiKey : String, query : String) : ResultWrapper<CityWeather>? {

       var result : ResultWrapper<CityWeather>? = null

       val localData = localDataSource.getWeatherDetails(query)


       if (localData!=null)
           result =  ResultWrapper.Success(localData)
       else {
           val remoteResult = remoteDataSource.getWeatherDetails(apiKey, query)


           when (remoteResult) {
               is ResultWrapper.Success -> {

                   if (remoteResult.value.cod == "404"){

                       result = ResultWrapper.Error(remoteResult.value.message, Exception(remoteResult.value.message))

                   }else {

                       val cityWeather = CityWeather(
                           query, System.currentTimeMillis(),
                           remoteResult.value.coord.lat,
                           remoteResult.value.coord.lon,
                           remoteResult.value.weather.first().id,
                           remoteResult.value.weather.first().main,
                           remoteResult.value.weather.first().description,
                           remoteResult.value.weather.first().icon,
                           remoteResult.value.name,
                           remoteResult.value.main.temp,
                           remoteResult.value.main.feelsLike,
                           remoteResult.value.main.pressure,
                           remoteResult.value.main.humidity,
                           remoteResult.value.main.tempMin,
                           remoteResult.value.main.tempMax,
                       )

                       // save the result to local db
                       localDataSource.insertWeatherRecord(cityWeather)

                       // enqueue request to work manager to delete the record after 24 hr
                       scheduleDeleteWeatherRecordRequest(query)

                       result = ResultWrapper.Success(cityWeather)
                   }
               }
               is ResultWrapper.Error -> {

                   result = remoteResult

               }
           }
       }

       return result

    }

    private fun scheduleDeleteWeatherRecordRequest(query: String) {

        val oneTimeRequest =  OneTimeWorkRequest.Builder(DeleteOldWeatherRecordsWorker::class.java)
                .setInitialDelay(24, TimeUnit.HOURS)
                .setInputData(workDataOf(
                        "QUERY" to query
                ))
                .build()
        WorkManager.getInstance(application).enqueue(oneTimeRequest)
    }

    fun getWeatherData_Flow(apiKey : String, query: String) : Flow<ResultWrapper<CityWeather>> = flow {

        val localData = localDataSource.getWeatherDetails(query)

        Log.d(TAG, " localData $localData")

        if (localData!=null)
           emit(ResultWrapper.Success(localData))
        else {
            val remoteResult = remoteDataSource.getWeatherDetails(apiKey, query)

            Log.d(TAG, " remoteResult $remoteResult")

            when (remoteResult) {
                is ResultWrapper.Success -> {

                    if (remoteResult.value.cod == "404"){

                       emit(ResultWrapper.Error(remoteResult.value.message, Exception(remoteResult.value.message)))

                    }else {

                        val cityWeather = CityWeather(
                            query, System.currentTimeMillis(),
                            remoteResult.value.coord.lat,
                            remoteResult.value.coord.lon,
                            remoteResult.value.weather.first().id,
                            remoteResult.value.weather.first().main,
                            remoteResult.value.weather.first().description,
                            remoteResult.value.weather.first().icon,
                            remoteResult.value.name,
                            remoteResult.value.main.temp,
                            remoteResult.value.main.feelsLike,
                            remoteResult.value.main.pressure,
                            remoteResult.value.main.humidity,
                            remoteResult.value.main.tempMin,
                            remoteResult.value.main.tempMax,
                        )

                        // save the result to local db
                        localDataSource.insertWeatherRecord(cityWeather)

                        // enqueue request to work manager to delete the record after 24 hr
                        val oneTimeRequest =  OneTimeWorkRequest.Builder(DeleteOldWeatherRecordsWorker::class.java)
                            .setInitialDelay(24, TimeUnit.HOURS)
                            .setInputData(workDataOf(
                                "QUERY" to query
                            ))
                            .build()
                        WorkManager.getInstance(application).enqueue(oneTimeRequest)

                        emit(ResultWrapper.Success(cityWeather))
                    }
                }
                is ResultWrapper.Error -> {

                  //  emit(remoteResult)
                    emit(ResultWrapper.Error(remoteResult.message, Exception(remoteResult.message)))

                }
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun deleteOldWeatherRecords(currentMillis : Long){

        localDataSource.deleteOldWeatherRecords(currentMillis)

    }

    suspend fun deleteWeatherRecord(query : String){

        localDataSource.deleteWeatherRecord(query)

    }

}