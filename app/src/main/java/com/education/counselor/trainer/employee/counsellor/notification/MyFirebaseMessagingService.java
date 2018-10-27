package com.education.counselor.trainer.employee.counsellor.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.student.StudentDashboardActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static com.facebook.GraphRequest.TAG;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> payload = remoteMessage.getData();
            showNotification(payload, remoteMessage.getFrom());
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void showNotification(Map<String, String> payload, String from) {
        Intent intent = new Intent(getApplicationContext(), StudentDashboardActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "ABCD";// The user-visible name of the channel.
        int importance = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH;
        }
        NotificationChannel mChannel;
        Notification n;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
            n = new Notification.Builder(getBaseContext())
                    .setContentTitle(from)
                    .setContentText("Subject")
                    .setSmallIcon(R.mipmap.ic_logo)
                    .setContentIntent(pIntent)
                    .setChannelId(CHANNEL_ID)
                    .setAutoCancel(true)
                    .build();
        } else {
            n = new Notification.Builder(getBaseContext())
                    .setContentTitle(from)
                    .setContentText("Subject")
                    .setSmallIcon(R.mipmap.ic_logo)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();
        }
        notificationManager.notify(0, n);
    }
}