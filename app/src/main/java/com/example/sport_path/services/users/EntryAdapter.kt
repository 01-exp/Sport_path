package com.example.sport_path.services.users

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_path.R
import com.example.sport_path.Utils
import com.example.sport_path.data_structures.Entry

class EntryAdapter(val entryList: MutableList<Entry>, val listener: OnDeleteButtonClickListener) :
    RecyclerView.Adapter<EntryAdapter.EntryHolder>() {



    inner class EntryHolder(item: View) : RecyclerView.ViewHolder(item), View.OnClickListener {
        val addressTextView: TextView = item.findViewById(R.id.address)
        val timeTextView: TextView = item.findViewById(R.id.time)
        val dateTextView: TextView = item.findViewById(R.id.date)
        val sportIcon: ImageView = item.findViewById(R.id.icon_sport)
        private val deleteButton: ImageButton = item.findViewById(R.id.delete_button)

        init {
            deleteButton.setOnClickListener(::onClick)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                Log.d("mLog","$position,${entryList.size}")
                listener.onDeleteButtonClick(entryList,position,entryList.size
                )



            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.entry_item, parent, false)
        return EntryHolder(view)
    }

    override fun getItemCount(): Int {
        return entryList.size
    }

    override fun onBindViewHolder(holder: EntryHolder, position: Int) {
        val element = entryList[position]
        val dateAndTime = element.time.split(" ")
        holder.addressTextView.text = Utils.cutAddress(element.placeAddress)
        holder.timeTextView.text = dateAndTime[1]

        holder.dateTextView.text = Utils.formattedDate(dateAndTime[0])


        holder.sportIcon.setImageResource(Utils.getIconBySport(element.placeSport))
    }

    interface OnDeleteButtonClickListener {
        fun onDeleteButtonClick(entryList:MutableList<Entry>, position: Int, size:Int)
    }

}