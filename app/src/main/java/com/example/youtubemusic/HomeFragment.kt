package com.example.youtubemusic

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.youtubemusic.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,  container, false)
        return binding.root

//        binding.fav2Img.setOnClickListener {
//            val intent = Intent(this, PlayMusicActivity::class.java)
//            startActivity(intent)
//        }
    }



}