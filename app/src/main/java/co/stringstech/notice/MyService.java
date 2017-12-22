package co.stringstech.notice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import co.stringstech.notice.schedules.SchedulesBuilder;
import timber.log.Timber;

public class MyService extends Service implements Runnable {

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.i("MyService onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i("MyService Created");
        App app = (App) getApplication();
        app.builder.build();

        SchedulesBuilder builder = new SchedulesBuilder(app);
        builder.executeNow();
    }

    @Override
    public void run() {
    }
}
