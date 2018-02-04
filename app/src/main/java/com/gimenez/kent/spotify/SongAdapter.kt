package com.gimenez.kent.spotify

import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gimenez.kent.spotify.model.SongModel
import kotlinx.android.synthetic.main.music_layout.view.*


class SongAdapter(private val selectedItem: SparseBooleanArray, private val songList:ArrayList<SongModel>) : RecyclerView.Adapter<SongAdapter.MyViewHolder>() {

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.tvName.text = songList[position].Name
        holder.tvSinger.text = songList[position].Singer
        holder.tvAlbum.text = songList[position].Album
        holder.tvName.isSelected = selectedItem.get(position,false)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder =
            MyViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.music_layout, parent, false))

    override fun getItemCount(): Int = songList.size

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.txtTitle!!
        val tvSinger = view.txtSinger!!
        val tvAlbum = view.txtAlbum!!
    }
}