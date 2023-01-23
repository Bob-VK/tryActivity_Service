package ru.bob.tryactivity_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private MyBroadcastReceiver mMyBroadcastReceiver;
    TextView CurVal;
    Intent myIntentService_Count;
    Intent Intent_ACTION_Start;
    Intent Intent_ACTION_Stop;
    ServiceTstStateRequest ServiceTst_StateReqwest_E ;
    int count_Wait_max =3;

/*    ServiceConnection sConn;
    boolean bound;*/
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent
                    .getStringExtra(MyIntentService_Count.EXTRA_KEY_OUT);
            CurVal.setText(result);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyBroadcastReceiver);
        ServiceTst_StateReqwest_E.UnregisterService();
        sendBroadcast(ServiceTst_StateReqwest_E.ActivityDestroy_I);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServiceTst_StateReqwest_E = new ServiceTstStateRequest(this);
        CurVal =findViewById(R.id.CurVal);
        InittService();
/*        // подготовка к биндингу
        String LOG_TAG ="-- onServiceConnected--";
        sConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(LOG_TAG, "MainActivity onServiceConnected");
                bound = true;
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                bound = false;
            }
        };//sConn = new ServiceConnection();*/
        // регистрируем BroadcastReceiver
        mMyBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(
                MyIntentService_Count.ACTION_MYINTENTSERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mMyBroadcastReceiver, intentFilter);
    }//protected void onCreate(Bundle savedInstanceState)

    public void onMyButtonClick(View view){
        switch (view.getId()){
            case R.id.startService:{
                InittService();
                startService(myIntentService_Count);
                break;}
            case R.id.ACTION_Start:{
                    sendBroadcast(Intent_ACTION_Start);
                break;}
            case R.id.ACTION_Stop:{
                   sendBroadcast(Intent_ACTION_Stop);
                break;}
            case R.id.SendRequest:{
                ServiceTst_StateReqwest_E.SendRequest();
/*
                boolean cc = bindService(myIntentService_Count, sConn, 0);
                Log.d("--cc--",String.valueOf(cc));
*/
                 break;}
            case R.id.I_destroy:{
                sendBroadcast(ServiceTst_StateReqwest_E.ActivityDestroy_I);
                break;}
                }//switch (view.getId())

    }//public void onMyButtonClick(View view
    private void InittService(){
         myIntentService_Count = new Intent(this, MyIntentService_Count.class);
//         Intent_ACTION_Start = new Intent(MyIntentService_Count.ACTION_Start,null, this,MyIntentService_Count.class);
         Intent_ACTION_Start = new Intent(MyIntentService_Count.ACTION_Start);
//         Intent_ACTION_Stop = new Intent(MyIntentService_Count.ACTION_Stop,null, this,MyIntentService_Count.class);
         Intent_ACTION_Stop = new Intent(MyIntentService_Count.ACTION_Stop);
         Intent_ACTION_Start.addCategory(Intent.CATEGORY_DEFAULT);
         Intent_ACTION_Stop.addCategory(Intent.CATEGORY_DEFAULT);
    }
    private class Whait_Service_signal extends Thread{


        @Override
        public void run() {
            int count_Wait=0;
            while (ServiceTst_StateReqwest_E.Service_Count_running && count_Wait < count_Wait_max){
                count_Wait++;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!ServiceTst_StateReqwest_E.Service_Count_running){

                }

            }
        }
    }
}