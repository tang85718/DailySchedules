package co.stringstech.notice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import co.stringstech.notice.schedules.BaseSchedule;
import co.stringstech.notice.schedules.DailyReport;
import co.stringstech.notice.schedules.DailyReportTip;
import co.stringstech.notice.schedules.StartRelaxMusic;
import co.stringstech.notice.schedules.StopRelaxMusic;
import co.stringstech.notice.schedules.WakeUpDeveloper;
import co.stringstech.notice.schedules.WakeUpMusic;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private AlarmManager alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new Timber.DebugTree());
        Timber.d("onCreate");
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        App app = (App) getApplication();
        app.smartBot = new SmartBot(this);

        alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        schedule(12, 30, 0, new StartRelaxMusic(app.musicPlayer));
        schedule(13, 0, 0, new StopRelaxMusic(app.musicPlayer));
        schedule(14, 10, 0, new WakeUpMusic(app.musicPlayer));
        schedule(14, 10, 10, new WakeUpDeveloper(app.smartBot, app.musicPlayer));
        schedule(14, 30, 0, new DailyReport(app.smartBot, app.musicPlayer));
        schedule(14, 45, 0, new DailyReportTip(app.smartBot, app.musicPlayer));
    }

    private void schedule(int h, int m, int s, BaseSchedule schedule) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
        calendar.set(Calendar.SECOND, s);

        App app = (App) getApplication();
        String name = schedule.getClass().getSimpleName();

        app.schedules.put(name, schedule);

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction(name);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                alarmIntent
        );
        Timber.i("name %d, %d, %s", h, m, name);
    }

    @OnClick({R.id.button, R.id.button2, R.id.daily_report_tip})
    public void onTouchButton(View v) {
        App app = (App) getApplication();
        Timber.d("onTouchButton:%d", v.getId());
        switch (v.getId()) {
            case R.id.button: {
                WakeUpDeveloper s = new WakeUpDeveloper(app.smartBot, app.musicPlayer);
                s.execute();
                break;
            }
            case R.id.button2: {
                DailyReport dailyReport = new DailyReport(app.smartBot, app.musicPlayer);
                dailyReport.execute();
                break;
            }
            case R.id.daily_report_tip: {
                DailyReportTip dailyReport = new DailyReportTip(app.smartBot, app.musicPlayer);
                dailyReport.execute();
                break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        App app = (App) getApplication();

        try {
            BaseSchedule schedule = app.schedules.get(event.name);
            schedule.execute();
        } catch (Exception e) {
            Timber.e(e);
        }
    }
}
