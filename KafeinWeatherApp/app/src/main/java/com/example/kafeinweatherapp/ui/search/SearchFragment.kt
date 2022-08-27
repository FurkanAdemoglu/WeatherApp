package com.example.kafeinweatherapp.ui.search

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.kafeinweatherapp.databinding.FragmentSearchBinding
import com.example.kafeinweatherapp.model.entity.search.SearchResponse
import com.example.kafeinweatherapp.model.entity.search.SearchResponseItem
import com.example.kafeinweatherapp.model.entity.searchedwords.Word
import com.example.kafeinweatherapp.ui.base.BaseFragment
import com.example.kafeinweatherapp.ui.detail.DetailViewModel
import com.example.kafeinweatherapp.ui.home.HomeViewModel
import com.example.kafeinweatherapp.ui.splash.SplashViewModel
import com.example.kafeinweatherapp.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class SearchFragment :BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()
    lateinit var listAdapter: SearchAdapter
    lateinit var searchedWordAdapter: SearchedWordAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchedWordRecyclerView()
        setupRecyclerView()
        viewModel.liveData.observe(viewLifecycleOwner,::onStateChanged)
        binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.search(query?:"")
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()){
                    listAdapter.differ.submitList(ArrayList())
                    viewModel.allWords.observe(viewLifecycleOwner, Observer {
                        searchedWordAdapter.submitList(it)
                    })
                    binding.searchedWords.visibility=View.VISIBLE
                    binding.rvCountryCityList.visibility=View.GONE
                }
                return true
            }
        })
        viewModel.allWords.observe(viewLifecycleOwner, Observer {
            searchedWordAdapter.submitList(it)
        })

    }

    fun onStateChanged(state: SearchViewModel.State){
        when(state){
            is SearchViewModel.State.OnError->{
                showError(state.errorMessage)
            }
            is SearchViewModel.State.OnSearchResponseSuccess->{
                onSuccessData(state.data)
            }

        }
    }
    private fun setupRecyclerView(){
        listAdapter= SearchAdapter()
        binding.rvCountryCityList.apply {
            adapter=listAdapter
            layoutManager= LinearLayoutManager(activity)

        }
    }

    private fun setupSearchedWordRecyclerView(){
        searchedWordAdapter = SearchedWordAdapter()
        binding.searchedWords.apply {
            adapter=searchedWordAdapter
            layoutManager = LinearLayoutManager(activity)

        }
        searchedWordAdapter.setOnItemClickListener(object : IWordClickListener{
            override fun onClick(name: String) {
                binding.etSearch.setQuery(name,true)
            }

        })
    }

    private fun onSuccessData(response:SearchResponse?){
        if (response?.isEmpty()?:false){
            binding.searchedWords.visibility=View.VISIBLE
            binding.rvCountryCityList.visibility=View.GONE
        }else{
            binding.searchedWords.visibility=View.GONE
            binding.rvCountryCityList.visibility=View.VISIBLE
        }
        listAdapter.setOnItemClickListener(object : ICityClickListener {
            override fun onClick(name: SearchResponseItem) {
                val action =SearchFragmentDirections.actionSplashFragmentToDetailFragment(name.key,name.localizedName)
                findNavController().navigate(action)
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