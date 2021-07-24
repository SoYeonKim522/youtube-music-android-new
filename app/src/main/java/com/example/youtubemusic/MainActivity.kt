package com.example.youtubemusic

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ServiceCompat.stopForeground
import androidx.core.content.ContextCompat
import com.example.youtubemusic.databinding.ActivityMainBinding
import com.example.youtubemusic.databinding.ActivityPlayMusicBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        Log.d("log", "onCreate")

        setTheme(R.style.Theme_YoutubeMusic)  //set back to normal theme before setContentView
        setContentView(binding.root)

        //클릭 시 화면이동
        binding.fav2Img.setOnClickListener{
            intent = Intent(this, PlayMusicActivity::class.java)
            startActivity(intent)
        }

        //서비스  시작
//        binding.fav2Img.setOnClickListener{
//            intent = Intent(this, PlayMusicService::class.java)
//            ContextCompat.startForegroundService(this, intent)
//        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("log", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("log", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("log","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("log","onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("log","onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("log","onDestroy")

        //Foreground 서비스 종료
        //stopService(Intent(this, PlayMusicService::class.java))

        //앱이 종료되었을 때만 포지션바가 초기화됨!!
        val prefs =  getSharedPreferences("prefs",  0)
        Log.d("log", "onDestroy  prefs: $prefs")
        val edit = prefs.edit()
        edit.clear().apply()
    }
}