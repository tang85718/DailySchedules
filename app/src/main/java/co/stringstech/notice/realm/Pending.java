package co.stringstech.notice.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tangxuyao on 2017/9/29.
 * Pending
 */

public class Pending extends RealmObject {

    @PrimaryKey
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
