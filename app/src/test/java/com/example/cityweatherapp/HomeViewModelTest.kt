package com.example.cityweatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cityweatherapp.repository.Repository
import com.example.cityweatherapp.repository.local.room.entity.CityWeather
import com.example.cityweatherapp.repository.remote.ResultWrapper
import com.myapp.mymoviesapp.utils.getOrAwaitValue
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import provideFakeCoroutinesDispatcherProvider

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(dispatcher = testDispatcher)

    }

    @After
    fun tearDown(){

        Dispatchers.resetMain()

        testDispatcher.cleanupTestCoroutines()

    }

    @Test
    fun `should emit loading and then result on fetchWeatherDetails success call`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val query = "pune"
            val apiKey = "hj433456yhdfb"


            val repo: Repository = mock()

            val viewModel = HomeViewModel(repo,provideFakeCoroutinesDispatcherProvider(testDispatcher,testDispatcher,testDispatcher))

            val cityWeather = CityWeather(
                    query, System.currentTimeMillis(),
                    98.09,
                    99.09,
                    12345,
                    "Clear",
                    "Clear Sky",
                    "10n",
                    "Pune",
                    1111.09,
                    1111.09,
                    40,
                    29,
                    1111.09,
                    1111.09,
            )

            val res = ResultWrapper.Success(cityWeather)

            Mockito.`when`(repo.getWeatherDetails(apiKey,query)).thenReturn(res)

            viewModel.fetchWeatherDetails(apiKey,query)

            val loadingActual = viewModel.weatherLiveData.value

            Assert.assertNotNull(loadingActual)

            assertEquals(ResultWrapper.Loading(true), loadingActual)

           testDispatcher.resumeDispatcher()

            val resultActual =   viewModel.weatherLiveData.getOrAwaitValue()

            print("resultActual $resultActual")

            Assert.assertNotNull(viewModel.weatherLiveData.value)

            assertEquals(res, resultActual)
        }

    }

    @Test
    fun `should emit loading and then error & exception on fetchWeatherDetails failure`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val query = "pune"
            val apiKey = "hj433456yhdfb"

            val repo: Repository = mock()

            val viewModel = HomeViewModel(repo,provideFakeCoroutinesDispatcherProvider(testDispatcher,testDispatcher,testDispatcher))

            val exception = Exception()

            val errorMsg = ""

            val res = ResultWrapper.Error(errorMsg,exception)

            Mockito.`when`(repo.getWeatherDetails(apiKey,query)).thenReturn(res)

            viewModel.fetchWeatherDetails(apiKey,query)

            val loadingActual = viewModel.weatherLiveData.value
            Assert.assertNotNull(loadingActual)

            assertEquals(ResultWrapper.Loading(true),loadingActual)

            testDispatcher.resumeDispatcher()

            val resultActual = viewModel.weatherLiveData.getOrAwaitValue()
            Assert.assertNotNull(resultActual)

            assertEquals(res,resultActual)

        }

    }

}