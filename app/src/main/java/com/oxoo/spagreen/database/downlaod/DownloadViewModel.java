package com.oxoo.spagreen.database.downlaod;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oxoo.spagreen.models.DownloadInfo;

import java.util.List;

public class DownloadViewModel extends AndroidViewModel {
    private static final String TAG = "DownloadViewModel";
    private DownloadRepository repository;
    private LiveData<List<DownloadInfo>> allDownloads;

    public DownloadViewModel(@NonNull Application application) {
        super(application);
        repository = new DownloadRepository(application);
        allDownloads = repository.getAllDownloadData();
    }

    public void insert(DownloadInfo downloadInfo){
        Log.e(TAG, "insert: id = " + downloadInfo.getDownloadId());
        repository.insert(downloadInfo);
    }

    public void update(DownloadInfo downloadInfo){
        Log.e(TAG, "update: id = " + downloadInfo.getDownloadId());
        repository.update(downloadInfo);
    }

    public void delete(DownloadInfo downloadInfo){
        Log.e(TAG, "delete: id = " + downloadInfo.getDownloadId());
        repository.delete(downloadInfo);
    }

    public void deleteAllDownloads(){
        repository.deleteAll();
    }

    public LiveData<List<DownloadInfo>> getAllDownloads(){
        return allDownloads;
    }
}
