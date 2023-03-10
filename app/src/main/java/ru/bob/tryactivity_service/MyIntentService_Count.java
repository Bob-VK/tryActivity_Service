package ru.bob.tryactivity_service;

import static java.lang.Thread.sleep;

import android.app.IntentService;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService_Count extends IntentService {

    public static final String EXTRA_KEY_OUT ="EXTRA_OUT" ;
    public static final String ACTION_MYINTENTSERVICE = "ru.bob.tryactivity_service.action.RESPONSE";


    private Service_Receiver Service_Receiver_E;
    private String LOG_TAG = "--MyIntentService_Count__";

    private Intent StateRunResponse_I = new Intent(ServiceTstStateRequest.StateRunResponse);

    public static final String ACTION_Start = "ru.bob.tryactivity_service.action.Start";
    public static final String ACTION_Stop = "ru.bob.tryactivity_service.action.Stop";

    public class Service_Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
            if (intent != null) {
                final String action = intent.getAction();
                switch(action){
                    case ACTION_Start:{
                        CountEnable=1;
                        break;}
                    case ACTION_Stop: {
                        CountEnable=0;
                        break;
                    }
                    case ServiceTstStateRequest.StateRunRequest: {
                        StateRunResponse_I.addCategory(Intent.CATEGORY_DEFAULT);
                        sendBroadcast(StateRunResponse_I);
                        HideNotify ();
                        break;
                    }
                    case ServiceTstStateRequest.ActivityDestroy: {
                        ShowOngoingNotify();
                        break;
                    }
                }
            }//if (intent != null)
//            throw new UnsupportedOperationException("Not yet implemented");
        }
    }
    NotificationManagerCompat notificationManager;
    private void ShowOngoingNotify (){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, ServiceTstStateRequest.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_gnome_power)
                        .setContentTitle("??????????????????????")
                        .setContentText("???????? ?????????????????? ????????")
                        .setContentIntent(contentIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                  Notification Notification_E = builder.build();
        Notification_E.flags =Notification_E.flags | Notification.FLAG_ONGOING_EVENT;

        notificationManager.notify(ServiceTstStateRequest.NOTIFY_ID,Notification_E );
    }
    private void HideNotify (){
        notificationManager.cancel(ServiceTstStateRequest.NOTIFY_ID);
    }
    public int i;
    public int CountEnable = 0;

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS


    // TODO: Rename parameters
    private static final String EXTRA_PARAM_StrSend = "ru.bob.tryactivity_service.extra.StrSend";


    public MyIntentService_Count() {
        super("MyIntentService_Count");
/*        count_Thread = new My_Thread();
        count_Thread.start();*/
        Log.d("----//--","MyIntentService_Count");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "MyService onBind");
        return new Binder();
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     *
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService_Count.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     *
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService_Count.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

     */

    @Override
    protected void onHandleIntent(Intent intent) {
        Service_Receiver_E = new Service_Receiver();
        notificationManager = NotificationManagerCompat.from(this);
        IntentFilter intentFilter = new IntentFilter(
                MyIntentService_Count.ACTION_Start);
        intentFilter.addAction(ACTION_Stop);
        intentFilter.addAction(ServiceTstStateRequest.StateRunRequest);
        intentFilter.addAction(ServiceTstStateRequest.ActivityDestroy);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(Service_Receiver_E, intentFilter);
        try {
            Counter();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
//!!!
    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.

    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.

    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
    */

    private void Counter() throws InterruptedException {
        String extraOut;
        for ( i = 1; i <= 100;  i=i+CountEnable) {
            extraOut = String.valueOf(i);
            Log.d("--/Service_Count/--",extraOut );

            // ???????????????????? ??????????????????
            Intent responseIntent = new Intent();
            responseIntent.setAction(ACTION_MYINTENTSERVICE);
            responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
            responseIntent.putExtra(EXTRA_KEY_OUT, extraOut);
            sendBroadcast(responseIntent);
            try {
                sleep(ServiceTstStateRequest.pause_send);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }//private void Counter()
}