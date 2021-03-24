package com.example.cityweatherapp.wm

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface MyWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): ListenableWorker
}