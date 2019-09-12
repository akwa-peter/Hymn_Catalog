package ng.com.rad5.hymncatalog;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "hymns")
public class Hymn {

    @PrimaryKey(autoGenerate = true)
    private int _Uid;

    @ColumnInfo(name = "title")
    private String _HymnTitle;

    @ColumnInfo(name = "hymn body")
    private String _Hymn;

    public Hymn(String _HymnTitle, String _Hymn) {
        this._HymnTitle = _HymnTitle;
        this._Hymn = _Hymn;
    }

    public void set_Uid(int uid){
        _Uid = uid;
    }

    public int get_Uid() {
        return _Uid;
    }

    public String get_HymnTitle() {
        return _HymnTitle;
    }

    public String get_Hymn() {
        return _Hymn;
    }
}
