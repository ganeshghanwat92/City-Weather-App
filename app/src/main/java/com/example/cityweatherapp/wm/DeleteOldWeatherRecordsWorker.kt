package com.example.cityweatherapp.wm

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.cityweatherapp.repository.Repository
import com.example.cityweatherapp.repository.local.LocalDataSource
import javax.inject.Inject
import javax.inject.Provider

class DeleteOldWeatherRecordsWorker @Inject constructor(val repository: Repository, context : Context, workerParams : WorkerParameters) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        Log.d("DeleteOldWeatherWorker", " do Work repository = $repository")

        val query =   inputData.getString("QUERY")

        Log.d("DeleteOldWeatherWorker", " query = $query")

        repository.deleteWeatherRecord(query!!)

      //  repository.deleteOldWeatherRecords(System.currentTimeMillis())

        return Result.success()
    }

    class Factory @Inject constructor(private val repositoryProvider: Provider<Repository>) : MyWorkerFactory{

        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {

            return DeleteOldWeatherRecordsWorker(
                repositoryProvider.get(),
                appContext,
                params
            )

        }


    }
}