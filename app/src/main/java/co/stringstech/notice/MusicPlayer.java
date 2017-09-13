package co.stringstech.notice;

import android.media.MediaPlayer;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import timber.log.Timber;

/**
 * Created by tangxuyao on 2017/9/12.
 * MusicPlayer
 */

public class MusicPlayer implements MediaPlayer.OnCompletionListener {

    private ArrayList<String> lists = new ArrayList<>();
    private MediaPlayer player;
    private boolean auto = true;

    public MusicPlayer() {
    }

    private void buildPlaylist() {
        lists.clear();

        String path = Environment.getExternalStorageDirectory().getPath();
        Timber.i("SD:%s", path);

        File[] files = new File(path, "/KuwoMusic/music").listFiles();
        for (File f : files) {
            String absolutePath = f.getAbsolutePath();
            Timber.d("KuWo: %s", absolutePath);
            lists.add(absolutePath);
        }

        files = new File(path, "/xiami/audios").listFiles();
        for (File f : files) {
            String absolutePath = f.getAbsolutePath();
            Timber.d("XiaMi: %s", absolutePath);
            lists.add(absolutePath);
        }
    }

    public void playOne() {
        buildPlaylist();
        auto = false;
        play();
    }

    public void playRandom() {
        buildPlaylist();
        auto = true;
        play();
    }

    public void stop() {
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

    public void resume() {
        if (player != null) {
            player.setVolume(1f, 1f);
        }
    }

    private void play() {
        if (lists.isEmpty()) {
            return;
        }

        Random random = new Random();
        int rand = random.nextInt(lists.size());
        String path = lists.get(rand);

        lists.remove(rand);
        Timber.i("当前播放歌曲：%s", path);

        try {
            Safeguard();
            player = new MediaPlayer();
            player.setOnCompletionListener(this);
            player.setScreenOnWhilePlaying(true);
            player.setDataSource(path);
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
        if (auto) {
            play();
        }
    }
}
