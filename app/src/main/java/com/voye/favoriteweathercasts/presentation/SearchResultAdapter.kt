package com.voye.favoriteweathercasts.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.voye.favoriteweathercasts.R
import com.voye.favoriteweathercasts.domain.location.CoordinatesByLocationName

class SearchResultAdapter: RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    private val diffUtil = object: DiffUtil.ItemCallback<CoordinatesByLocationName>(){
        override fun areItemsTheSame(
            oldItem: CoordinatesByLocationName,
            newItem: CoordinatesByLocationName
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: CoordinatesByLocationName,
            newItem: CoordinatesByLocationName
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)
    fun saveData(dataResponse: List<CoordinatesByLocationName>){
        asyncListDiffer.submitList(dataResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.list_item_search_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = asyncListDiffer.currentList[position]
        val cityName = data.name
        val cityState= data.state
        val cityCountry = data.country
        holder.cityNameTextView.text = cityName + ", " + cityState + ", " + cityCountry
    }


    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cityNameTextView: TextView = itemView.findViewById(R.id.city_name_textView)
    }
}