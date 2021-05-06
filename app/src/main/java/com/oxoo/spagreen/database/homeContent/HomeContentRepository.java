package com.oxoo.spagreen.database.homeContent;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oxoo.spagreen.models.home_content.HomeContent;

public class HomeContentRepository {
    private HomeContentDao homeContentDao;
    private LiveData<HomeContent> homeContentLiveData;
    private static HomeContent homeContent = null;

    public HomeContentRepository(Application application){
        HomeContentDatabase database = HomeContentDatabase.getInstance(application);
        homeContentDao = database.homeContentDao();
        homeContentLiveData = homeContentDao.getLiveHomeContentData();

    }

    public void insert(HomeContent homeContent){
       // new InsertHomeContentAsyncTask(homeContentDao).execute(homeContent);
        HomeContentDatabase.databaseWriteExecutor.execute(() -> {
            homeContentDao.insertHomeContentData(homeContent);
        });
    }

    public void update(HomeContent homeContent){
        new UpdateHomeContentAsyncTask(homeContentDao).execute(homeContent);
    }

    public LiveData<HomeContent> getLiveHomeContentData(){
        return homeContentLiveData;
    }

    public HomeContent getHomeContentData(){
        HomeContentDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                homeContent = homeContentDao.getHomeContentData();
                //Log.e("HomeContentDatabase", "Movie: " + homeContent.getLatestMovies().get(0).getTitle());

            }
        });
        return homeContent;
    }

    public void delete(){
        new DeleteAllHomeContentAsyncTask(homeContentDao).execute();
    }


    private class InsertHomeContentAsyncTask extends AsyncTask<HomeContent, Void, Void>{
        private HomeContentDao homeContentDao;

        public InsertHomeContentAsyncTask(HomeContentDao homeContentDao) {
            this.homeContentDao = homeContentDao;
        }

        @Override
        protected Void doInBackground(HomeContent... homeContents) {
            homeContentDao.insertHomeContentData(homeContents[0]);
            Log.e("HomeContentDatabase", "Data inserted to database");
            return null;
        }
    }

    private class UpdateHomeContentAsyncTask extends AsyncTask<HomeContent, Void, Void>{
        private HomeContentDao homeContentDao;

        public UpdateHomeContentAsyncTask(HomeContentDao dao) {
            this.homeContentDao = dao;
        }

        @Override
        protected Void doInBackground(HomeContent... homeContents) {
            homeContentDao.updateHomeContentData(homeContents[0]);
            Log.e("HomeContentDatabase", "Data updated to database");
            return null;
        }
    }

    private class DeleteAllHomeContentAsyncTask extends AsyncTask<Void, Void, Void>{
        private HomeContentDao homeContentDao;

        public DeleteAllHomeContentAsyncTask(HomeContentDao homeContentDao) {
            this.homeContentDao = homeContentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            homeContentDao.deleteAllHomeContentData();
            Log.e("HomeContentDatabase", "All HomeContent data has been deleted.");
            return null;
        }
    }


}
