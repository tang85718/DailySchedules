package co.stringstech.notice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import co.stringstech.notice.schedules.BaseSchedule;
import timber.log.Timber;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.
        App app = (App) context.getApplicationContext();
        String name = intent.getAction();
        Timber.d("AlarmReceiver:%s", name);

        try {
            BaseSchedule schedule = app.schedules.get(name);
            schedule.execute();
        } catch (Exception e) {
            Timber.e(e);
        }
    }
}
