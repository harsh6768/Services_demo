package com.technohack.intentservicedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button startIntentServiceBtn,stopIntentServiceBtn;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startIntentServiceBtn=findViewById(R.id.main_start_intent_serviceBtnId);
        stopIntentServiceBtn=findViewById(R.id.main_stop_intent_service_BtnId);

        startIntentServiceBtn.setOnClickListener(this);
        stopIntentServiceBtn.setOnClickListener(this);

        //to going to the Intent service class
        serviceIntent=new Intent(this,MyIntentService.class);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_start_intent_serviceBtnId:
                startService(serviceIntent);
                break;
            case R.id.main_stop_intent_service_BtnId:
                stopService(serviceIntent);
                break;
                default:
                    break;

        }
    }
}
