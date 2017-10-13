package co.stringstech.notice;

import android.os.Environment;
import android.widget.Toast;

import java.io.File;

import co.stringstech.notice.realm.Pending;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by tangxuyao on 2017/9/29.
 * PlaylistBuilder
 */

public class PlaylistBuilder {

    private App app;

    public PlaylistBuilder(App app) {
        this.app = app;
    }

    public void build() {
        Realm realm = app.getRealm();

        RealmQuery<Pending> query = realm.where(Pending.class);
        RealmResults<Pending> results = query.findAll();
        if (!results.isEmpty()) {
            Timber.d("歌曲还没有播放完，不进行导入操作");
            return;
        }

        String path = Environment.getExternalStorageDirectory().getPath();
        Timber.i("SD:%s", path);

        File[] files = new File(path, "/KuwoMusic/music").listFiles();
        for (File f : files) {
            String absolutePath = f.getAbsolutePath();
            realm.beginTransaction();
            Pending pending = realm.createObject(Pending.class, absolutePath);
            realm.copyToRealm(pending);
            realm.commitTransaction();

            Timber.d("KuWo: %s", absolutePath);
        }

        files = new File(path, "/xiami/audios").listFiles();
        for (File f : files) {
            String absolutePath = f.getAbsolutePath();

            realm.beginTransaction();
            Pending pending = realm.createObject(Pending.class, absolutePath);
            realm.copyToRealm(pending);
            realm.commitTransaction();
            Timber.d("XiaMi: %s", absolutePath);
        }

        realm.close();
    }
}
