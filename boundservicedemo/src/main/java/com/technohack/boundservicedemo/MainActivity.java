package com.technohack.boundservicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    TextView showRandomNum;
    Button startServiceBtn,stopServiceBun,onBindServiceBtn,unBindServiceBtn,getRandumNumBtn;
    //for binding the service we need to use the service connection Api
    ServiceConnection serviceConnection=null;

    BoundServiceExample boundServiceExample;
    boolean isServiceBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showRandomNum=findViewById(R.id.main_randum_numId);
        startServiceBtn=findViewById(R.id.main_start_serviceId);
        stopServiceBun=findViewById(R.id.main_stop_serviceId);
        onBindServiceBtn=findViewById(R.id.main_bind_service_btnId);
        unBindServiceBtn=findViewById(R.id.main_unbind_btnId);
        getRandumNumBtn=findViewById(R.id.main_getNum_BtnId);

        startServiceBtn.setOnClickListener(this);
        stopServiceBun.setOnClickListener(this);
        onBindServiceBtn.setOnClickListener(this);
        unBindServiceBtn.setOnClickListener(this);
        getRandumNumBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id=v.getId();
        switch (id){
            case R.id.main_start_serviceId:
                startService(new Intent(this,BoundServiceExample.class));
                break;
            case R.id.main_stop_serviceId:
                stopService(new Intent(this,BoundServiceExample.class));
                break;
            case R.id.main_bind_service_btnId:
                onBindService();
                break;
            case R.id.main_unbind_btnId:
                onUnBindService();
                break;
            case R.id.main_getNum_BtnId:
                setRandomNum();
                break;
        }

    }

    private void onBindService() {

        //if service is not bound then serviceConnection variable should be
        if(!isServiceBound){
            //ServiceConnection serviceConnection;
            serviceConnection=new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder iBinder) {

                    //setting the value
                    BoundServiceExample.MyServiceBinder myServiceBinder = (BoundServiceExample.MyServiceBinder) iBinder;
                    //bind the service with serviceConnection
                    boundServiceExample = myServiceBinder.getService();
                    isServiceBound = true;

                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    isServiceBound = false;
                }
            };

            //preDefined method to bind the service
            bindService(new Intent(this,BoundServiceExample.class),serviceConnection, Context.BIND_AUTO_CREATE);

        }
    }

    //to unBind the service
    private void onUnBindService() {
         if(isServiceBound){
             unbindService(serviceConnection);
             isServiceBound=false;
         }
    }

    //to display the randomNumber in textView
    public void setRandomNum(){
        if(isServiceBound){
            showRandomNum.setText("Random Number:"+boundServiceExample.getmRandomNumber());
        }else{
            showRandomNum.setText("Service not bound");
        }
    }
}
