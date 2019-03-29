package com.technohack.remotebinding_app2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bindServiceBtn,unBindServiceBtn,getNumberBtn;
    private TextView showRandomNumText;

    Messenger randomNumberRequestMessenger, randomNumberReceiveMessenger;

    Intent serviceIntent;
    private boolean mIsBind;
    private int randomNumberValue;
    public static final int GET_RANDOM_NUMBER_FLAG=0;
    class RecieveRandomNumberHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            randomNumberValue=0;
            switch (msg.what){
                case GET_RANDOM_NUMBER_FLAG:
                    //getting the random number
                    randomNumberValue=msg.arg1;
                    //to show the random number
                    showRandomNumText.setText("Random Number:"+randomNumberValue);
                    break;
                    default:
                        break;

            }
            super.handleMessage(msg);
        }
    }

    ServiceConnection randomNumberServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {

                   randomNumberRequestMessenger=new Messenger(iBinder);

                   randomNumberReceiveMessenger=new Messenger(new RecieveRandomNumberHandler());
                   mIsBind=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
                randomNumberRequestMessenger=null;
                randomNumberReceiveMessenger=null;
                mIsBind=false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindServiceBtn=findViewById(R.id.main_bind_service_btnId);
        unBindServiceBtn=findViewById(R.id.main_unbind_service_BtnId);
        getNumberBtn=findViewById(R.id.main_get_num_BtnId);
        showRandomNumText=findViewById(R.id.main_random_Num_textId);

        bindServiceBtn.setOnClickListener(this);
        unBindServiceBtn.setOnClickListener(this);
        getNumberBtn.setOnClickListener(this);

        serviceIntent=new Intent();
        //to connect the client side app to server side app using setComponent method by providing the package name and the class name of that app
        serviceIntent.setComponent(new ComponentName("com.technohack.remotebinding_app1","com.technohack.remotebinding_app1.RemoteServiceExample"));
        //to provide the packageName
        serviceIntent.setPackage(getPackageName());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.main_bind_service_btnId:
                          bindToRemoteService();
                             break;
            case R.id.main_unbind_service_BtnId:
                      unBindFromRemoteDevice();
                           break;
            case R.id.main_get_num_BtnId:
                      fetchRandomNumber();
                break;
                default:
                    break;

        }
    }

    //to bind the service
    private void bindToRemoteService() {
        // it will bind the service and then start the service automatically
       bindService(serviceIntent,randomNumberServiceConnection,BIND_AUTO_CREATE);
        Toast.makeText(this, "Service Bind", Toast.LENGTH_SHORT).show();
    }
    //to unBind Service
    private void unBindFromRemoteDevice() {
        unbindService(randomNumberServiceConnection);
        Toast.makeText(this, "Service unBind", Toast.LENGTH_SHORT).show();
    }

    //to get the random number
    private void fetchRandomNumber() {
         if(mIsBind==true){
             Message requestMessage=Message.obtain(null,GET_RANDOM_NUMBER_FLAG);
             requestMessage.replyTo=randomNumberReceiveMessenger;
             try{
                 randomNumberRequestMessenger.send(requestMessage);
             }catch (RemoteException e) {
                 e.printStackTrace();
             }
         }else{
             Toast.makeText(this, "Service Unbound,can't get random number", Toast.LENGTH_SHORT).show();
         }
     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        randomNumberServiceConnection=null;
    }
}
