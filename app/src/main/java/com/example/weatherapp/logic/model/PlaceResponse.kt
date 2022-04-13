package com.example.weatherapp.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
    //使用SerializedName注解，将自定义的名称和json格式数据中的名称对应起来
    //上述因为 json 格式数据中的一项属性为formatted_address，太繁琐，简化为address，使用注解形成对应关系。
)

data class Location(val lng: String, val lat: String)
