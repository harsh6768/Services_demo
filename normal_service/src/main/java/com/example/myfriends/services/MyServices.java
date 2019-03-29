package com.example.myfriends.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MyServices extends Service {

    /*
    We need to extends the Service class to start the services or destroy the services

     */
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer=MediaPlayer.create(this,R.raw.abc);
        mediaPlayer.setLooping(false);
        
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
    }


}
