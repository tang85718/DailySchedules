package co.stringstech.notice;


import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.OnLongClick;
import co.stringstech.notice.schedules.DailyReport;
import co.stringstech.notice.schedules.DailyReportTip;
import co.stringstech.notice.schedules.SchedulesBuilder;
import co.stringstech.notice.schedules.StartRelaxMusic;
import co.stringstech.notice.schedules.StopRelaxMusic;
import co.stringstech.notice.schedules.WakeUpDeveloper;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new Timber.DebugTree());
        Timber.d("onCreate");
        ButterKnife.bind(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initVolume();

        App app = (App) getApplication();
        app.smartBot = new SmartBot(this);

        SchedulesBuilder builder = new SchedulesBuilder(app);
        builder.executeNow();

        app.schedule(23, 0, 0, builder, true);

        app.builder.build();
//        app.schedule(12, 31, 0, new StartRelaxMusic(app.smartBot, app.musicPlayer, app.builder));
//        app.schedule(12, 55, 0, new StopRelaxMusic(app.musicPlayer));
//
//        app.schedule(14, 5, 0, new WakeUpMusic(app));
//        app.schedule(14, 5, 10, new WakeUpDeveloper(app.smartBot, app.musicPlayer));
//        app.schedule(14, 30, 0, new DailyReport(app.smartBot, app.musicPlayer));
//        app.schedule(14, 45, 0, new DailyReportTip(app.smartBot, app.musicPlayer));
//
//        app.schedule(18, 10, 0, new StartRelaxMusic(app.smartBot, app.musicPlayer, app.builder));
//        app.schedule(19, 0, 0, new StopRelaxMusic(app.musicPlayer));
    }

    private void initVolume() {
        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int max = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        manager.setStreamVolume(AudioManager.STREAM_MUSIC, max, AudioManager.FLAG_PLAY_SOUND);
    }

//    @OnClick(R.id.enter_webview)
//    public void onTapButton() {
//        Intent intent = new Intent(this, MediaPlayerActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }

    @OnLongClick({R.id.button, R.id.button2, R.id.daily_report_tip, R.id.start_music, R.id.stop_music})
    public boolean onTouchButton(View v) {
        App app = (App) getApplication();
        switch (v.getId()) {
            case R.id.button: {
                WakeUpDeveloper s = new WakeUpDeveloper(app.smartBot, app.musicPlayer);
                s.executeNow();
                break;
            }
            case R.id.button2: {
                DailyReport dailyReport = new DailyReport(app.smartBot, app.musicPlayer);
                dailyReport.executeNow();
                break;
            }
            case R.id.daily_report_tip: {
                DailyReportTip dailyReport = new DailyReportTip(app.smartBot, app.musicPlayer);
                dailyReport.executeNow();
                break;
            }
            case R.id.start_music: {
                StartRelaxMusic s = new StartRelaxMusic(app.smartBot, app.musicPlayer, app.builder);
                s.executeNow();
                break;
            }
            case R.id.stop_music: {
                StopRelaxMusic s = new StopRelaxMusic(app.musicPlayer);
                s.executeNow();
                break;
            }
        }
        return false;
    }
}
