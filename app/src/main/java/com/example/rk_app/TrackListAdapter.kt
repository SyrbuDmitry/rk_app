package com.example.rk_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

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

        holder.cv.setOnClickListener { v ->
//            val args = Bundle()
//            val articleJSON: String = articles!!.get(position).toString()
//            args.putString(ArticleFragment_ARG_ARTICLE_JSON, articleJSON)
            v.findNavController().navigate(R.id.action_trackListFragment_to_cryptoInfoFragment)
        }

        holder.setData(item)
    }


    class ElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cv : CardView = itemView.findViewById(R.id.courseCard)

        var date : TextView = itemView.findViewById(R.id.date)
        var currency : TextView = itemView.findViewById(R.id.currency)
        var price : TextView = itemView.findViewById(R.id.price)


        fun setData(item:DataListItem){
            date.text = convertStampToDate(item.time)
            price.text = item.close.toString()
            currency.text = item.currency
        }

        fun convertStampToDate(stamp:Int):String{
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val dateInst = java.util.Date(stamp.toLong()*1000)
            return sdf.format(dateInst).toString()
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