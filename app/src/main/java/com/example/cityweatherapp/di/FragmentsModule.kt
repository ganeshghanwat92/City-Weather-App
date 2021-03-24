package com.example.cityweatherapp.di

import com.example.cityweatherapp.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun provideHomeFragment() : HomeFragment

}