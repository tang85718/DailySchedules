package co.stringstech.notice.schedules;

import co.stringstech.notice.MusicPlayer;

/**
 * Created by tangxuyao on 2017/9/12.
 * WakeUpMusic
 */

public class WakeUpMusic implements BaseSchedule {
    private MusicPlayer musicPlayer;

    public WakeUpMusic(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }

    @Override
    public void execute() {
        musicPlayer.playOne();
    }
}
