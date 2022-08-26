package com.example.kafeinweatherapp.ui.home

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import com.example.kafeinweatherapp.R

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kafeinweatherapp.databinding.FragmentHomeBinding
import com.example.kafeinweatherapp.databinding.FragmentSplashBinding
import com.example.kafeinweatherapp.model.entity.fivedayresponse.DailyForecast
import com.example.kafeinweatherapp.model.entity.fivedayresponse.WeatherFiveDayResponse
import com.example.kafeinweatherapp.model.entity.twelvehourresponse.WeatherTwelveHourResponse
import com.example.kafeinweatherapp.ui.base.BaseFragment
import com.example.kafeinweatherapp.ui.home.adapters.WeatherDailyForecastAdapter
import com.example.kafeinweatherapp.ui.home.adapters.WeatherDetailedInfoAdapter
import com.example.kafeinweatherapp.ui.home.adapters.WeatherHourlyForecastAdapter
import com.example.kafeinweatherapp.utils.Constants
import com.example.kafeinweatherapp.utils.Resource
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val args: HomeFragmentArgs by navArgs()

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var hourlyForecastAdapter: WeatherHourlyForecastAdapter
    private lateinit var dailyForecastAdapter: WeatherDailyForecastAdapter
    private lateinit var weatherDetailedInfoAdapter: WeatherDetailedInfoAdapter
    private val weatherDetailedInfoHashMap: HashMap<String, String> = HashMap()

    private fun onSetWeatherDetailedInfoAdapter() {
        binding.rvDetailedWeatherInfoList.addItemDecoration(DividerItemDecoration(
            requireContext(),
            RecyclerView.VERTICAL
        ))
        weatherDetailedInfoAdapter = WeatherDetailedInfoAdapter()
        binding.rvDetailedWeatherInfoList.adapter = weatherDetailedInfoAdapter
    }

    private fun onSetDailyForecastAdapter() {
        binding.rvDailyForecastList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.HORIZONTAL
            )
        )
        dailyForecastAdapter = WeatherDailyForecastAdapter()
        binding.rvDailyForecastList.adapter = dailyForecastAdapter
    }

    private fun onSetHourlyForecastAdapter() {
        binding.rvHourlyForecastList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.HORIZONTAL
            )
        )
        hourlyForecastAdapter = WeatherHourlyForecastAdapter()
        binding.rvHourlyForecastList.adapter = hourlyForecastAdapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSetDailyForecastAdapter()
        onSetHourlyForecastAdapter()
        onSetWeatherDetailedInfoAdapter()
        binding.tvWeatherFragmentCity.text = args.cityName
        binding.tvWeatherFragmentTopCity.text = args.cityName
       /* binding.svWeatherFragmentScroll.viewTreeObserver.addOnScrollChangedListener(
            ScrollPositionObserver()
        )*/
        viewModel.getWeather5DaysForecast(args.key?:"").observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> showWeatherDailyDataInfo(it.data)
                Resource.Status.ERROR -> showError(it.message)
            }

        })
        viewModel.getWeather12HourlyForecast(args.key?:"").observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> showWeatherDataInfo(it.data)
                Resource.Status.ERROR -> showError(it.message)
            }

        })

        binding.srWeatherFragmentRefresh.setOnRefreshListener {

        }

    }
    private fun showWeatherDataInfo(dataList: WeatherTwelveHourResponse?) {
        binding.srWeatherFragmentRefresh.isRefreshing = false
        if (dataList?.isNotEmpty()==true) {
            val weatherHourItem = dataList[0]
            binding.tvWeatherFragmentTemperature.text = weatherHourItem.temperature.value.toString()
            binding.tvWeatherFragmentPhrase.text = weatherHourItem.iconPhrase
            binding.tvWeatherFragmentTopPhrase.text = weatherHourItem.iconPhrase

            Glide.with(this).load("https://weather.ls.hereapi.com/static/weather/icon/${weatherHourItem.weatherIcon}.png?apikey=${Constants.API_KEY}").error(R.drawable.ic_broken_image_white)
                .into(binding.ivWeatherFragmentIcon)

            hourlyForecastAdapter.updateHourlyForecast(dataList)
            hourlyForecastAdapter.onItemClick = { onOpenUrl(it) }
            weatherDetailedInfoHashMap["humidity"] = weatherHourItem.relativeHumidity.toString()
            weatherDetailedInfoHashMap["realFeelTemperature"] = "${weatherHourItem.realFeelTemperature.value} ${weatherHourItem.realFeelTemperature.unit}"
            weatherDetailedInfoHashMap["uvIndex"] = weatherHourItem.uVIndexText
            weatherDetailedInfoAdapter.updateData(weatherDetailedInfoHashMap)

        }
    }

    private fun showWeatherDailyDataInfo(weather5DaysForecast: WeatherFiveDayResponse?) {
        binding.srWeatherFragmentRefresh.isRefreshing = false
        val dataList = weather5DaysForecast?.dailyForecasts
        if (dataList?.isNotEmpty() == true) {
            dailyForecastAdapter.updateDailyForecast(dataList)
            dailyForecastAdapter.onItemClick = { onOpenUrl(it) }

        }
    }

    private fun showError(message:String?){
        val dialog = AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage("${message}")
            .setPositiveButton("ok") { dialog, button ->
                dialog.dismiss()
            }
        dialog.show()
    }


    private fun onOpenUrl(it: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(it)
        startActivity(intent)
    }

    /*private inner class ScrollPositionObserver : ViewTreeObserver.OnScrollChangedListener {

        private val mTopLayoutHeight: Int =
            resources.getDimensionPixelSize(R.dimen.top_layout_height)

        override fun onScrollChanged() {
            val scrollY = binding.svWeatherFragmentScroll.scrollY.coerceAtLeast(0)
                .coerceAtMost(mTopLayoutHeight)

            binding.layoutWeatherFragmentTopScroll.translationY =
                (scrollY / 2).toFloat()

            val alpha = 100 * scrollY / mTopLayoutHeight.toFloat()

            binding.layoutWeatherFragmentTop.alpha = alpha
            binding.layoutWeatherFragmentTop.visibility = View.VISIBLE

        }
    }*/


}