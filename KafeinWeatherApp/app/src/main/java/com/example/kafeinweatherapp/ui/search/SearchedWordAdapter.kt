package com.example.kafeinweatherapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kafeinweatherapp.R
import com.example.kafeinweatherapp.model.entity.searchedwords.Word
import kotlinx.android.synthetic.main.item_searched_words.view.*

class SearchedWordAdapter : ListAdapter<Word, SearchedWordAdapter.WordViewHolder>(WordsComparator()) {
    private var listener: IWordClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.word)
        holder.itemView.searchedWordCardView.setOnClickListener {
            listener?.onClick(current.word)
        }
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)
      //  private val searchedWordCardView: TextView = itemView.findViewById(R.id.searchedWordCardView)
        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_searched_words, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.word == newItem.word
        }
    }
    fun setOnItemClickListener(listener: IWordClickListener) {
        this.listener = listener
    }
}