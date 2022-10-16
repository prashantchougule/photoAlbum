package com.example.photoalbum.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.photoalbum.R
import com.example.photoalbum.databinding.AlbumCardBinding
import com.example.photoalbum.presentation.uistate.AlbumItemUIState

class AlbumsListAdapter(): RecyclerView.Adapter<AlbumsListAdapter.ViewHolder>() {
    private var data: List<AlbumItemUIState> = emptyList()
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(albumItemUIState: AlbumItemUIState) {
            val bind = AlbumCardBinding.bind(itemView)

            bind.apply {
                photoTitle.text = albumItemUIState.photoTitle
                albumName.text = albumItemUIState.albumName
                userName.text = albumItemUIState.userName

                //Added to fix issue in Glide to load images from placehoder.com site
                // This must be added in ViewModel while generating data using mapper(Added here to fix testcase)
                val url = GlideUrl(
                    albumItemUIState.photoUrl, LazyHeaders.Builder()
                        .addHeader("User-Agent", "your-user-agent")
                        .build())

                Glide.with(albumImage.context)
                    .load(url)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(albumImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.album_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun setData(newsList: List<AlbumItemUIState>) {
        this.data = newsList
    }
}