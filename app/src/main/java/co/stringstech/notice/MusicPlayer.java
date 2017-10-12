package co.stringstech.notice;

import android.media.MediaPlayer;

import java.io.IOException;
import java.util.Random;

import co.stringstech.notice.realm.Pending;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by tangxuyao on 2017/9/12.
 * MusicPlayer
 */

public class MusicPlayer implements MediaPlayer.OnCompletionListener {

    private App app;
    private MediaPlayer player;
    private boolean isLooping = true;
    private boolean isStopped = false;
    private int maxRetryCount = 5;

    public MusicPlayer(App app) {
        this.app = app;
    }

    public void playOne() {
        isLooping = false;
        play(getRandomSong());
    }

    public void playRandom() {
        isLooping = true;
        isStopped = false;
        play(getRandomSong());
    }

    private String getRandomSong() {
        Realm realm = app.getRealm();
        RealmQuery<Pending> query = realm.where(Pending.class);
        RealmResults<Pending> results = query.findAll();

        if (results.isEmpty()) {
            if (maxRetryCount-- <= 0) {
                return "";
            }

            app.builder.build();
            return getRandomSong();
        }

        Random random = new Random();
        int rand = random.nextInt(results.size());
        Pending song = results.get(rand);
        String path = song.getPath();

        realm.beginTransaction();
        results.deleteFromRealm(rand);
        realm.commitTransaction();

        realm.close();
        return path;
    }

    public void stop() {
        isStopped = true;
    }

    public void stopForce() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    public void duck() {
        if (player != null) {
            player.setVolume(0.1f, 0.1f);
        }
    }

    public void unduck() {
        if (player != null) {
            player.setVolume(1f, 1f);
        }
    }

    private void play(String path) {
        Timber.i("当前播放歌曲：%s", path);

        try {
            Safeguard();
            player = new MediaPlayer();
            player.setOnCompletionListener(this);
            player.setDataSource(path);
            player.setVolume(1f, 1f);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Safeguard() {
        if (player == null) {
            return;
        }

        if (player.isPlaying()) {
            player.stop();
        }

        player.release();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (isStopped) {
            stopForce();
            return ;
        }

        if (isLooping) {
            play(getRandomSong());
        } else {
            stopForce();
        }
    }
}
