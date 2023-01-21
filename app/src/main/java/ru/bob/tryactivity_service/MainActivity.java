package ru.bob.tryactivity_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ForCalback{
    private MyBroadcastReceiver mMyBroadcastReceiver;
    TextView CurVal;
    Intent myIntentService_Count;
    Intent Intent_ACTION_Start;
    Intent Intent_ACTION_Stop;
    android.content.ComponentName E_ComponentName;
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CurVal =findViewById(R.id.CurVal);
        InittService();

        // регистрируем BroadcastReceiver
        mMyBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(
                MyIntentService_Count.ACTION_MYINTENTSERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mMyBroadcastReceiver, intentFilter);
    }//protected void onCreate(Bundle savedInstanceState)

    public void onMyButtonClick(View view){
        switch (view.getId()){
            case R.id.startService:
                InittService();
                startService(myIntentService_Count);
                break;
            case R.id.ACTION_Start:
                sendBroadcast(Intent_ACTION_Start);
                break;
            case R.id.ACTION_Stop:
                sendBroadcast(Intent_ACTION_Stop);
                break;
            case R.id.Get_I:

                break;
        }

    }
    private void InittService(){
         myIntentService_Count = new Intent(this, MyIntentService_Count.class);
//         Intent_ACTION_Start = new Intent(MyIntentService_Count.ACTION_Start,null, this,MyIntentService_Count.class);
         Intent_ACTION_Start = new Intent(MyIntentService_Count.ACTION_Start);
         Intent_ACTION_Stop = new Intent(MyIntentService_Count.ACTION_Stop,null, this,MyIntentService_Count.class);
//         Intent_ACTION_Stop = new Intent(MyIntentService_Count.ACTION_Stop);
         Intent_ACTION_Start.addCategory(Intent.CATEGORY_DEFAULT);
         Intent_ACTION_Stop.addCategory(Intent.CATEGORY_DEFAULT);
    }

    @Override
    public void Cur_I(int i) {

    }
}