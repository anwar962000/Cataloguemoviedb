package com.istiabudi.cataloguemoviedb.reminder;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.istiabudi.cataloguemoviedb.MainActivity;
import com.istiabudi.cataloguemoviedb.R;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String EXTRA_MESSAGES = "message";
    public static final String EXTRA_TYPE = "type";
    public static final String TYPE_REMINDER = "reminderAlarm";

    private final int NOTIF_ID_REMINDER = 1;

    public AlarmReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra( EXTRA_TYPE );
        String message = intent.getStringExtra( EXTRA_MESSAGES );
        String title = "Reminder Movie Daily";
        int notifId = NOTIF_ID_REMINDER;  //type.equal

        showReminderNotification( context, title, message, notifId );
    }

    private void showReminderNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
        Uri alarmSound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
        Intent intent = new Intent( context, MainActivity.class );
        PendingIntent pendingIntent = TaskStackBuilder.create( context )
//                .addParentStack(DetailActivity.class)
                .addNextIntent( intent ).getPendingIntent( NOTIF_ID_REMINDER, PendingIntent.FLAG_UPDATE_CURRENT );
        NotificationCompat.Builder builder = new NotificationCompat.Builder( context, message ).setSmallIcon( R.mipmap.ic_launcher ).setContentTitle( title ).setContentText( message ).setContentIntent( pendingIntent ).setColor( ContextCompat.getColor( context, android.R.color.transparent ) ).setVibrate( new long[]{1000, 1000, 1000, 1000, 1000} ).setSound( alarmSound );

        notificationManagerCompat.notify( notifId, builder.build() );

    }

    public void setReminder(Context context, String typeReminder, String time, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService( Context.ALARM_SERVICE );
        Intent intent = new Intent( context, AlarmReceiver.class );
        intent.putExtra( EXTRA_MESSAGES, message );
        intent.putExtra( EXTRA_TYPE, typeReminder );
        String timeArray[] = time.split( ":" );
        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.HOUR_OF_DAY, Integer.parseInt( timeArray[0] ) );
        calendar.set( Calendar.MINUTE, Integer.parseInt( timeArray[1] ) );
        calendar.set( Calendar.SECOND, 0 );

        int requestCode = NOTIF_ID_REMINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast( context, requestCode, intent, 0 );
        alarmManager.setInexactRepeating( AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent );

        Toast.makeText( context, R.string.reminderSaveDaily, Toast.LENGTH_SHORT ).show();
    }

    public void cancelReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService( Context.ALARM_SERVICE );
        Intent intent = new Intent( context, AlarmReceiver.class );
        int requestCode = NOTIF_ID_REMINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast( context, requestCode, intent, 0 );
        alarmManager.cancel( pendingIntent );
        Toast.makeText( context, R.string.reminderCancelDaily, Toast.LENGTH_SHORT ).show();
    }

}

