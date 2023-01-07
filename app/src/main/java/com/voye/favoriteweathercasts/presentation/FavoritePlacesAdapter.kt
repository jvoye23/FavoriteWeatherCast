package com.voye.favoriteweathercasts.presentation

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.voye.favoriteweathercasts.R
import com.voye.favoriteweathercasts.data.local.FavoriteLocationDTO
import com.voye.favoriteweathercasts.data.local.LocationDTO


/**
 * A [RecyclerView.Adapter]
 */
class FavoritePlacesAdapter(
    val favoritePlaceOnClickListener: FavoritePlaceOnClickListener,
    val deleteFavoritePlaceOnClickListener: DeleteFavoritePlaceOnClickListener
    ): RecyclerView.Adapter<FavoritePlacesAdapter.FavoritePlaceAdapterViewHolder>() {

    var isDeleteButtonVisible: Boolean = false


    private val diffUtil = object : DiffUtil.ItemCallback<FavoriteLocationDTO>() {
        override fun areItemsTheSame(oldItem: FavoriteLocationDTO, newItem: FavoriteLocationDTO): Boolean {
            return oldItem.city == newItem.city
        }

        override fun areContentsTheSame(oldItem: FavoriteLocationDTO, newItem: FavoriteLocationDTO): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun saveData(favoriteLocationsData: List<FavoriteLocationDTO>){
        asyncListDiffer.submitList(favoriteLocationsData)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePlaceAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.list_item_favorite_place, parent, false)
        return FavoritePlaceAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritePlaceAdapterViewHolder, position: Int) {
        val data = asyncListDiffer.currentList[position]
        holder.favoriteCity.text = data.city
        holder.favoriteCountry.text = data.country
        holder.itemView.setOnClickListener {
            favoritePlaceOnClickListener.onClick(data)
        }
        if (isDeleteButtonVisible) {
            holder.deleteFavoritePlaceButton.visibility = View.VISIBLE
        } else {
            holder.deleteFavoritePlaceButton.visibility = View.GONE
        }
        holder.deleteFavoritePlaceButton.setOnClickListener {
            //Toast.makeText(it.context, "Place: ${data.city}", Toast.LENGTH_LONG).show()
            deleteFavoritePlaceOnClickListener.onClick(data)
        }
    }
    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    class FavoritePlaceAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val favoriteCity: TextView = itemView.findViewById(R.id.favoriteCity_textView)
        val favoriteCountry: TextView = itemView.findViewById(R.id.favoriteCountry_textView)
        val deleteFavoritePlaceButton: ImageButton = itemView.findViewById(R.id.deleteFavoritePlace_button)
    }

    class FavoritePlaceOnClickListener(val clickListener: (favoriteLocationDTO: FavoriteLocationDTO) -> Unit) {
        fun onClick(favoriteLocationDTO: FavoriteLocationDTO) = clickListener(favoriteLocationDTO)
    }

    class DeleteFavoritePlaceOnClickListener (val clickListener: (favoriteLocationDTO: FavoriteLocationDTO) -> Unit) {
        fun onClick(favoriteLocationDTO: FavoriteLocationDTO) = clickListener(favoriteLocationDTO)
    }




}


