package com.example.reps;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent resultIntent = new Intent(context, LogedActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "programarEntrenamiento")
                .setSmallIcon(R.drawable.reps_logo_icon_no_background)
                .setContentTitle(context.getString(R.string.titulo_notificacion))
                .setContentText(context.getString(R.string.mensaje_notificacion))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());
    }
}
