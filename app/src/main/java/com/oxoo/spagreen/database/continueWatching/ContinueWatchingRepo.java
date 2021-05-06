package com.oxoo.spagreen.database.continueWatching;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class ContinueWatchingRepo {

    private ArrayList<ContinueWatchingModel> contentArrayList = new ArrayList<>();

    private ContinueWatchingDao continueWatchingDao;
    private LiveData<List<ContinueWatchingModel>> allContents;

    public ContinueWatchingRepo(Application application) {
        ContinueWatchingDatabase continueWatchingDB = ContinueWatchingDatabase.getInstance(application);
        continueWatchingDao = continueWatchingDB.continueWatchingDao();
        allContents = continueWatchingDao.getAllContent();
    }

    public void insert(ContinueWatchingModel continueWatchingModel){
        new InsertContentAsyncTask(continueWatchingDao).execute(continueWatchingModel);

    }

    public void update(ContinueWatchingModel continueWatchingModel){
        new UpdateContentAsyncTask(continueWatchingDao).execute(continueWatchingModel);
    }

    public void delete(ContinueWatchingModel continueWatchingModel){
        new DeleteContentAsyncTask(continueWatchingDao).execute(continueWatchingModel);

    }
    public void deleteAllContent(){
        new DeleteAllContentAsyncTask(continueWatchingDao).execute();
    }

    public LiveData<List<ContinueWatchingModel>> getAllContents(){
        return allContents;
    }

    private static class InsertContentAsyncTask extends AsyncTask<ContinueWatchingModel, Void, Void>{
        private ContinueWatchingDao continueWatchingDao;

        private InsertContentAsyncTask(ContinueWatchingDao continueWatchingDao) {
            this.continueWatchingDao = continueWatchingDao;
        }

        @Override
        protected Void doInBackground(ContinueWatchingModel... continueWatchingModels) {
            continueWatchingDao.insertInfo(continueWatchingModels[0]);
            Log.e("123", "Inserted");
            return null;
        }
    }

    private static class UpdateContentAsyncTask extends AsyncTask<ContinueWatchingModel, Void, Void>{
        private ContinueWatchingDao continueWatchingDao;

        private UpdateContentAsyncTask(ContinueWatchingDao continueWatchingDao) {
            this.continueWatchingDao = continueWatchingDao;
        }

        @Override
        protected Void doInBackground(ContinueWatchingModel... continueWatchingModels) {
            continueWatchingDao.updateInfo(continueWatchingModels[0]);
            Log.e("123", "updated: " + continueWatchingModels[0].getPosition());
            return null;
        }
    }

    private static class DeleteContentAsyncTask extends AsyncTask<ContinueWatchingModel, Void, Void>{
        private ContinueWatchingDao continueWatchingDao;

        private DeleteContentAsyncTask(ContinueWatchingDao continueWatchingDao) {
            this.continueWatchingDao = continueWatchingDao;
        }

        @Override
        protected Void doInBackground(ContinueWatchingModel... continueWatchingModels) {
            continueWatchingDao.delete(continueWatchingModels[0]);
            return null;
        }
    }

    private static class DeleteAllContentAsyncTask extends AsyncTask<Void, Void, Void>{
        private ContinueWatchingDao continueWatchingDao;

        private DeleteAllContentAsyncTask(ContinueWatchingDao continueWatchingDao) {
            this.continueWatchingDao = continueWatchingDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            continueWatchingDao.deleteAll();
            return null;
        }
    }

}
