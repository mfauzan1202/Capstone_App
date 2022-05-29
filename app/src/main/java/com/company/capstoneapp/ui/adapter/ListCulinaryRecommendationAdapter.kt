package com.company.capstoneapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.capstoneapp.R
import com.company.capstoneapp.ui.DetailActivity

class ListCulinaryRecommendationAdapter : RecyclerView.Adapter<ListCulinaryRecommendationAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_culinary_recommendation, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.apply {
            itemImage.setImageResource(R.drawable.food_dummy_3)
            itemName.text = "Sate Madura"
            itemRate.text = "4.5"

            itemView.setOnClickListener {
                val detailPage = Intent(context, DetailActivity::class.java)
                context.startActivity(detailPage)
            }
        }
    }

    override fun getItemCount(): Int = 10

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context: Context = itemView.context
        var itemImage: ImageView = itemView.findViewById(R.id.item_image)
        var itemName: TextView = itemView.findViewById(R.id.item_name)
        var itemRate: TextView = itemView.findViewById(R.id.item_rate)
    }
}