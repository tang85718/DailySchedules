package co.stringstech.notice.schedules;

import java.util.Locale;

import co.stringstech.notice.MusicPlayer;
import co.stringstech.notice.SmartBot;

/**
 * Created by tangxuyao on 2017/9/12.
 * StartRelaxMusic
 */

public class StartRelaxMusic implements BaseSchedule {
    private MusicPlayer musicPlayer;
    private SmartBot smartBot;

    public StartRelaxMusic(SmartBot sb, MusicPlayer rt) {
        this.musicPlayer = rt;
        this.smartBot = sb;
    }

    @Override
    public void execute() {
        musicPlayer.playRandom();
//        String text = String.format(Locale.getDefault(),
//                "大家好，我是%s，大家工作辛苦了，现在是休息时间，播放几个小曲放松心情。喵～",
//                smartBot.getName()
//        );
//
//        smartBot.speak(text, () -> musicPlayer.playRandom());
    }
}
