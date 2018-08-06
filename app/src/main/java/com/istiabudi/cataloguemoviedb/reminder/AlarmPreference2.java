package com.istiabudi.cataloguemoviedb.reminder;

import android.content.Context;
import android.content.SharedPreferences;

import com.istiabudi.cataloguemoviedb.R;

public class AlarmPreference2 {

    private String KEY_REMINDER = "reminder";
    private String KEY_RELEASE = "release";
    private SharedPreferences preferences;

    private final static String PREF_NAME = "reminderPreferences";
    private final static String KEY_REMINDER_Daily = "DailyTime";
    private final static String KEY_REMINDER_Release = "ReleaseTime";
    private final static String KEY_REMINDER_MESSAGE_Release = "reminderMessageRelease";
    private final static String KEY_REMINDER_MESSAGE_Daily = "reminderMessageDaily";

    //public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public AlarmPreference2(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setReminderReleaseTime(String time){
        editor.putString(KEY_REMINDER_Daily,time);
        editor.commit();
    }

    public String getReminderReleaseTime(){
        return preferences.getString(KEY_REMINDER_Release,null);
    }
    public void setReminderReleaseMessage (String message){
        editor.putString(KEY_REMINDER_MESSAGE_Release,message);
    }

    public String getReminderReleaseMessage(){
        return  preferences.getString(KEY_REMINDER_MESSAGE_Release,null);
    }

    public void setReminderDailyTime(String time){
        editor.putString(KEY_REMINDER_Daily,time);
        editor.commit();
    }

    public String getReminderDailyTime(){
        return preferences.getString(KEY_REMINDER_Daily,null);
    }

    public void setReminderDailyMessage(String message){
        editor.putString(KEY_REMINDER_MESSAGE_Daily,message);
    }

    public String getReminderDailyMessage(){
        return  preferences.getString(KEY_REMINDER_MESSAGE_Daily,null);
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }
    public void setHasChecked(boolean hasChecked, int checboxId) {
        SharedPreferences.Editor editor = preferences.edit();

        if (checboxId == R.id.dailyRemind) {
            editor.putBoolean(KEY_REMINDER, hasChecked);
        } else {
            editor.putBoolean(KEY_RELEASE, hasChecked);
        }
        editor.apply();
    }

    public boolean getHasChcked(int checboxId) {

        if (checboxId == R.id.dailyRemind) {
            return preferences.getBoolean(KEY_REMINDER, false);
        } else {
            return preferences.getBoolean(KEY_RELEASE, false);
        }

    }
}
