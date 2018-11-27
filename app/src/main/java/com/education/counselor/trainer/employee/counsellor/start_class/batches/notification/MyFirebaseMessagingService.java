package com.education.counselor.trainer.employee.counsellor.start_class.batches.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.launcher.SplashScreenActivity;
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
//        Intent intent = new Intent(getApplicationContext(), StudentDashboardActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
//        Intent snoozeIntent = new Intent(this, StudentDashboardActivity.class);
//        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        int notifyID = 1;
//        String CHANNEL_ID = "my_channel_01";// The id of the channel.
//        CharSequence name = "ABCD";// The user-visible name of the channel.
//        String description = "Description";
//        int importance = 0;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            importance = NotificationManager.IMPORTANCE_DEFAULT;
//        }
//        NotificationChannel mChannel;
//        Notification n;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
//            mChannel.setDescription(description);
//            snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
//            notificationManager.createNotificationChannel(mChannel);
//            n = new Notification.Builder(getBaseContext())
//                    .setContentTitle(payload.get("title"))
//                    .setContentText(payload.get("body"))
//                    .setSmallIcon(R.mipmap.ic_logo)
//                    .setContentIntent(pIntent)
//                    .setChannelId(CHANNEL_ID)
//                    .setAutoCancel(true)
//                    .setStyle(new Notification.BigTextStyle().bigText(payload.get("body")))
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    .addAction(R.mipmap.ic_logo, payload.get("body"), snoozePendingIntent)
//                    .build();
//            notificationManager.createNotificationChannel(mChannel);
//        } else {
//            n = new Notification.Builder(getBaseContext())
//                    .setContentTitle(payload.get("title"))
//                    .setContentText(payload.get("body"))
//                    .setSmallIcon(R.mipmap.ic_logo)
//                    .setContentIntent(pIntent)
//                    .setAutoCancel(true)
//                    .build();
//        }
//        notificationManager.notify(notifyID, n);
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        String CHANNEL_ID = "1";
        int notificationId = 1;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_logo)
                .setContentTitle(payload.get("title"))
                .setContentText(payload.get("body"))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(payload.get("body")))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "EduCounsellor";
            String description = "RANKETHON";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, mBuilder.build());
    }
}