package com.example.youtubemusic

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaTimestamp
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.youtubemusic.databinding.ActivityMainBinding
import com.example.youtubemusic.databinding.ActivityPlayMusicBinding

class PlayMusicActivity: AppCompatActivity() {
    private lateinit var binding:ActivityPlayMusicBinding
    private lateinit var mp: MediaPlayer
    private var totalTime: Int  = 0
    private var nowPlaying:Boolean = false
    private var playFromStart = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("log", "music_onCreate")
        binding = ActivityPlayMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //play & pause music
        mp =  MediaPlayer.create(this, R.raw.dancing_in_the_kitchen)
        mp.isLooping = true   //기본설정으로  반복재생
        totalTime = mp.duration


        //position bar   _mp 선언보다 위에 있으면 seekbar 안 움직임
        binding.positionBar.max = totalTime
        binding.positionBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {if(fromUser) {
                    mp.seekTo(progress) //progress : onProgressChanged 함수의 매개변수
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {//seekbar 터치로 조절 시작
                }
                override fun onStopTrackingTouch(seekBar: SeekBar?) {//seekbar 터치로 조절 종료
                }
            }
        )


        //Log.d("log", "oncreate - loadData 전 nowplaying $nowPlaying")


        //loadData 안에서 전역변수 nowPlaying T/F 판단 후 playFromStart T/F 설정
        loadData()


        binding.playBtn.setOnClickListener {
            nowPlaying = if(mp.isPlaying){
                //pause
                mp.pause()
                binding.playBtn.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)
                false
                //isPaused=true
                //firstEntry=false
            } else {
                //start
                mp.start()
                binding.playBtn.setBackgroundResource(R.drawable.ic_baseline_pause_24)
                true
                //isPaused=false
                //firstEntry=false
            }
        }


        @SuppressLint("HandlerLeak")
        val handler = object : Handler(){
            override fun handleMessage(msg: Message) {
                val currentPosition = msg.what

                //update positionBar every second
                binding.positionBar.progress = currentPosition

                //update labels
                val elapsedTime = createTimeLabel(currentPosition)
                binding.elapsedTimeLabel.text = elapsedTime

                val totalDurationTime = createTimeLabel(totalTime)
                binding.totalTimeLabel.text = totalDurationTime
            }
        }

        //작업스레드
        Thread(Runnable {
            while (mp != null){
                try{
                    var msg = Message()
                    msg.what = mp.currentPosition  //현재 위치를 millisec으로 나타냄(int)
                    handler.sendMessage(msg)  //핸들러로 메시지 보내고
                    Thread.sleep(1000)  //1초 쉬었다가 또 메시지보내고 반복 (while문 false될때까지)
                } catch (e: InterruptedException){
                }
            }
        }).start()   //start 메소드 호출해야 스레드가 실행
    }

    private fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        val min = time / 1000 / 60
        val sec = time / 1000  % 60

        timeLabel = "$min:"
        if(sec<10)  timeLabel  +="0"
        timeLabel+=sec

        return  timeLabel
    }

    private fun loadData() {
        val prefs =  getSharedPreferences("prefs",  0)
        Log.d("log", "loadData  prefs $prefs")
        var pausedPosition =  prefs.getInt("pausedPosition",  0)
        var nowPlaying = prefs.getBoolean("isNowPlaying",  false)
        var nowPlayingPosition = prefs.getInt("nowPlayingPosition", 0)
        //isPaused = prefs.getBoolean("isPaused",  false)
        //firstEntry = prefs.getBoolean("isFirstEntry",true)

        Log.d("log", "loadData - nowPlaying $nowPlaying")


            if(!nowPlaying  &&  pausedPosition  < 2000){
                playFromStart=true          //처음부터 재생
            }else  if(!nowPlaying  && pausedPosition  >=  2000){
                Log.d("log", "isPaused TRUE -> 일시정지했던 곳으로")
                mp.seekTo(pausedPosition)
            }
            else if(nowPlaying) {
                Log.d("log", "nowPlaying TRUE -> 이미 재생중인 정보 불러오기")
                Log.d("log", "현재 재생 위치 : $nowPlayingPosition")
                mp.seekTo(nowPlayingPosition)
        }
    }



    private fun saveData() {
        val prefs =  getSharedPreferences("prefs",  0)
        val edit = prefs.edit()  //수정모드로
        if(!nowPlaying) {
            Log.d("log", "saveData: now playing FALSE로 저장")
            edit.putInt("pausedPosition", mp.currentPosition)    //키값과 실제 저장할 값
            //edit.putBoolean("isPaused", isPaused)
            //edit.putBoolean("isFirstEntry", firstEntry)
            edit.putBoolean("isNowPlaying", nowPlaying)
        } else if(nowPlaying) {
            Log.d("log", "saveData: now playing TRUE로 저장")
            edit.putBoolean("isNowPlaying", nowPlaying)  //destroy되어도 현재 재생중이면 그 사실을 저장
            edit.putInt("nowPlayingPosition",mp.currentPosition)
        }
        edit.apply()  //값 저장 완료
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("log", "music_onDestroy")

        saveData()   //백버튼 눌렀을 때 정보 저장
    }

    override fun onStart() {
        super.onStart()
        Log.d("log", "music_onStart")
    }


    override fun onResume() {
        super.onResume()
        Log.d("log", "music_onResume")
        if(playFromStart){
            mp.start()   //바로 재생되도록 하는 세줄
            binding.playBtn.setBackgroundResource(R.drawable.ic_baseline_pause_24)
            nowPlaying=true
        }else if(nowPlaying){
            Log.d("log", "onstart - 이미 재생중")
            //mp.start()
            binding.playBtn.setBackgroundResource(R.drawable.ic_baseline_pause_24)
            //isPaused=false
            //firstEntry=false
        }else if(!nowPlaying){
            Log.d("log", "onstart - 이미 재생중 아님")
            //isPaused=false
            //firstEntry=false
        }
    }

    //    FOR  LOG
    override fun onPause() {
        super.onPause()
        Log.d("log","music_onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("log","music_onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("log","music_onRestart")
    }

    override fun onBackPressed() { //뒤로가기 버튼 누르자마자 바로제일먼저!
        super.onBackPressed()
        //Log.d("log","music_on back pressed")
    }
}

