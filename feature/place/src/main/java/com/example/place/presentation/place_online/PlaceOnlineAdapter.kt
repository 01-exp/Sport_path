package com.example.place.presentation.place_online

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.place.R


class PlaceOnlineAdapter(private val placeOnlineList: List<Pair<String, Int>>) :
    RecyclerView.Adapter<PlaceOnlineAdapter.PlaceOnlineHolder>() {
    inner class PlaceOnlineHolder(item: View) : RecyclerView.ViewHolder(item) {
        val time: TextView = item.findViewById(R.id.time)
        val peopleCount: TextView = item.findViewById(R.id.people_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceOnlineHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_place_online, parent, false)
        return PlaceOnlineHolder(view)
    }

    override fun getItemCount(): Int {
        return placeOnlineList.size
    }

    override fun onBindViewHolder(holder: PlaceOnlineHolder, position: Int) {
        val element = placeOnlineList[position]
        holder.time.text = element.first
        val count = if ((element.second == 0) or (element.second % 2 != 0)) {
            "${element.second} человек"
        } else {
            "${element.second} человека"
        }
        holder.peopleCount.text = count
    }
}