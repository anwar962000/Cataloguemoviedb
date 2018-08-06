package com.istiabudi.cataloguemoviedb;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TimePicker;

import com.istiabudi.cataloguemoviedb.reminder.AlarmPreference2;
import com.istiabudi.cataloguemoviedb.reminder.AlarmReceiver;
import com.istiabudi.cataloguemoviedb.reminder.AlarmReleaseReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingActivity3 extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.dailyRemind)
    CheckBox dailyRemind;
    @BindView(R.id.releaseRemind)
    CheckBox releaseRemind;

    public static final String KEY_HEADER_UPCOMING_REMINDER = "upcomingReminder";
    public static final String KEY_HEADER_DAILY_REMINDER = "dailyReminder";
    public static final String KEY_FIELD_UPCOMING_REMINDER = "checkedUpcoming";
    public static final String KEY_FIELD_DAILY_REMINDER = "checkedDaily";

    public AlarmReceiver alarmDailyReceiver;
    public AlarmReleaseReceiver alarmReleaseReceiver;
    public AlarmPreference2 alarmPreference;
    public SharedPreferences sReleaseReminder, sDailyReminder;
    public SharedPreferences.Editor editorReleaseReminder, editorDailyReminder;

    final Calendar repeatTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_setting3 );

        alarmReleaseReceiver = new AlarmReleaseReceiver();
        alarmDailyReceiver = new AlarmReceiver();

        ButterKnife.bind( this );
        alarmPreference = new AlarmPreference2( this );

        dailyRemind.setChecked( alarmPreference.getHasChcked( R.id.dailyRemind ) );
        releaseRemind.setChecked( alarmPreference.getHasChcked( R.id.releaseRemind ) );

        dailyRemind.setOnCheckedChangeListener( this );
        releaseRemind.setOnCheckedChangeListener( this );


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected( item );

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged( newConfig );
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        alarmPreference.setHasChecked( isChecked, buttonView.getId() );
        if (buttonView.getId() == R.id.dailyRemind) {

            if (isChecked) {
                setDailyRemind( buttonView.getId() );
            } else {
                alarmDailyReceiver.cancelReminder( SettingActivity3.this );
            }

        } else {
            if (isChecked) {
                setReleaseRemind( buttonView.getId() );
            } else {
                alarmReleaseReceiver.cancelReminder( SettingActivity3.this );
            }
        }
    }

    public void setDailyRemind(final int checkBoxId) {
        final Calendar currentDate = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog( this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                repeatTime.set( Calendar.HOUR_OF_DAY, hourOfDay );
                repeatTime.set( Calendar.MINUTE, minute );
                SimpleDateFormat timeFormat = new SimpleDateFormat( "HH:mm", Locale.getDefault() );
                String repeatTimeTime = timeFormat.format( repeatTime.getTime() );
                String message = "Daily Movie , please Wait come Back Soon";

                alarmDailyReceiver.setReminder( SettingActivity3.this, AlarmReceiver.TYPE_REMINDER, repeatTimeTime, message );

            }

        }, currentDate.get( Calendar.HOUR_OF_DAY ), currentDate.get( Calendar.MINUTE ), true );
//
//        timePickerDialog.setButton( DialogInterface.BUTTON_NEGATIVE, getString( R.string.cancel ), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                if (which == DialogInterface.BUTTON_NEGATIVE) {
//                    if (checkBoxId == R.id.dailyRemind) {
//                        dailyRemind.setChecked( false );
//                    } else {
//                        releaseRemind.setChecked( false );
//                    }
//                }
//            }
//        } );
        timePickerDialog.setCanceledOnTouchOutside( false );
        timePickerDialog.show();
    }

    public void setReleaseRemind(final int checkBoxId) {
        final Calendar currentDate = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog( this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                repeatTime.set( Calendar.HOUR_OF_DAY, hourOfDay );
                repeatTime.set( Calendar.MINUTE, minute );
                SimpleDateFormat timeFormat = new SimpleDateFormat( "HH:mm", Locale.getDefault() );
                String repeatTimeTime = timeFormat.format( repeatTime.getTime() );
                String message = "Release Movie , please Wait come Back Soon";


                alarmReleaseReceiver.setReminder( SettingActivity3.this, AlarmReleaseReceiver.TYPE_REMINDER, repeatTimeTime, message );


            }
        }, currentDate.get( Calendar.HOUR_OF_DAY ), currentDate.get( Calendar.MINUTE ), true );
        timePickerDialog.show();
    }
}
