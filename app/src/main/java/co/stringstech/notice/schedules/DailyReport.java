package co.stringstech.notice.schedules;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import co.stringstech.notice.App;

/**
 * Created by tangxuyao on 2017/9/12.
 * DailyReport for
 */

public class DailyReport extends BaseSchedule {

    private App app;

    public DailyReport(App app) {
        this.app = app;
    }


    @Override
    public String getName() {
        return "DailyReport";
    }

    @Override
    public void executeNow() {

        ArrayList<String> admins = new ArrayList<>();
        admins.add("邓京辉");
        admins.add("彭智耿");
        admins.add("杨裕安");

        ArrayList<String> developers = new ArrayList<>();
        developers.add("姚春林");
        developers.add("彭智耿");
        developers.add("杨裕安");
        developers.add("刘阳");
        developers.add("黄磊");
        developers.add("柳成望");
        developers.add("邓京辉");
        developers.add("李佳洋");
        developers.add("陈峻炫");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        String developer = developers.get(dayOfYear % developers.size());
        String text = String.format(Locale.getDefault(),
                "开始站会, 请%s第一个发言", developer);

        app.musicPlayer.duck();
        app.broadcaster.speak(text, () -> app.musicPlayer.unduck());
    }
}
