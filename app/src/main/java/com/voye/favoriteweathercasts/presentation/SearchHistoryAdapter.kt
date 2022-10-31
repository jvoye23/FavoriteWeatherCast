package com.voye.favoriteweathercasts.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.voye.favoriteweathercasts.R
import com.voye.favoriteweathercasts.data.local.LocationDTO
import com.voye.favoriteweathercasts.data.local.LocationDataItem

/**
 * A [RecyclerView.Adapter]
 */

class SearchHistoryAdapter (val searchHistoryClickListener: SearchHistoryOnClickListener): RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryAdapterViewHolder>() {
    //private val recentLocations = mutableListOf<LocationDataItem>()

    private val diffUtil = object : DiffUtil.ItemCallback<LocationDTO>(){
        override fun areItemsTheSame(
            oldItem: LocationDTO,
            newItem: LocationDTO
        ): Boolean {
            return oldItem.city == newItem.city
        }

        override fun areContentsTheSame(
            oldItem: LocationDTO,
            newItem: LocationDTO
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun saveData(locationsData: List<LocationDTO>){
        asyncListDiffer.submitList(locationsData)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryAdapter.SearchHistoryAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.search_history_location_item, parent, false)
        return SearchHistoryAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHistoryAdapterViewHolder, position: Int) {
        val data = asyncListDiffer.currentList[position]
        holder.recentCity.text = data.city
        holder.recentCountry.text = data.country
        holder.itemView.setOnClickListener{
            searchHistoryClickListener.onClick(data)
        }
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    class SearchHistoryAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val recentCity: TextView = itemView.findViewById(R.id.recentCity_textView)
        val recentCountry: TextView = itemView.findViewById(R.id.recentCountry_textView)
    }

    class SearchHistoryOnClickListener(val clickListener: (locationDTO: LocationDTO) -> Unit){
        fun onClick(locationDTO: LocationDTO) = clickListener(locationDTO)
    }

}