package co.stringstech.notice.schedules;

import java.util.Calendar;
import java.util.TimerTask;

import timber.log.Timber;

/**
 * Created by tangxuyao on 2017/9/12.
 * BaseSchedule
 * for test TFS
 */

public abstract class BaseSchedule extends TimerTask {


    private long scheduleTime;

    public void execute() {
        Timber.i("starting: %s", getName());

        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(System.currentTimeMillis());

        Calendar latest = Calendar.getInstance();
        latest.setTimeInMillis(scheduleTime);

        int th = today.get(Calendar.HOUR_OF_DAY);
        int tm = today.get(Calendar.MINUTE);
        int ts = today.get(Calendar.SECOND);

        int lh = latest.get(Calendar.HOUR_OF_DAY);
        int lm = latest.get(Calendar.MINUTE);
        int ls = latest.get(Calendar.SECOND);

        if (th != lh) {
            Timber.w("超过计划时间，不执行计划");
            return;
        }

        if (tm != lm) {
            Timber.w("超过计划时间，不执行计划");
            return;
        }

        long diff = Math.abs(ts - ls);
        if (diff >= 10) {
            Timber.w("超过计划时间%d秒，不执行计划", diff);
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
