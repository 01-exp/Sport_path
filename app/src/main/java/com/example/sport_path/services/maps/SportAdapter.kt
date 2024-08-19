package com.example.sport_path.services.maps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_path.R
import com.example.maps.data.data_structures.Sport

class SportAdapter(private val sportList: List<Sport>,
                   private val listener : OnItemCLickListener
) :
    RecyclerView.Adapter<SportAdapter.SportHolder>() {
    inner class SportHolder(item: View) : RecyclerView.ViewHolder(item),View.OnClickListener {
        val name: TextView = item.findViewById(R.id.sport_name)
        val icon: ImageView = item.findViewById(R.id.icon)
        private val cardView :CardView = item.findViewById(R.id.cardView)
        init{
            cardView.setOnClickListener(::onClick)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position!=RecyclerView.NO_POSITION){
                listener.onItemCLick(
                    sportList[position]
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_sport, parent, false)
        return SportHolder(view)
    }
    override fun onBindViewHolder(holder: SportHolder, position: Int) {
        val element = sportList[position]
        holder.name.text = element.name
        holder.icon.setImageResource(
            element.icon
        )
    }
    override fun getItemCount(): Int {
        return sportList.size
    }
    interface OnItemCLickListener{
        fun onItemCLick(sport: Sport)
    }
}