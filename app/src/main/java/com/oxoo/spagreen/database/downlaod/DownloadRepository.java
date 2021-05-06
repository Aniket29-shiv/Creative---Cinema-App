package com.oxoo.spagreen.database.downlaod;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.oxoo.spagreen.models.DownloadInfo;

import java.util.ArrayList;
import java.util.List;

public class DownloadRepository {
    private static final String TAG = "DownloadRepository";

    private ArrayList<DownloadInfo> downloadList = new ArrayList<>();
    private DownloadDao downloadDao;
    private LiveData<List<DownloadInfo>> allDownloadData;

    public DownloadRepository(Application application) {
        DownloadDatabase database = DownloadDatabase.getInstance(application);
        downloadDao = database.downloadDao();
        allDownloadData = downloadDao.getAllDownloads();
    }

    public void insert(DownloadInfo info){
        DownloadDatabase.databaseWriteExecutor.execute(() -> {
            downloadDao.insertDownloadInfo(info);
        });
    }

    public void update(DownloadInfo info){
        DownloadDatabase.databaseWriteExecutor.execute(() ->{
            downloadDao.updateDownload(info);
        });
    }

    public void delete(DownloadInfo downloadInfo){
        DownloadDatabase.databaseWriteExecutor.execute(() -> {
            downloadDao.deleteDownload(downloadInfo);
        });
    }

    public void deleteAll(){
        DownloadDatabase.databaseWriteExecutor.execute(() ->{
            downloadDao.deleteAll();
        });
    }

    public LiveData<List<DownloadInfo>> getAllDownloadData(){
        return allDownloadData;
    }


}
