package co.stringstech.notice.schedules;

import java.util.Locale;

import co.stringstech.notice.App;
import co.stringstech.notice.Broadcaster;


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

        Broadcaster alice = new Broadcaster(app);

        String text = String.format(Locale.getDefault(),
                "大家工作辛苦了，%s来为大家放歌，嗨起来，喵～",
                alice.getName()
        );

        alice.speak(text, () -> app.musicPlayer.playRandom());
    }
}
