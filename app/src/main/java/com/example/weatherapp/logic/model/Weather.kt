package com.example.weatherapp.logic.model

data class Weather(val data: DataResponse.Daily, val realtime: RealtimeResponse.Realtime)
