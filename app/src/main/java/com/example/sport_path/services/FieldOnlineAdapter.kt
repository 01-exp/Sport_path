package com.example.sport_path.services.maps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_path.R

class PlaceOnlineAdapter(private val fieldOnlineList: List<Pair<String, Int>>) :
    RecyclerView.Adapter<PlaceOnlineAdapter.PlaceOnlineHolder>() {
    inner class PlaceOnlineHolder(item: View) : RecyclerView.ViewHolder(item) {
        val time: TextView = item.findViewById(R.id.time)
        val online: TextView = item.findViewById(R.id.online)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceOnlineHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.place_online_item_view, parent, false)
        return PlaceOnlineHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceOnlineHolder, position: Int) {
        val element = fieldOnlineList[position]
        holder.time.text = element.first
        holder.online.text = element.second.toString()
    }

    override fun getItemCount(): Int {
        return fieldOnlineList.size
    }

}