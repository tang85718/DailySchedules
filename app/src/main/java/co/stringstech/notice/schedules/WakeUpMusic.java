package co.stringstech.notice.schedules;

import co.stringstech.notice.App;

/**
 * Created by tangxuyao on 2017/9/12.
 * WakeUpMusic
 */

public class WakeUpMusic extends BaseSchedule {
    private App app;

    public WakeUpMusic(App app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "WakeupMusic";
    }

    @Override
    public void executeNow() {
        app.musicPlayer.playOne();
    }
}
