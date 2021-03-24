package com.example.cityweatherapp.bindings

import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.cityweatherapp.Utils
import com.example.cityweatherapp.repository.local.room.entity.CityWeather
import com.example.cityweatherapp.repository.remote.ResultWrapper
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


@BindingAdapter("hideViewOnLoading")
    fun ViewGroup.hideViewOnLoading(resultWrapper: ResultWrapper<CityWeather>) {
        visibility = if (resultWrapper is ResultWrapper.Loading)
            View.GONE
        else
            View.VISIBLE
    }

    @BindingAdapter("showProgressBarOnLoading")
    fun ProgressBar.showProgressBarOnLoading(resultWrapper: ResultWrapper<CityWeather>?) {
        visibility = if (resultWrapper!=null && resultWrapper is ResultWrapper.Loading)
            View.VISIBLE
        else
            View.GONE
    }

@BindingAdapter("weatherViewVisibility")
fun ViewGroup.weatherViewVisibility(resultWrapper: ResultWrapper<CityWeather>?) {
    visibility = if (resultWrapper!=null && resultWrapper is ResultWrapper.Success)
        View.VISIBLE
    else
        View.GONE

}

@BindingAdapter("showError")
fun ViewGroup.showError(resultWrapper: ResultWrapper<CityWeather>?) {
    if (resultWrapper!=null && resultWrapper is ResultWrapper.Error){

        Toast.makeText(this.context,"Error : ${resultWrapper.message}", Toast.LENGTH_LONG).show()

    }
}

    @BindingAdapter("loadImage")
    fun ImageView.loadImage(url : String?) {

        Glide
            .with(this.context)
            .load(url?.let { Utils.buildWeatherIconUrl(it) })
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)

    }

    @BindingAdapter("temp")
    fun TextView.temp(temp: Double){

        val tempInCel = (temp - 273.15).roundToInt()

        text = "$tempInCel" + 0x00B0.toChar() + " C"

    }

@BindingAdapter("humidity")
fun TextView.humidity(p: Int){

    text = "$p %"

}

@BindingAdapter("currentTime")
fun TextView.currentTime(date: Long){

    val sdf = SimpleDateFormat("hh:mm")
    val time = sdf.format(Date(date))
    text = time

}
