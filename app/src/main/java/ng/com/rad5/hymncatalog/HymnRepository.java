package ng.com.rad5.hymncatalog;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class HymnRepository {

    private UserDao userDao;
    private LiveData<List<Hymn>> allHymns;

    HymnRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        allHymns = userDao.getAll();
    }

    LiveData<List<Hymn>> getAllHymns() {
        return allHymns;
    }

    public void insert(Hymn word) {
        new insertAsyncTask(userDao).execute(word);
    }

    public void delete(String title){
        userDao.deleteHymn(title);
    }

    private static class insertAsyncTask extends AsyncTask<Hymn, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Hymn... params) {
            mAsyncTaskDao.insertHymn(params[0]);
            return null;
        }
    }
}
