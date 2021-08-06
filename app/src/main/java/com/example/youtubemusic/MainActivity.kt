package com.example.youtubemusic

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.View
import androidx.core.app.ServiceCompat.stopForeground
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.youtubemusic.databinding.ActivityMainBinding
import com.example.youtubemusic.databinding.ActivityPlayMusicBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //Fragments
    private val homeFragment  = HomeFragment()
    private val libraryFragment  = LibraryFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setTheme(R.style.Theme_YoutubeMusic)  //set back to normal theme before setContentView
        setContentView(binding.root)

        //클릭 시 화면이동
//        binding.fav2Img.setOnClickListener{
//            intent = Intent(this, PlayMusicActivity::class.java)
//            startActivity(intent)
//        }

        //서비스  시작
//        binding.fav2Img.setOnClickListener{
//            intent = Intent(this, PlayMusicService::class.java)
//            ContextCompat.startForegroundService(this, intent)
//        }

        makeCurrentFrag(homeFragment)  //기본값 설정

        binding.bottomNavigation.setOnItemSelectedListener{
            when(it.itemId){
                R.id.home  ->  makeCurrentFrag(homeFragment)
                R.id.library  ->  makeCurrentFrag(libraryFragment)
            }
            true
        }


        //이거 뭐지?  여기에서도  해도  되나????
        //libraryFragment.onAddPlaylistBtnClick()
    }

//    private fun makeCurrentFrag(fragment: Fragment){    //로그보려고 만든건데
//        val transaction  = supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragment_container, fragment)
//            commit()
//        }
//        Log.d("log", "makeCurrentFragment: $fragment")
//    }

    private fun makeCurrentFrag(fragment: Fragment)  =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
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

//    override fun onStart() {
//        super.onStart()
//        Log.d("log", "onStart")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.d("log", "onResume")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.d("log","onPause")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.d("log","onStop")
//    }
//
//    override fun onRestart() {
//        super.onRestart()
//        Log.d("log","onRestart")
//    }
}