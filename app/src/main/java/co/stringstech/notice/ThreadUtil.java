package co.stringstech.notice;

import android.os.Handler;
import android.os.Looper;

import timber.log.Timber;

public class ThreadUtil {

    private static Handler sHandler;

    /**
     * 必须在主线程中调用。
     */
    public static void bind() {
        if (sHandler == null) {
            sHandler = new Handler();
            Timber.i("MainThread.bind()");
        }
    }

    public static void run(Runnable action) {
        if (sHandler == null) {
            Timber.e("must be invoke bind() in main thread.");
            return;
        }
        sHandler.post(action);
    }

    public static void runDelay(int delayMillis, Runnable action) {
        if (sHandler == null) {
            Timber.e("must be invoke bind() in main thread.");
            return;
        }
        sHandler.postDelayed(action, delayMillis);
    }

    public static boolean sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Timber.w("sleep interrupt");
            return false;
        }
        return true;
    }

    public static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static String generateTrace() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String ret = "\n";
        for (int i = 4; i < trace.length; i++) {
            ret += "      ";
            ret += trace[i].toString();
            ret += "\n";
        }

        return ret;
    }
}
