package co.stringstech.notice;

import android.content.Context;
import android.media.MediaPlayer;

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
        File[] files = new File("/storage/sdcard0/KuwoMusic/music").listFiles();
        for (File f : files) {
            String absolutePath = f.getAbsolutePath();
            Timber.d("music: %s", absolutePath);
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
        if (player != null && player.isPlaying()) {
            return;
        }

        if (lists.isEmpty()) {
            return;
        }

        Random random = new Random();
        int rand = random.nextInt(lists.size());
        String path = lists.get(rand);

        lists.remove(rand);

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
