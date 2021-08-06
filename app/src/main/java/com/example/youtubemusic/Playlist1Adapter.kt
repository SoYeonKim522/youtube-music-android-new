package com.example.youtubemusic

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.youtubemusic.databinding.Playlist1ItemBinding

class Playlist1Adapter(context: Context,  private val playlist1ArrayList: ArrayList<PlayList1>):BaseAdapter() {
    private val inflater  =  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as  LayoutInflater
    lateinit var binding: Playlist1ItemBinding

    override fun getCount(): Int = playlist1ArrayList.size

    override fun getItem(position: Int): Any =playlist1ArrayList[position]

    override fun getItemId(position: Int): Long =position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        binding = Playlist1ItemBinding.inflate(inflater, parent,  false)
        binding.songTitle.text = playlist1ArrayList[position].title
        binding.songSinger.text = playlist1ArrayList[position].singer
        binding.songDuration.text = playlist1ArrayList[position].duration
        binding.albumImg.setImageResource(playlist1ArrayList[position].albumImg)

        return  binding.root
    }


}