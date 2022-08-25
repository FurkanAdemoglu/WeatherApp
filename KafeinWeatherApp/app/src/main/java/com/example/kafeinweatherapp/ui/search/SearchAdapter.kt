package com.example.kafeinweatherapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kafeinweatherapp.R
import com.example.kafeinweatherapp.model.entity.search.SearchResponseItem
import kotlinx.android.synthetic.main.item_search.view.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var listener: ICityClickListener? = null
    private val differCallback = object : DiffUtil.ItemCallback<SearchResponseItem>() {
        override fun areItemsTheSame(oldItem: SearchResponseItem, newItem: SearchResponseItem): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: SearchResponseItem, newItem: SearchResponseItem): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_search,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchItem = differ.currentList[position]
        holder.itemView.apply {

            tv_country.text = searchItem.country.localizedName
            tv_city.text = searchItem.localizedName

            movieCardView.setOnClickListener {
                listener?.onClick(searchItem)
            }
        }
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    fun setOnItemClickListener(listener: ICityClickListener) {
        this.listener = listener
    }
}