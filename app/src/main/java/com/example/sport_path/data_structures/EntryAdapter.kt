package com.example.sport_path.data_structures

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_path.databinding.EntryItemBinding
import java.util.ArrayList

class EntryAdapter: RecyclerView.Adapter<EntryAdapter.EntryHolder>() {
    val planList = ArrayList <Entry>()
    class EntryHolder(item:View): RecyclerView.ViewHolder(item) {
        val binding = EntryItemBinding.bind(item)
        fun bind(entry: Entry) = with(binding)

        {
           textView2.text = entry.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.entry_item,parent, false)
        return  EntryHolder(view)
    }

    override fun getItemCount(): Int {
         return planList.size
    }

    override fun onBindViewHolder(holder: EntryHolder, position: Int) {
        holder.bind(planList[position])
    }
    fun addEntry(entry: Entry){
        planList.add(entry)
        notifyDataSetChanged()
    }
}