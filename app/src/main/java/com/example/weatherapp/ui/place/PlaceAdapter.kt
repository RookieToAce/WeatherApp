package com.example.weatherapp.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.logic.model.Place
import com.example.weatherapp.ui.wether.WeatherActivity

class PlaceAdapter(private val fragment: Fragment, private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)

        //从搜索地址界面跳转到天气显示界面
        val holder = ViewHolder(view)
        // itemView 点击查询出来的数据任何位置都可以触发
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition   //确定点击的位置
            val place = placeList[position]         //确定点击位置的城市
            val intent = Intent(parent.context,WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            fragment.startActivity(intent)
            fragment.activity?.finish()
        }

        return holder
    }

    override fun onBindViewHolder(holder: PlaceAdapter.ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    override fun getItemCount(): Int = placeList.size

}