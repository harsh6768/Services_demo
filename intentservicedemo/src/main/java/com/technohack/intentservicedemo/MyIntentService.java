package com.technohack.intentservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

import static android.support.constraint.Constraints.TAG;

public class MyIntentService extends IntentService {
    private  int mRandomNumber;
    private  boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private  final int MAX=100;


    public MyIntentService(){
        super(null);

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //by using this method we won't have to use the another thread because it will automatically invoke into the another thread
    @Override
    protected void onHandleIntent( @Nullable Intent intent) {
              mIsRandomGeneratorOn=true;
              startRandomNumberGenerator();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRandomNumberGenerator();
        Log.i(TAG, "onDestroy: Service Stopped");

    }

    //to generate the randomNumber
    public void startRandomNumberGenerator(){

        while(mIsRandomGeneratorOn){
            try{
                //we don't want to generate the random number very quickly
                Thread.sleep(1000);
                if(mIsRandomGeneratorOn){
                    mRandomNumber=new Random().nextInt(MAX)+MIN;
                    Log.i(TAG, "startRandomNumberGenerator: Thread id: "+Thread.currentThread().getId()+" Random Number:"+mRandomNumber);
                }
            }
            catch (InterruptedException e){
                Log.i(TAG, "Thread Interrupted!!!");
            }
        }
    }

    public void stopRandomNumberGenerator(){
        mIsRandomGeneratorOn=false;
    }

    public int getmRandomNumber(){
        return mRandomNumber;
    }

}
