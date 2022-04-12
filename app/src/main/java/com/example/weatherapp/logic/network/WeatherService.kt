package com.example.weatherapp.logic.network

import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.logic.model.DataResponse
import com.example.weatherapp.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    //获取实时天气信息
    //https://api.caiyunapp.com/v2.5/{token}/116.4073963,39.9041999/realtime.json

    //获取未来几天的天气信息
    //https://api.caiyunapp.com/v2.5/{token}/116.4073963,39.9041999/daily.json

    @GET("v2.5/${WeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Call<RealtimeResponse>

    @GET("v2.5/${WeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DataResponse>

}