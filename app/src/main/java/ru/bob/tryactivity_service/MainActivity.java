package ru.bob.tryactivity_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private MyBroadcastReceiver mMyBroadcastReceiver;
    TextView CurVal;

    Intent Intent_ACTION_Start;
    Intent Intent_ACTION_Stop;
    ServiceTstStateRequest ServiceTst_StateReqwest_E ;


/*    ServiceConnection sConn;
    boolean bound;*/
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                ServiceTst_StateReqwest_E.Service_Not_running = false;//если хоть что-то пришло

                final String action = intent.getAction();
                switch(action){
                    case MyIntentService_Count.ACTION_MYINTENTSERVICE: {
                        String result = intent
                                .getStringExtra(MyIntentService_Count.EXTRA_KEY_OUT);
                        CurVal.setText(result);
                        break;
                    }
                }
            }//if (intent != null)

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyBroadcastReceiver);
//!!!!!reqv        ServiceTst_StateReqwest_E.UnregisterService();
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
        intentFilter.addAction(ServiceTstStateRequest.StateRunResponse);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mMyBroadcastReceiver, intentFilter);
    }//protected void onCreate(Bundle savedInstanceState)

    public void onMyButtonClick(View view){
        switch (view.getId()){
            case R.id.startService:{
                InittService();
                ServiceTst_StateReqwest_E.Start_Service();
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

//         Intent_ACTION_Start = new Intent(MyIntentService_Count.ACTION_Start,null, this,MyIntentService_Count.class);
         Intent_ACTION_Start = new Intent(MyIntentService_Count.ACTION_Start);
//         Intent_ACTION_Stop = new Intent(MyIntentService_Count.ACTION_Stop,null, this,MyIntentService_Count.class);
         Intent_ACTION_Stop = new Intent(MyIntentService_Count.ACTION_Stop);
         Intent_ACTION_Start.addCategory(Intent.CATEGORY_DEFAULT);
         Intent_ACTION_Stop.addCategory(Intent.CATEGORY_DEFAULT);
    }

}