package com.technohack.remotebinding_app1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startServiceBtn,stopServiceBtn;

    Intent serviceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startServiceBtn=findViewById(R.id.main_start_service_BtnId);
        stopServiceBtn=findViewById(R.id.main_stop_service_BtnId);

        startServiceBtn.setOnClickListener(this);
        stopServiceBtn.setOnClickListener(this);

        serviceIntent=new Intent(this,RemoteServiceExample.class);

    }

    @Override
    public void onClick(View v) {
        //int id=v.getId();
        switch (v.getId()){
            case R.id.main_start_service_BtnId:
                       startService(serviceIntent);
                      break;
                      case R.id.main_stop_service_BtnId:
                      stopService(serviceIntent);
                      break;
                      default:
                          break;

        }
    }
}
