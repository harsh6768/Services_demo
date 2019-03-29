package com.technohack.boundservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

import static android.support.constraint.Constraints.TAG;

public class BoundServiceExample extends Service {

    private  int mRandomNumber;
    private  boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private  final int MAX=100;

    //we can also implements IBinder interface
    //In local binding we use IBinder but for remote binding we use Messenger and AIDL
    class MyServiceBinder extends Binder {
        BoundServiceExample getService(){
            return BoundServiceExample.this;
        }
    }
    public IBinder mBinder=new MyServiceBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "onRebind:");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");

        mIsRandomGeneratorOn=true;
        // We know services run in main thread so we don't want that our service will block the main UI so will use new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                //to generate the random number
                  startRandomNumberGenerator();
            }
         }).start();
       //services will automatically start
        //if the app killed during resource crunching
        return START_STICKY;

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
