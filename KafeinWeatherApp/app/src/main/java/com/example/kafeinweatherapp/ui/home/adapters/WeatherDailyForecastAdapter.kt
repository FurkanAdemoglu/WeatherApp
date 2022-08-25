package com.example.kafeinweatherapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.kafeinweatherapp.R
import com.example.kafeinweatherapp.databinding.ItemDailyForecastBinding
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.kafeinweatherapp.model.entity.fivedayresponse.DailyForecast
import com.example.kafeinweatherapp.model.entity.fivedayresponse.WeatherFiveDayResponse
import com.example.kafeinweatherapp.utils.Constants.API_KEY

class WeatherDailyForecastAdapter : RecyclerView.Adapter<WeatherDailyForecastAdapter.ViewHolder>() {

    lateinit var dayForecastList: MutableList<DailyForecast>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemDailyForecastBinding = DataBindingUtil.inflate(layoutInflater,  R.layout.item_daily_forecast, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return if (::dayForecastList.isInitialized) dayForecastList.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dayForecast = dayForecastList[position]
        holder.bind(dayForecast)
    }

    fun updateDailyForecast(dayForecastList: List<DailyForecast>) {
        this.dayForecastList = dayForecastList.toMutableList()
        notifyDataSetChanged()
    }

    var onItemClick: ((String) -> Unit)? = null

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val rowDailyForecastLayout: ViewGroup = itemView.findViewById(R.id.rowDailyForecastLayout)
        private val tvDailyForecastDate: TextView = itemView.findViewById(R.id.tvDailyForecastDate)
        private val tvDailyForecastDayTemp: TextView = itemView.findViewById(R.id.tvDailyForecastDayTemp)
        private val tvDailyForecastNightTemp: TextView = itemView.findViewById(R.id.tvDailyForecastNightTemp)
        private val ivDailyForecastDayIcon: ImageView = itemView.findViewById(R.id.ivDailyForecastDayIcon)
        private val ivDailyForecastNightIcon: ImageView = itemView.findViewById(R.id.ivDailyForecastNightIcon)


        fun bind(dayForecast: DailyForecast) {

            tvDailyForecastDate.text = dayForecast.date
            tvDailyForecastDayTemp.text ="${dayForecast.temperature.maximum.value} ${dayForecast.temperature.maximum.unit}"
            tvDailyForecastNightTemp.text = "${dayForecast.temperature.minimum.value} ${dayForecast.temperature.minimum.unit}"
                Glide.with(itemView)
                    .load("https://weather.ls.hereapi.com/static/weather/icon/${dayForecast.day.icon}.png?apikey=${API_KEY}")
                    .into(ivDailyForecastDayIcon)

            ivDailyForecastNightIcon.let {
                Glide.with(it)
                    .load(dayForecast.night.icon)
                    .into(ivDailyForecastDayIcon)
            }

            rowDailyForecastLayout.setOnClickListener {
                onItemClick?.invoke(dayForecast.mobileLink)
            }
        }
    }
}