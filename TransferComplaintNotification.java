package com.wizard.androidclubproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

//I have just mentioned this class for having the unique channel IDs as 
//they should be accessed directly like: MyWorkerClass.CHANNEL_ID by another classes...>>

public class MyWorkerClass extends Worker {

    //Unique channel Id or IDs :->)
    public static final String CHANNEL_ID="Transfer";
    
    //It is used to identify the notification
    public static final int NID=1011;

    public MyWorkerClass(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    @NonNull
    @Override
    public Result doWork() {
        TransferComplaintNotification notifyUser=new TransferComplaintNotification();
        return Result.success();
    }
    
    //This is the main class that will work for notifications...>>
    class TransferComplaintNotification{
        NotificationManager nm;
        NotificationManagerCompat nmCompat;

        TransferComplaintNotification(){

            //nmCompat is used because of backward compatibility issues and we will create Notifications from it always.
            //we can also create channels from it but can't get notification services using it so we will create channels by
            //simple NotificationManager...>>
            nmCompat=NotificationManagerCompat.from(getApplicationContext());

            //This is used to get the notification service and creating channels...>>
            nm= (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

            //getting Notification channel..>>
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"Request Transfer"
                        ,NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("Used for Transfer complaints at upper hierarchy");
                channel.enableLights(true);
                channel.enableVibration(true);
                assert nm != null;
                nm.createNotificationChannel(channel);
            }
            nmCompat.notify(NID,getNotification("by do work()","i am on completing my work"));
        }

        //this is the function that will create the notification with two action buttons :->>
        private Notification getNotification(String title,String desc) {
        
        //The code written below will work also for the devices that are below api level 26 or oreo as 
        //in that situation channel id will just ommitted...NotificationCompat.Builder ensures it...>>
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    
                    //1. Ok action:- this will redirect the user to the app for further process...>>
                    .addAction(R.mipmap.ic_launcher_round, "Ok", PendingIntent.getBroadcast(getApplicationContext(),0
                            ,new Intent(getApplicationContext(),NotificationReceiver.class)
                            .putExtra("toPostActivity","PostIt")
                            .putExtra("nid",NID)
                            ,PendingIntent.FLAG_UPDATE_CURRENT))

                    //2. Cancel button:- this will just remove the notification from notification drawer...>>
                    .addAction(R.mipmap.ic_launcher_round, "Cancel",PendingIntent.getBroadcast(getApplicationContext(),1
                            ,new Intent(getApplicationContext(),NotificationReceiver.class)
                             .putExtra("nid",NID)
                            ,PendingIntent.FLAG_CANCEL_CURRENT))

                    //3. Click on the notification will open up the app...>>
                    .setContentIntent(PendingIntent.getActivity(getApplicationContext(),2
                            ,new Intent(getApplicationContext(),MainActivity.class)
                            ,PendingIntent.FLAG_UPDATE_CURRENT))
                    .setAutoCancel(true)

                    .setContentTitle(title)
                    .setContentText(desc)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setSmallIcon(R.drawable.ic_views)
                    .setColor(Color.YELLOW)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setChannelId(CHANNEL_ID)
                    .setColorized(true)
                    .build();
            return notification;
        }
    }
    
//I have made a BroadcastReceiver class that will do following work...>>
//1. Get into the app when user clicked "Ok" when app is closed
//2. Removing the notification after any action performed.
package com.wizard.androidclubproject;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    NotificationManager manager;
    @Override
    public void onReceive(Context context, Intent intent) {
        manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (intent.hasExtra("nid")){
            if (intent.hasExtra("toPostActivity")){
                removeNotification(context,intent);
                intent=new Intent(context.getApplicationContext(),PostsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("PostIt","PostIt");
                context.startActivity(intent);
                Toast.makeText(context,"You are redirected in the app",Toast.LENGTH_SHORT).show();
            }
            else {
                removeNotification(context,intent);
            }
        }
    }
    private void removeNotification(Context context,Intent intent){
        if (intent.hasExtra("nid")){
            int nid=intent.getIntExtra("nid",-1);
            if (nid!=-1){
                Toast.makeText(context,"You canceled out notification",Toast.LENGTH_SHORT).show();
                assert manager != null;
                manager.cancel(nid);
                //for closing the notification drawer...>>
                context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
            }
        }
    }
}
