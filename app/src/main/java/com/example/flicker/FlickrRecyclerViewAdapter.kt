package com.example.flicker

import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FlickrImageViewHolder(view: View):RecyclerView.ViewHolder(view){
    var thumbNail:ImageView = view.findViewById(R.id.thumbNail)
    var title: TextView = view.findViewById(R.id.titleBrowse)
}
class FlickrRecyclerViewAdapter(private var photoList:List<Photo>):RecyclerView.Adapter<FlickrImageViewHolder>() {
    private val tag = "FlickrAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse,parent,false)
        return FlickrImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
        val photoItem = photoList[position]
        val load = Picasso.get().load(photoItem.image)
                .error(R.drawable.baseline_image_black_48dp)
                .placeholder(R.drawable.baseline_image_black_48dp)
                .into(holder.thumbNail)
        holder.title.text = photoItem.title
    }

    override fun getItemCount(): Int {
        return if(photoList.isNotEmpty()) photoList.size else 0
    }
    fun loadNewData(newPhotos:List<Photo>){
        photoList = newPhotos
        notifyDataSetChanged()
    }
    fun getPhoto(position:Int):Photo?{
        return if(photoList.isNotEmpty()) photoList[position] else null
    }
}