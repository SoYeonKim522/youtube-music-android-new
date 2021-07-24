package com.example.youtubemusic

import android.app.*
import android.content.ComponentName
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.SeekBar
import androidx.core.app.NotificationCompat

class PlayMusicService : Service() {
    val CHANNEL_ID = "foreground_channel"
    val NOTIFICATION_ID = 1

    private lateinit var mp: MediaPlayer
    private var totalTime: Int  = 0
    private var nowPlaying:Boolean = false
    private var playFromStart = false

    override fun onCreate() {
        //init Music
        mp =  MediaPlayer.create(this, R.raw.dancing_in_the_kitchen)
        mp.isLooping = true
        totalTime = mp.duration

        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //음악 시작

        //Notification channel
        createNotificationChannel()

        //show notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("On Youtube Music")
            .setSmallIcon(R.drawable.yt_music_logo)
            .build()

        startForeground(NOTIFICATION_ID, notification)
        return START_NOT_STICKY
    }

//    private fun showNotification() {
//        val notificationIntent = Intent(this, MainActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, 0)
//        val  notification = Notification.Builder(this,  CHANNEL_ID)
//            .setContentText("On Youtube Music").setSmallIcon(R.drawable.yt_music_logo)
//            .setContentIntent(pendingIntent)
//            .build()
//    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(CHANNEL_ID, "foreground",NotificationManager.IMPORTANCE_DEFAULT)  //채널만들기
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)  //채널 등록
        }
    }


    override fun onBind(intent: Intent?): IBinder? {
        return  Binder()
    }


}