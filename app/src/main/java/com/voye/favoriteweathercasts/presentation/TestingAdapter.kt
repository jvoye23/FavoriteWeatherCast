package com.voye.favoriteweathercasts.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.voye.favoriteweathercasts.R
import com.voye.favoriteweathercasts.domain.location.SearchTesting

/*
class TestingAdapter: RecyclerView.Adapter<TestingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.list_item_search_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = SearchTesting().filteredList[position]
        val cityName = data.name
        val cityState = data.state
        val cityCountry = data.country
        holder.cityNameTextView.text = cityName + ", " + cityState + ", " + cityCountry
    }

    override fun getItemCount(): Int {
        val listSize = SearchTesting().filteredList.size
        return listSize
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cityNameTextView: TextView = itemView.findViewById(R.id.city_name_textView)
    }

}*/
