package com.example.a7minutesworkoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkoutapp.databinding.ActivityHistoryBinding
import com.example.a7minutesworkoutapp.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val items: ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemHistoryRowBinding):RecyclerView.ViewHolder(binding.root){
        val llHistory = binding.llHistory
        val tvItem = binding.tvItem
        val tvPosition = binding.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = items.get(position)
        holder.tvItem.text = (position+1).toString()
        holder.tvPosition.text = date

        if (position % 2 == 0){
            holder.llHistory.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.lightGrey))
        }else{
            holder.llHistory.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.white))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}