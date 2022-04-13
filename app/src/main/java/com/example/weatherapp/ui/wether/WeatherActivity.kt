package com.example.weatherapp.ui.wether

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.R
import com.example.weatherapp.logic.model.Weather
import com.example.weatherapp.logic.model.getSky
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val decorView = window.decorView
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

        setContentView(R.layout.activity_weather)

        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }

        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }

        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }

        viewModel.weatherLiveData.observe(this, Observer {
            val weather = it.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法获取天气信息", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
        })

        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)

    }

    private fun showWeatherInfo(weather: Weather) {
        placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.data

        //填充 now.xml 中的数据 currentTemp  currentSky  currentAQI
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        currentTemp.text = currentTempText
        currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        currentAQI.text = currentPM25Text
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)

        //填充未来数据板块 即 forecast.xml 中的数据
        //先清空所有的历史数据显示
        forecastLayout.removeAllViews()
        val days = daily.skycon.size  //确定一共有多少天的未来数据进行展示
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            //向forecastLayout中动态添加布局文件,获取实例，目的是更新布局中控件的内容。
            val view =
                LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)

            //向新建的forecast_item中添加数据 dataInfo skyIcon skyInfo temperatureInfo
            val dataInfo = view.findViewById(R.id.dataInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDataFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dataInfo.text = simpleDataFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min} ~ ${temperature.max} ℃"
            temperatureInfo.text = tempText

            //把新的view 添加进入 forecast.xml
            forecastLayout.addView(view)
        }

        //填充生活指数板块 即 life_index.xml 中的数据  coldRiskText
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc

        weatherLayout.visibility = View.VISIBLE
    }
}
