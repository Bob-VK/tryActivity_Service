package ru.bob.tryactivity_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.appcompat.app.AppCompatActivity;

public  class ServiceTstStateRequest {
    public static final String StateRunRequest = "ru.bob.tryactivity_service.action.StateRunRequest";
    public static final String StateRunResponse = "ru.bob.tryactivity_service.action.StateRunResponse";
//    public static final String ActivityStarted = "ru.bob.tryactivity_service.action.ActivityStarted";
    public static final String ActivityDestroy = "ru.bob.tryactivity_service.action.ActivityDestroy";
    public Intent ActivityDestroy_I = new Intent(ActivityDestroy).addCategory(Intent.CATEGORY_DEFAULT);


    public static final int pause_send = 500;// частота свежих данных
    private int count_Wait_max = 10;
    //!!!reqvest boolean IntentService_started = false;

    // Идентификатор уведомления
    public static final int NOTIFY_ID = 101;

    // Идентификатор канала
    public static String CHANNEL_ID = "Cat channel";

    private AppCompatActivity AppCompatActivity_E;
//!!!reqvest    BroadcastReceiver_ServiceTst BroadcastReceiver_ServiceTst_E ;
    public ServiceTstStateRequest(MainActivity AppCompatActivity_p) {
        AppCompatActivity_E=AppCompatActivity_p;
/*!!!reqvest
      IntentFilter intentFilter = new IntentFilter(
                StateRunResponse);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        BroadcastReceiver_ServiceTst_E= new BroadcastReceiver_ServiceTst();
        AppCompatActivity_p.registerReceiver(BroadcastReceiver_ServiceTst_E, intentFilter);*/
        SendRequest();
    }
    public void SendRequest(){
        Intent StateRunRequest_I = new Intent(StateRunRequest);
        StateRunRequest_I.addCategory(Intent.CATEGORY_DEFAULT);
        AppCompatActivity_E.sendBroadcast(StateRunRequest_I);
    }

    public boolean Service_Not_running = true;
   /*!!!!!reqvest
        public class BroadcastReceiver_ServiceTst extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Service_Not_running =false;//теперь знаем, что сервис запущен
        }
    }*/
    public Whait_Service_signal Whait_Service_signal_E ;
    public void Start_Service(){
        SendRequest();
        Whait_Service_signal_E = new Whait_Service_signal();
        Whait_Service_signal_E.start();

    }
    public class Whait_Service_signal extends Thread{
        @Override
        public void run() {
            try {
                sleep(pause_send);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Service_Not_running){
                Intent myIntentService_Count = new Intent(AppCompatActivity_E, MyIntentService_Count.class);
                AppCompatActivity_E.startService(myIntentService_Count);
            }
        }//public void run()
    }//private class Whait_Service_signal
/*!!!!!reqv
    public void UnregisterService(){
        AppCompatActivity_E.unregisterReceiver(BroadcastReceiver_ServiceTst_E);
    }*/
}

