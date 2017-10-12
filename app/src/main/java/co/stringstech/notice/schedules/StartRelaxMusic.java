package co.stringstech.notice.schedules;

import android.os.Looper;

import java.util.Locale;

import co.stringstech.notice.MusicPlayer;
import co.stringstech.notice.PlaylistBuilder;
import co.stringstech.notice.SmartBot;

/**
 * Created by tangxuyao on 2017/9/12.
 * StartRelaxMusic
 */

public class StartRelaxMusic extends BaseSchedule {
    private MusicPlayer musicPlayer;
    private SmartBot smartBot;
    private PlaylistBuilder builder;

    public StartRelaxMusic(SmartBot sb, MusicPlayer rt, PlaylistBuilder builder) {
        this.musicPlayer = rt;
        this.smartBot = sb;
        this.builder = builder;
    }

    @Override
    public void executeNow() {
        builder.build();
        String text = String.format(Locale.getDefault(),
                "大家好，我是%s，大家工作辛苦了，现在是休息时间，播放几个小曲放松心情。喵～",
                smartBot.getName()
        );

        smartBot.speak(text, () -> musicPlayer.playRandom());
    }
}
