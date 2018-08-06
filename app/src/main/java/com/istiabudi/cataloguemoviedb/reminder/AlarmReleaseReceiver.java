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
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.istiabudi.cataloguemoviedb.BuildConfig;
import com.istiabudi.cataloguemoviedb.DetailActivity;
import com.istiabudi.cataloguemoviedb.MainActivity;
import com.istiabudi.cataloguemoviedb.MovieItem;
import com.istiabudi.cataloguemoviedb.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AlarmReleaseReceiver extends BroadcastReceiver {

    public static final String EXTRA_MESSAGE = "messageRelease";
    public static final String EXTRA_TYPE = "typeRelease";
    public static final String TYPE_REMINDER = "reminderAlarmRelease";

    private final int NOTIF_ID_RELEASE = 21;
    public List<MovieItem> listMovie = new ArrayList<>();

    public AlarmReleaseReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra( EXTRA_TYPE );
        String message = intent.getStringExtra( EXTRA_MESSAGE );
        String title = "Reminder Movie Release";
        int notifId = NOTIF_ID_RELEASE;  //type.equal
        getMovie( context );
        //showReminderNotification(context,title,message,notifId);
    }

    private void showReminderNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
        Uri alarmSound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
        Intent intent = new Intent( context, MainActivity.class );
        PendingIntent pendingIntent = TaskStackBuilder.create( context ).addNextIntent( intent ).getPendingIntent( NOTIF_ID_RELEASE, PendingIntent.FLAG_UPDATE_CURRENT );
        NotificationCompat.Builder builder = new NotificationCompat.Builder( context, message ).setSmallIcon( R.mipmap.ic_launcher ).setContentTitle( title ).setContentText( message ).setContentIntent( pendingIntent ).setColor( ContextCompat.getColor( context, android.R.color.transparent ) ).setVibrate( new long[]{1000, 1000, 1000, 1000, 1000} ).setSound( alarmSound );

        notificationManagerCompat.notify( notifId, builder.build() );

    }

    public void setReminder(Context context, String typeReminder, String time, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService( Context.ALARM_SERVICE );
        Intent intent = new Intent( context, AlarmReleaseReceiver.class );
        intent.putExtra( EXTRA_MESSAGE, message );
        intent.putExtra( EXTRA_TYPE, typeReminder );
        String timeArray[] = time.split( ":" );
        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.HOUR_OF_DAY, Integer.parseInt( timeArray[0] ) );
        calendar.set( Calendar.MINUTE, Integer.parseInt( timeArray[1] ) );
        calendar.set( Calendar.SECOND, 0 );

        int requestCode = NOTIF_ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast( context, requestCode, intent, 0 );
        alarmManager.setInexactRepeating( AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent );

        Toast.makeText( context, R.string.reminderSave, Toast.LENGTH_SHORT ).show();
    }

    public void cancelReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService( Context.ALARM_SERVICE );
        Intent intent = new Intent( context, AlarmReleaseReceiver.class );
        int requestCode = NOTIF_ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast( context, requestCode, intent, 0 );
        alarmManager.cancel( pendingIntent );
        Toast.makeText( context, R.string.reminderCancel, Toast.LENGTH_SHORT ).show();
    }

    private void getMovie(final Context context) {
        listMovie.clear();
        String url = "https://api.themoviedb.org/3/movie/upcoming?language=en-US&api_key=" + BuildConfig.API_KEY;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get( url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String( responseBody );
                Log.d( "MovieResult", result );
                try {
                    JSONObject responseObject = new JSONObject( result );
                    JSONArray results = responseObject.getJSONArray( "results" );
                    Gson gson = new Gson();
                    listMovie = gson.fromJson( String.valueOf( results ), new TypeToken<List<MovieItem>>() {
                    }.getType() );
                    Log.d( "MovieResult", String.valueOf( gson.toJson( listMovie ) ) );


                    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
                    String today = sdf.format( new Date() );

                    Log.d( "onNotif" , "Date " + today );

                    List<MovieItem> items = new ArrayList<>();

                    for (int i = 0; i < listMovie.size(); i++) {
                        String movieDate = listMovie.get( i ).getDate();
                        Log.d( "onNotif" , "Date " + movieDate + " title " + listMovie.get( i ).title);
                        if (today.equals( movieDate )){
                            items.add( listMovie.get( i ) );
                        }
                    }

                    Log.d( "onNotif" , "movieSize " + items.size());


                    int notifId = 200;

                    showNotification( context, notifId, items );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText( context, R.string.err_load_failed, Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    private void showNotification(Context context,int notifId, List<MovieItem> items) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
        Uri alarmSound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );



        for (int i = 0; i < items.size(); i++) {
            Log.d( "onNotif" , "Notif Show" );
            Intent intent = new Intent( context, DetailActivity.class );
            intent.putExtra( DetailActivity.MOVIE_ITEM, new Gson().toJson( items.get( i ) ) );
            PendingIntent pendingIntent = PendingIntent.getActivity( context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT );

            NotificationCompat.Builder builder = new NotificationCompat.Builder( context, items.get( i ).getTitle() )
                    .setSmallIcon( R.mipmap.ic_launcher_round )
                    .setContentText( items.get( i ).title )
                    .setContentTitle("Movie Release")
                    .setColor( ContextCompat.getColor( context, android.R.color.transparent ) )
                    .setContentIntent( pendingIntent )
                    .setAutoCancel( true )
                    .setVibrate( new long[]{1000, 1000, 1000, 1000, 1000} )
                    .setSound( alarmSound );

            notificationManagerCompat.notify( i, builder.build() );
        }


    }

}
