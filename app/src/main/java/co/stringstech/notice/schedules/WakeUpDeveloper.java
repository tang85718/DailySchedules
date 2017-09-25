package co.stringstech.notice.schedules;

import java.util.Locale;

import co.stringstech.notice.SmartBot;
import co.stringstech.notice.MusicPlayer;

/**
 * Created by tangxuyao on 2017/9/12.
 * WakeUpDeveloper
 */

public class WakeUpDeveloper implements BaseSchedule {
    private SmartBot smartBot;
    private MusicPlayer musicPlayer;

    public WakeUpDeveloper(SmartBot smartBot, MusicPlayer musicPlayer) {
        this.smartBot = smartBot;
        this.musicPlayer = musicPlayer;
    }

    @Override
    public void execute() {
        musicPlayer.duck();
//        String text = String.format(Locale.getDefault(),
//                "大家好，请开发组准备2点半的站会, 记得站会时不要害羞.",
//                smartBot.getName()
//        );
        smartBot.speak("大家好，请开发组准备2点半的站会, 记得站会时不要害羞.", () -> musicPlayer.unduck());
    }
}
