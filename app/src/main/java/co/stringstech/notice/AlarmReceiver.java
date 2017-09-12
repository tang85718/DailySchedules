package co.stringstech.notice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import timber.log.Timber;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.
        App app = (App) context.getApplicationContext();
        String name = intent.getAction();
        Timber.d("name:%s", name);
        EventBus.getDefault().post(new MessageEvent(name));
    }
}
