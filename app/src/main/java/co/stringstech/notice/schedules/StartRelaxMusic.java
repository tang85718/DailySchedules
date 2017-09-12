package co.stringstech.notice.schedules;

import co.stringstech.notice.MusicPlayer;
import timber.log.Timber;

/**
 * Created by tangxuyao on 2017/9/12.
 * StartRelaxMusic
 */

public class StartRelaxMusic implements BaseSchedule {
    private MusicPlayer musicPlayer;

    public StartRelaxMusic(MusicPlayer rt) {
        this.musicPlayer = rt;
    }

    @Override
    public void execute() {
        musicPlayer.playRandom();
        Timber.i("StartRelaxMusic");
    }
}
