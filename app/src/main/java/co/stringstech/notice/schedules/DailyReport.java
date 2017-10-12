package co.stringstech.notice.schedules;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import co.stringstech.notice.MusicPlayer;
import co.stringstech.notice.SmartBot;

/**
 * Created by tangxuyao on 2017/9/12.
 * DailyReport for
 */

public class DailyReport extends BaseSchedule {

    private SmartBot smartBot;
    private MusicPlayer musicPlayer;

    public DailyReport(SmartBot smartBot, MusicPlayer musicPlayer) {
        this.smartBot = smartBot;
        this.musicPlayer = musicPlayer;
    }

    @Override
    public void executeNow() {
        ArrayList<String> developers = new ArrayList<>();
        developers.add("袁超");
        developers.add("彭智耿");
        developers.add("杨裕安");
        developers.add("刘阳");
        developers.add("黄磊");
        developers.add("柳成望");
        developers.add("邓京辉");
        developers.add("李佳洋");
        developers.add("陈峻炫");

        Random random = new Random();
        int index = random.nextInt(developers.size());
        String developer = developers.get(index);

        String text = String.format(Locale.getDefault(),
                "站会，站会，站会，重要的事情说三遍, 请%s第一个发言，喵～", developer);

        musicPlayer.duck();
        smartBot.speak(text, () -> musicPlayer.unduck());
    }
}
