package co.stringstech.notice.schedules;

import java.util.TimerTask;
import timber.log.Timber;

/**
 * Created by tangxuyao on 2017/9/12.
 * BaseSchedule
 * fix some bugs
 */

public abstract class BaseSchedule extends TimerTask {


    private long scheduleTime;

    public void execute() {
        Timber.i("starting: %s", getName());

        long diff = Math.abs(System.currentTimeMillis() - getScheduleTime());
        if (diff >= 10000) {
            Timber.w("超过计划时间%d秒，不执行计划", diff / 1000);
            return;
        }

        executeNow();
    }

    public abstract String getName();

    public abstract void executeNow();

    public void setScheduleTime(long time) {
        this.scheduleTime = time;
    }

    public long getScheduleTime() {
        return scheduleTime;
    }

    @Override
    public void run() {
        execute();
    }
}
