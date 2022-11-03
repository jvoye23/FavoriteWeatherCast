package com.voye.favoriteweathercasts.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.voye.favoriteweathercasts.R
import com.voye.favoriteweathercasts.utils.GeocodingResult
import java.util.ArrayList

/**
 * A [RecyclerView.Adapter] for a [com.google.android.libraries.places.api.model.AutocompletePrediction].
 */
class PlacePredictionAdapter: RecyclerView.Adapter<PlacePredictionAdapter.PlacePredictionViewHolder>() {
    private val predictions: MutableList<AutocompletePrediction> = ArrayList()
    var onPlaceClickListener: ((AutocompletePrediction) -> Unit)? = null
    var onFavoriteClickListener: ((AutocompletePrediction) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacePredictionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlacePredictionViewHolder(
            inflater.inflate(R.layout.place_prediction_item, parent, false))
    }

    override fun onBindViewHolder(holder: PlacePredictionViewHolder, position: Int) {
        val place = predictions[position]
        holder.setPrediction(place)
        holder.saveFavoriteLocationButton.setOnClickListener {
            onFavoriteClickListener?.invoke(place)
        }

        holder.itemView.setOnClickListener {
            onPlaceClickListener?.invoke(place)
        }

    }

    override fun getItemCount(): Int {
        return predictions.size
    }

    fun setPredictions(predictions: List<AutocompletePrediction>?) {
        this.predictions.clear()
        this.predictions.addAll(predictions!!)
        notifyDataSetChanged()
    }

    class PlacePredictionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.text_view_title)
        private val address: TextView = itemView.findViewById(R.id.text_view_address)
        val saveFavoriteLocationButton: Button = itemView.findViewById(R.id.save_to_favorites_button)

        fun setPrediction(prediction: AutocompletePrediction) {
            title.text = prediction.getPrimaryText(null)
            address.text = prediction.getSecondaryText(null)
        }
    }

    interface OnPlaceClickListener {
        fun onPlaceClicked(place: AutocompletePrediction)
    }

    interface OnFavoriteClickListener {
        fun onFavoriteClicked(place: AutocompletePrediction)
    }

}