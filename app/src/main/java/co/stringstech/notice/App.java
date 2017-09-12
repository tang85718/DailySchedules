package co.stringstech.notice;

import android.app.Application;

import java.util.HashMap;

import co.stringstech.notice.schedules.BaseSchedule;

/**
 * Created by tangxuyao on 2017/9/12.
 * App
 */

public class App extends Application {

    public MusicPlayer musicPlayer = new MusicPlayer();
    public HashMap<String, BaseSchedule> schedules = new HashMap<>();
    public SmartBot smartBot;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}