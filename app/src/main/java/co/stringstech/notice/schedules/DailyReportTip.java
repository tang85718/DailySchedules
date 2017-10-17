package co.stringstech.notice.schedules;

import co.stringstech.notice.MusicPlayer;
import co.stringstech.notice.SmartBot;

/**
 * Created by tangxuyao on 2017/9/12.
 * DailyReportTip
 */

public class DailyReportTip extends BaseSchedule {

    private SmartBot smartBot;
    private MusicPlayer musicPlayer;

    public DailyReportTip(SmartBot smartBot, MusicPlayer musicPlayer) {
        this.smartBot = smartBot;
        this.musicPlayer = musicPlayer;
    }

    @Override
    public void executeNow() {
        musicPlayer.duck();
        smartBot.speak("久坐对颈椎不利，请大家停下忙碌，休息10分钟，离开座位，走动一会，换换思路，之后，请花5分钟时间认真编写今天的日志", () -> musicPlayer.unduck());
    }
}
