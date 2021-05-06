package com.oxoo.spagreen.database.continueWatching;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContinueWatchingViewModel extends AndroidViewModel {
    private ContinueWatchingRepo continueWatchingRepo;
    private LiveData<List<ContinueWatchingModel>> allContents;

    public ContinueWatchingViewModel(@NonNull Application application) {
        super(application);
        continueWatchingRepo = new ContinueWatchingRepo(application);
        allContents = continueWatchingRepo.getAllContents();
    }
    public void insert(ContinueWatchingModel continueWatchingModel){
        continueWatchingRepo.insert(continueWatchingModel);
    }
    public void update(ContinueWatchingModel continueWatchingModel){
        continueWatchingRepo.update(continueWatchingModel);
    }
    public void delete(ContinueWatchingModel continueWatchingModel){
        continueWatchingRepo.delete(continueWatchingModel);
    }
    public void deleteAllContent(){
        continueWatchingRepo.deleteAllContent();
    }

    public LiveData<List<ContinueWatchingModel>> getAllContents(){
        return allContents;
    }
}
