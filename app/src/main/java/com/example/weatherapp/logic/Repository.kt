package com.example.weatherapp.logic

import androidx.lifecycle.liveData
import com.example.weatherapp.logic.network.WeatherNetWork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

object Repository {

    //还将liveData()函数的线程参数类型指定成了Dispatchers.IO，这样代码块中的所有代码就都运行在子线程中了
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = WeatherNetWork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response data is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        emit(result)
    }

}