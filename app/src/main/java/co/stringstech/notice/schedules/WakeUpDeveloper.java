package co.stringstech.notice.schedules;

import android.content.Context;

import java.util.Locale;

import co.stringstech.notice.SmartBot;
import co.stringstech.notice.MusicPlayer;

/**
 * Created by tangxuyao on 2017/9/12.
 * WakeUpDeveloper
 */

public class WakeUpDeveloper implements BaseSchedule {
    SmartBot smartBot;
    MusicPlayer musicPlayer;

    public WakeUpDeveloper(SmartBot smartBot, MusicPlayer musicPlayer) {
        this.smartBot = smartBot;
        this.musicPlayer = musicPlayer;
    }

    @Override
    public void execute() {
        musicPlayer.duck();

//        ArrayList<String> admin = new ArrayList<>();
//        admin.add("彭智耿");
//        admin.add("袁超");
//        admin.add("杨裕安");

        String text = String.format(Locale.getDefault(),
                "大家好，我是%s，工作辛苦了，请大家准备2点半的站会, 记得站会时不要害羞.喵～.",
                smartBot.getName()
        );
        smartBot.speak(text, () -> musicPlayer.resume());
    }
}
