package com.example.kafeinweatherapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kafeinweatherapp.R
import com.example.kafeinweatherapp.databinding.ItemHourlyForecastBinding
import com.example.kafeinweatherapp.model.entity.twelvehourresponse.WeatherTwelveHourResponseItem
import com.example.kafeinweatherapp.utils.Constants

class WeatherHourlyForecastAdapter : RecyclerView.Adapter<WeatherHourlyForecastAdapter.ViewHolder>() {

    lateinit var list: MutableList<WeatherTwelveHourResponseItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemHourlyForecastBinding = DataBindingUtil.inflate(layoutInflater,  R.layout.item_hourly_forecast, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return if (::list.isInitialized) list.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather12HourlyForecast = list[position]
        holder.bind(weather12HourlyForecast)
    }

    fun updateHourlyForecast(list: List<WeatherTwelveHourResponseItem>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    var onItemClick: ((String) -> Unit)? = null

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val rowHourlyForecastLayout: ViewGroup = itemView.findViewById(R.id.rowHourlyForecastLayout)
        private val tvHourlyForecastTime: TextView = itemView.findViewById(R.id.tvHourlyForecastTime)
        private val ivHourlyForecastIcon: ImageView = itemView.findViewById(R.id.ivHourlyForecastIcon)
        private val tvHourlyForecastHumidity: TextView = itemView.findViewById(R.id.tvHourlyForecastHumidity)
        private val tvHourlyForecastTemperature: TextView = itemView.findViewById(R.id.tvHourlyForecastTemperature)



        fun bind(weather12HourlyForecast: WeatherTwelveHourResponseItem) {

            tvHourlyForecastHumidity.text = weather12HourlyForecast.relativeHumidity.toString()
            tvHourlyForecastTemperature.text = weather12HourlyForecast.temperature.value.toString()
            tvHourlyForecastTime.text = weather12HourlyForecast.dateTime
            itemView.let {
                Glide.with(it)
                    .load("https://weather.ls.hereapi.com/static/weather/icon/${weather12HourlyForecast.weatherIcon}.png?apikey=${Constants.API_KEY}")
                    .error(R.drawable.ic_broken_image_white)
                    .fitCenter()
                    .into(ivHourlyForecastIcon)

            }
            rowHourlyForecastLayout.setOnClickListener {
                onItemClick?.invoke(weather12HourlyForecast.mobileLink)
            }
        }
    }
}