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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.kafeinweatherapp.R
import com.example.kafeinweatherapp.databinding.FragmentSplashBinding
import com.example.kafeinweatherapp.ui.base.BaseFragment
import com.example.kafeinweatherapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate),EasyPermissions.PermissionCallbacks {

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var locationManager: LocationManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    locationManager=activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
        sendRequest()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        onPermissionGranted()
    }

    private fun sendRequest(){

        viewModel.getLocationData("${viewModel.location?.latitude ?: 41},${viewModel.location?.longitude ?: 28}").observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {

                }
                Resource.Status.SUCCESS -> {

                    Log.v("getLocationData","${it.data}")
                    /* val action =
                         SplashFragmentDirections.actionSplashFragmentToListFragment(
                             it.data?.accessToken
                         )
                     findNavController().navigate(action)*/

                }
                Resource.Status.ERROR -> {
                    val dialog = AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("${it.message}")
                        .setPositiveButton("ok") { dialog, button ->
                            dialog.dismiss()
                        }
                    dialog.show()
                }
            }

        })
    }
    private fun onPermissionGranted(){
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            val providers:List<String> = locationManager.getProviders(true)
            var bestLocation: Location? = null
            for (provider in providers){
                val l : Location =locationManager.getLastKnownLocation(provider) ?: continue
                if (bestLocation==null || l.accuracy<bestLocation.accuracy){
                    bestLocation = l
                }
            }
            viewModel.location=bestLocation
        }
        sendRequest()
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
                    "Hava durumu için konuma izin vermelisiniz",
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

