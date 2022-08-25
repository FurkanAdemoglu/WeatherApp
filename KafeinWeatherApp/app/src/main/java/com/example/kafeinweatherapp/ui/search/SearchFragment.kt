package com.example.kafeinweatherapp.ui.search

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.kafeinweatherapp.databinding.FragmentSearchBinding
import com.example.kafeinweatherapp.model.entity.search.SearchResponse
import com.example.kafeinweatherapp.model.entity.search.SearchResponseItem
import com.example.kafeinweatherapp.ui.base.BaseFragment
import com.example.kafeinweatherapp.ui.home.HomeViewModel
import com.example.kafeinweatherapp.ui.splash.SplashViewModel
import com.example.kafeinweatherapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment :BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()
    lateinit var listAdapter: SearchAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.search(query?:"").observe(viewLifecycleOwner, Observer {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                        }
                        Resource.Status.SUCCESS -> onSuccessData(it.data)
                        Resource.Status.ERROR -> showError(it.message)
                    }
                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }

        })

    }
    private fun setupRecyclerView(){
        listAdapter= SearchAdapter()
        binding.rvCountryCityList.apply {
            adapter=listAdapter
            layoutManager= LinearLayoutManager(activity)

        }
    }


    private fun onSuccessData(response:SearchResponse?){
        listAdapter.setOnItemClickListener(object : ICityClickListener {

            override fun onClick(name: SearchResponseItem) {

            }
        })
        listAdapter.differ.submitList(response)
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
}