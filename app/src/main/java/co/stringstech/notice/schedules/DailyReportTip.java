package co.stringstech.notice.schedules;

import co.stringstech.notice.App;

/**
 * Created by tangxuyao on 2017/9/12.
 * DailyReportTip
 */

public class DailyReportTip extends BaseSchedule {

    private App app;

    public DailyReportTip(App app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "DailyReportTip";
    }

    @Override
    public void executeNow() {
        app.musicPlayer.duck();
        app.broadcaster.speak("温馨提示：请花5分钟时间写日报，请花5分钟时间写日报，请花5分钟时间写日报，非常重要，喵喵",
                () -> app.musicPlayer.unduck());
    }
}
