package co.stringstech.notice;

import android.app.AlarmManager;
import android.app.Application;

import com.facebook.stetho.Stetho;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import co.stringstech.notice.schedules.BaseSchedule;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by tangxuyao on 2017/9/12.
 * App
 */

public class App extends Application {

    public MusicPlayer musicPlayer = new MusicPlayer(this);
    public Broadcaster broadcaster;
    public HashMap<String, BaseSchedule> schedules = new HashMap<>();
    public PlaylistBuilder builder = new PlaylistBuilder(this);
    private Timer timer = new Timer();

    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=59e5a85a");

        Realm.init(this);

        ThreadUtil.bind();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                                .databaseNamePattern(Pattern.compile(".+\\.realm"))
                                .build())
                        .build());
    }

    public Realm getRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration((realm, oldVersion, newVersion) -> {

                })
                .build();
        return Realm.getInstance(config);
    }

    public void reset() {

    }

    public void schedule(int h, int m, int s, BaseSchedule schedule, boolean loop) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
        calendar.set(Calendar.SECOND, s);

        long scheduleTime = calendar.getTimeInMillis();
        schedule.setScheduleTime(scheduleTime);

        String name = schedule.getClass().getSimpleName();
        schedules.put(name, schedule);

        Timber.i("create schedule: %d, %d, %s", h, m, name);

        if (loop) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Timber.i("starting: %s", schedule.getClass().getSimpleName());
                    schedule.execute();
                }
            }, calendar.getTime(), AlarmManager.INTERVAL_DAY);
        } else {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Timber.i("starting: %s", schedule.getClass().getSimpleName());
                    schedule.execute();
                }
            }, calendar.getTime());
        }
    }
}
