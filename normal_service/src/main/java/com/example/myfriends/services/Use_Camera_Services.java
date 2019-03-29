package com.example.myfriends.services;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Use_Camera_Services extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use__camera__services);

       imageView=findViewById(R.id.posterId);

    }

    public void useCamera(View view)
    {
        Intent intent=new Intent(Intent.ACTION_CAMERA_BUTTON);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100)
        {
            Bitmap photo= (Bitmap) data.getExtras().get("harsh");
            imageView.setImageBitmap(photo);

        }
    }
}
