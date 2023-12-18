package com.example.sport_path.services.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_path.R
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.databinding.EntryItemBinding

class EntryAdapter(val entryList: List<String>): RecyclerView.Adapter<EntryAdapter.EntryHolder>() {



    class EntryHolder(item:View): RecyclerView.ViewHolder(item) {
        val binding = EntryItemBinding.bind(item)
        fun bind(entry: Entry) = with(binding)

        {
           textView2.text = entry.startTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.entry_item,parent, false)
        return  EntryHolder(view)
    }

    override fun getItemCount(): Int {
         return entryList.size
    }

    override fun onBindViewHolder(holder: EntryHolder, position: Int) {
        holder.bind(entryList[position])
    }

}