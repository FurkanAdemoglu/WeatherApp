package com.example.kafeinweatherapp.ui.splash

import android.Manifest
import android.animation.Animator
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.kafeinweatherapp.databinding.FragmentSplashBinding
import com.example.kafeinweatherapp.model.entity.geopointresponse.GeoPositionResponse
import com.example.kafeinweatherapp.model.local.SharedPrefManager
import com.example.kafeinweatherapp.ui.base.BaseFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions
import kotlin.system.exitProcess


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate),EasyPermissions.PermissionCallbacks {

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    locationManager=activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        viewModel.liveData.observe(viewLifecycleOwner,::onStateChanged)
    }

    fun onStateChanged(state:SplashViewModel.State){
        when(state){
            is SplashViewModel.State.OnError->{
                showError(state.errorMessage)
            }
            is SplashViewModel.State.OnSuccess->{
                success(state.data)
            }
        }
    }


    private fun initViews() {
        binding.lottieAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                Log.v("Animation", "Started")
            }

            override fun onAnimationEnd(animation: Animator?) {
               checkPermissionGranted()
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.v("Animation", "Canceled")
            }

            override fun onAnimationRepeat(animation: Animator?) {
                Log.v("Animation", "Repeated")
            }
        })
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    onPermissionGranted()
    // sendRequest(viewModel.location)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        onPermissionGranted()
    }

    private fun sendRequest(location:Location?){
        viewModel.getLocationData("${location?.latitude?:41},${location?.longitude?:28}")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }

    private fun success(data: GeoPositionResponse?){
        SharedPrefManager(requireContext()).saveKey(data?.key?:"")
        SharedPrefManager(requireContext()).saveCity(data?.localizedName?:"")
        val action=SplashFragmentDirections.actionSplashFragmentToHomeFragment()
        findNavController().navigate(action)
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
    private fun onPermissionGranted(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {


            activity?.finish()
            exitProcess(0)
            //sendRequest(Location.)
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                viewModel.location=location
                sendRequest(location)
            }

    }

    private fun checkPermissionGranted() {
        if (context != null) {
            if (!EasyPermissions.hasPermissions(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                EasyPermissions.requestPermissions(
                    this,
                    "Bulunduğunuz konumdaki hava durumu için konuma izin vermelisiniz.",
                    200,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                return
            } else {
                onPermissionGranted()
            }
        }
    }
}

