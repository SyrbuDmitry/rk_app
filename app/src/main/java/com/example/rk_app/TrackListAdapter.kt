package com.example.rk_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context

class TrackListAdapter(val context: Context) :RecyclerView.Adapter<TrackListAdapter.ElementViewHolder>(){
    var data = listOf<DataListItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        return ElementViewHolder.from(parent)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        val item = data[position]
        holder.setData(item)
    }


    class ElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cv : CardView = itemView.findViewById(R.id.courseCard)

        var date : TextView = itemView.findViewById(R.id.date)
        var currency : TextView = itemView.findViewById(R.id.currency)

        fun setData(item:DataListItem){
            date.text = item.time.toString()
            currency.text = item.currency
        }

        companion object{
            fun from(parent: ViewGroup) : ElementViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item,parent,false)
                return ElementViewHolder(view)
            }
        }

    }
}