package com.company.capstoneapp.ui.adapter

import android.content.Intent
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.company.capstoneapp.R
import com.company.capstoneapp.ui.DetailActivity

class ListCulinaryAroundAdapter : RecyclerView.Adapter<ListCulinaryAroundAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_culinary_around, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.apply {
            itemImage.setImageResource(R.drawable.food_dummy_2)
            itemName.text = "Nasi Kuning"
            itemRate.text = "4.2"

            itemView.setOnClickListener {
                val detailPage = Intent(context, DetailActivity::class.java)
                context.startActivity(detailPage)
            }
        }

        if (position == 9) { // beri margin right untuk item yang terakhir
            val params = holder.itemCard.layoutParams as RecyclerView.LayoutParams
            params.rightMargin = 40
            holder.itemCard.layoutParams = params
        }
    }

    override fun getItemCount(): Int = 10

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context: Context = itemView.context
        var itemCard: CardView = itemView.findViewById((R.id.card_view))
        var itemImage: ImageView = itemView.findViewById(R.id.item_image)
        var itemName: TextView = itemView.findViewById(R.id.item_name)
        var itemRate: TextView = itemView.findViewById(R.id.item_rate)
    }
}