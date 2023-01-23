package ru.bob.tryactivity_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.appcompat.app.AppCompatActivity;

public  class ServiceTstStateRequest {
    public static final String StateRunRequest = "ru.bob.tryactivity_service.action.StateRunRequest";
    public static final String StateRunResponse = "ru.bob.tryactivity_service.action.StateRunResponse";
    private AppCompatActivity AppCompatActivity_E;
    BroadcastReceiver_ServiceTst BroadcastReceiver_ServiceTst_E = new BroadcastReceiver_ServiceTst();
    public ServiceTstStateRequest(AppCompatActivity AppCompatActivity_p) {
        AppCompatActivity_E=AppCompatActivity_p;
        IntentFilter intentFilter = new IntentFilter(
                StateRunResponse);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        AppCompatActivity_p.registerReceiver(BroadcastReceiver_ServiceTst_E, intentFilter);
        SendRequest();
    }
    public void SendRequest(){
        Intent StateRunRequest_I = new Intent(StateRunRequest);
        StateRunRequest_I.addCategory(Intent.CATEGORY_DEFAULT);
        AppCompatActivity_E.sendBroadcast(StateRunRequest_I);
    }

    public boolean Service_Count_running = false;
    public class BroadcastReceiver_ServiceTst extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Service_Count_running =true;//теперь знаем, что сервис запущен
        }
    }
    public void UnregisterService(){
        AppCompatActivity_E.unregisterReceiver(BroadcastReceiver_ServiceTst_E);
    }
}

