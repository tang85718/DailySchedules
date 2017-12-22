package co.stringstech.notice.schedules;

import java.util.Locale;

import co.stringstech.notice.App;


/**
 * Created by tangxuyao on 2017/9/12.
 * StartRelaxMusic
 */

public class StartRelaxMusic extends BaseSchedule {
    private App app;

    public StartRelaxMusic(App app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "PlayMusic";
    }

    @Override
    public void executeNow() {
        app.builder.build();
        String text = String.format(Locale.getDefault(),
                "大家好，我是%s，大家工作辛苦了，现在是休息时间，播放几个小曲放松心情。喵～",
                app.broadcaster.getName()
        );

        app.broadcaster.speak(text, () -> app.musicPlayer.playRandom());
    }
}
