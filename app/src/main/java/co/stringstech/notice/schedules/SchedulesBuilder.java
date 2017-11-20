package co.stringstech.notice.schedules;

import co.stringstech.notice.App;

/**
 * Created by tangxuyao on 2017/10/12.
 * SchedulesBuilder
 */

public class SchedulesBuilder extends BaseSchedule {

    private App app;

    public SchedulesBuilder(App app) {
        this.app = app;
    }

    @Override
    public void executeNow() {
        app.schedule(12, 31, 0, new StartRelaxMusic(app), true);
        app.schedule(13, 0, 0, new StopRelaxMusic(app.musicPlayer), true);

        app.schedule(14, 5, 0, new WakeUpMusic(app), true);
        app.schedule(14, 5, 20, new WakeUpDeveloper(app), true);
        app.schedule(14, 30, 0, new DailyReport(app), true);
//        app.schedule(17, 0, 0, new DailyReportTip(app), true);

        app.schedule(18, 10, 0, new StartRelaxMusic(app), true);
        app.schedule(19, 0, 0, new StopRelaxMusic(app.musicPlayer), true);
    }
}
