package com.example.weatherapp.logic.network

import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//获取全球大多数城市数据
//https://api.caiyunapp.com/v2/place?query=北京&token={token}&lang=zh_CN

interface PlaceService {

    //只有query参数需要认为输入，其它的token与lang都是固定值，直接写即可
    @GET("v2/place/token={${WeatherApplication.TOKEN}}&lang=zh_CN")
    fun searchPlace(@Query("query") query: String): Call<PlaceResponse>


}