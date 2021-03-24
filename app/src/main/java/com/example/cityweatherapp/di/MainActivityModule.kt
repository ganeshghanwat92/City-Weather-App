package com.example.cityweatherapp.di

import com.example.cityweatherapp.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

@ContributesAndroidInjector(modules = [ FragmentsModule::class])
abstract fun mainActivity() : MainActivity


}
