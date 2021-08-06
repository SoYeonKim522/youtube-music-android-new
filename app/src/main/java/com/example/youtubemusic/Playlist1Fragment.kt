package com.example.youtubemusic

import android.graphics.Point
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.youtubemusic.databinding.FragmentLibraryBinding
import com.example.youtubemusic.databinding.FragmentPlaylist1Binding

data class PlayList1(val title:String, val singer:String, val duration:String, val albumImg:Int)

class Playlist1Fragment : Fragment() {

    private var  mBinding: FragmentPlaylist1Binding?  =null
    private val  binding  get()  = mBinding!!
    private lateinit var playlist1Adapter: Playlist1Adapter
    private var playlist1ArrayList  =  ArrayList<PlayList1>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding  =  FragmentPlaylist1Binding.inflate(inflater, container,false)

        for (x in 0..9){
            playlist1ArrayList.add(PlayList1("What would you do","Honne", "2:55", R.drawable.what_would_you_do))
            playlist1ArrayList.add(PlayList1("The Way You Felt","Alec Benjamin", "3:03", R.drawable.comethru))
        }

        playlist1Adapter = Playlist1Adapter(requireContext(), playlist1ArrayList)
        binding.listviewPlaylist1.adapter = playlist1Adapter

        //스크롤뷰 안에 리스트뷰 있어서 생기는 문제 때문에
        binding.listviewPlaylist1.isNestedScrollingEnabled=false


        return binding.root
    }


}