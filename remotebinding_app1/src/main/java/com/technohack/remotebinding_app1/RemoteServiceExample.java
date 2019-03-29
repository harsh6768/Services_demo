package com.technohack.remotebinding_app1;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.constraint.Constraints;
import android.util.Log;
import android.widget.Toast;


import java.util.Random;

import static android.content.ContentValues.TAG;

public class RemoteServiceExample extends Service {

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;
    private  final int MIN=0;
    private final int MAX=100;

    public static final int GET_RANDOM_NUMBER_FLAG =0;

    private class RandomNumberRequestHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case GET_RANDOM_NUMBER_FLAG:
                    Message messageSendRandomNumber=Message.obtain(null,GET_RANDOM_NUMBER_FLAG);
                    messageSendRandomNumber.arg1=getRandomNumber();

                    try{
                        msg.replyTo.send(messageSendRandomNumber);
                    }catch (RemoteException e){
                        Log.i(TAG, "handleMessage:"+e.getMessage());
                    }

            }
            super.handleMessage(msg);
        }
    }
    //to handle the request from the another app
    private Messenger randomNumberMessenger=new Messenger(new RandomNumberRequestHandler());


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: "+intent.getPackage());
        if(intent.getPackage().equals("com.technohack.remotebinding_app2")){
            Toast.makeText(this, "Correct Package", Toast.LENGTH_SHORT).show();
            //we don't need to  make the class to get the IBinder Object
            return randomNumberMessenger.getBinder();
        }else{
            Toast.makeText(this, "Wrong Package", Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mIsRandomGeneratorOn=true;
        //to run it in another thread because service run in UI THREAD
        new Thread(new Runnable() {
            @Override
            public void run() {
             startRandomNumberGenerator();
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "onRebind: ");
        super.onRebind(intent);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.i(TAG, "unbindService: ");
        super.unbindService(conn);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRandomNumberGenerator();
        Log.i(Constraints.TAG, "onDestroy: Service Stopped");

    }

    //to generate the randomNumber
    public void startRandomNumberGenerator(){

        while(mIsRandomGeneratorOn){
            try{
                //we don't want to generate the random number very quickly
                Thread.sleep(1000);
                if(mIsRandomGeneratorOn){
                    mRandomNumber=new Random().nextInt(MAX)+MIN;
                    Log.i(Constraints.TAG, "startRandomNumberGenerator: Thread id: "+Thread.currentThread().getId()+" Random Number:"+mRandomNumber);
                }
            }
            catch (InterruptedException e){
                Log.i(Constraints.TAG, "Thread Interrupted!!!");
            }
        }
    }

    public void stopRandomNumberGenerator(){
        mIsRandomGeneratorOn=false;
    }

    public int getRandomNumber(){
        return mRandomNumber;
    }


}
