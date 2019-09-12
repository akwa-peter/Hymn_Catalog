package ng.com.rad5.hymncatalog;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class HymnViewModel extends AndroidViewModel {

    private HymnRepository mRepository;
    private LiveData<List<Hymn>> allHymns;

    public HymnViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HymnRepository(application);
        allHymns = mRepository.getAllHymns();
    }

    LiveData<List<Hymn>> getAllHymns() { return allHymns; }

    public void insert(Hymn hymn) { mRepository.insert(hymn); }

    public void delete(String title){
        mRepository.delete(title);
    }
}
