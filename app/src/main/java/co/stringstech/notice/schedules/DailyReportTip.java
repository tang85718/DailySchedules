package co.stringstech.notice.schedules;

import co.stringstech.notice.MusicPlayer;
import co.stringstech.notice.SmartBot;

/**
 * Created by tangxuyao on 2017/9/12.
 * DailyReportTip
 */

public class DailyReportTip implements BaseSchedule {

    private SmartBot smartBot;
    private MusicPlayer musicPlayer;

    public DailyReportTip(SmartBot smartBot, MusicPlayer musicPlayer) {
        this.smartBot = smartBot;
        this.musicPlayer = musicPlayer;
    }

    @Override
    public void execute() {
        musicPlayer.duck();
        smartBot.speak("开发的大大，请记得写日报，日报，日报，喵～", () -> musicPlayer.unduck());
    }
}
