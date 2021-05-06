package com.oxoo.spagreen.database.homeContent;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oxoo.spagreen.models.home_content.HomeContent;

public class HomeContentViewModel extends AndroidViewModel {
    private HomeContentRepository repository;
    private LiveData<HomeContent> homeContentLiveData;

    public HomeContentViewModel(@NonNull Application application) {
        super(application);
        repository = new HomeContentRepository(application);
        homeContentLiveData = repository.getLiveHomeContentData();
    }

    public void insert(HomeContent homeContent){
        repository.insert(homeContent);
    }

    public void update(HomeContent homeContent){
        repository.update(homeContent);
    }

    public void deleteAll(){
        repository.delete();
    }

    public LiveData<HomeContent> getAllContents(){
        return homeContentLiveData;
    }

    public HomeContent getHomeContent(){
        return repository.getHomeContentData();
    }
}
