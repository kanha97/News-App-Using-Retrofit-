package com.devkanhaiya.newsappwithretrofit

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewAdapter(
        private val titles: List<String>,
        private val details: List<String>,
        private val images: List<String>,
        private val links: List<String>
): RecyclerView.Adapter<RecyclerViewAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title_view:TextView = itemView.findViewById(R.id.tv_title)
        val image_view:ImageView = itemView.findViewById(R.id.iv_image)
        val details_view:TextView = itemView.findViewById(R.id.tv_description)



        init {
            itemView.setOnClickListener { v: View ->
                val position = adapterPosition
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(links[position])
                startActivity(itemView.context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return NewsViewHolder(v)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.title_view.text = titles[position]
        holder.details_view.text = details[position]

        Glide.with(holder.image_view)
                .load(images[position])
                .into(holder.image_view)
    }

    override fun getItemCount(): Int {
       return images.size
    }
}