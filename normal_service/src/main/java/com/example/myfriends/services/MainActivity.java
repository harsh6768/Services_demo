package com.example.myfriends.services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button startBt,stopBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBt=findViewById(R.id.startBtId);
        stopBt=findViewById(R.id.stopButtonId);

        startBt.setOnClickListener(this);
        stopBt.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.startBtId:
                // to start the sevices we ned to add the make the another activity which will handle the services
                Toast.makeText(MainActivity.this, "Working", Toast.LENGTH_SHORT).show();
                startService(new Intent(this,MyServices.class));
                break;
            case R.id.stopButtonId:
                stopService(new Intent(this,MyServices.class));
                break;
        }
    }


    public void onCamera(View view)
    {

        Intent intent=new Intent(this,Use_Camera_Services.class);
        startActivity(intent);

    }
}























