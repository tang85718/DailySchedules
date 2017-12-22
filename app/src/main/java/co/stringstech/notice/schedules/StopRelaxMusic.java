package co.stringstech.notice.schedules;

import co.stringstech.notice.MusicPlayer;
import timber.log.Timber;

/**
 * Created by tangxuyao on 2017/9/12.
 * StopRelaxMusic
 */

public class StopRelaxMusic extends BaseSchedule {
    private MusicPlayer musicPlayer;

    public StopRelaxMusic(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }

    @Override
    public String getName() {
        return "StopMusic";
    }

    @Override
    public void executeNow() {
        musicPlayer.stop();
        Timber.i("StopRelaxMusic");
    }
}
