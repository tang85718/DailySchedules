package co.stringstech.notice.schedules;

import co.stringstech.notice.MusicPlayer;
import co.stringstech.notice.SmartBot;

/**
 * Created by tangxuyao on 2017/9/12.
 * DailyReport
 */

public class DailyReport implements BaseSchedule {

    private SmartBot smartBot;
    private MusicPlayer musicPlayer;

    public DailyReport(SmartBot smartBot, MusicPlayer musicPlayer) {
        this.smartBot = smartBot;
        this.musicPlayer = musicPlayer;
    }

    @Override
    public void execute() {
        musicPlayer.duck();
        smartBot.speak("站会，站会，站会，重要的事情说三遍～喵", () -> musicPlayer.resume());
    }
}
