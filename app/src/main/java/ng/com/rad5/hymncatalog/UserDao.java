package ng.com.rad5.hymncatalog;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM hymns ORDER BY title ASC")
    LiveData<List<Hymn>> getAll();

    @Query("SELECT * FROM hymns WHERE title LIKE :mTitle")
    Hymn findByTitle(String mTitle);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHymn(Hymn hymn);

    @Query("DELETE FROM hymns WHERE title LIKE :mTitle")
    void deleteHymn(String mTitle);
}
