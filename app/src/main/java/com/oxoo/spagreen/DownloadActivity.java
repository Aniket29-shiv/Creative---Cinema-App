package com.oxoo.spagreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.oxoo.spagreen.adapters.DownloadHistoryAdapter;
import com.oxoo.spagreen.adapters.FileDownloadingAdapter;
import com.oxoo.spagreen.database.downlaod.DownloadViewModel;
import com.oxoo.spagreen.models.DownloadInfo;
import com.oxoo.spagreen.models.Work;
import com.oxoo.spagreen.models.VideoFile;
import com.oxoo.spagreen.utils.Constants;
import com.oxoo.spagreen.utils.RtlUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity implements  DownloadHistoryAdapter.OnDeleteDownloadFileListener {
    public static DownloadActivity instance;
    public static final String ACTION_PLAY_VIDEO = "play_video";
    public static final String TAG = "DownloadActivity";
    private RecyclerView downloadingRv, downloadedFileRv;
    private ProgressBar progressBar;
    private TextView amountTv;
    private LinearLayout progressLayout;

    Toolbar toolbar;
    CoordinatorLayout coordinatorLayout;

    private TextView downloadStatusTv;
    private TextView downloadedFileTV, downloadingFileTv;
    private Work work;
    private ImageView startPauseIv, cancelIV;
    private boolean isDownloading = true;
    private int actionPosition;
    private FileDownloadingAdapter downloadingAdapter;
    private DownloadHistoryAdapter downloadHistoryAdapter;

    private List<VideoFile> videoFiles = new ArrayList<>();
    private boolean isDark;
    private List<DownloadInfo> downloadingFileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RtlUtils.setScreenDirection(this);
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("push", MODE_PRIVATE);
        isDark = sharedPreferences.getBoolean("dark", false);

        if (isDark) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppThemeLight);
        }

        setContentView(R.layout.activity_download);


        downloadedFileTV = findViewById(R.id.downloaded_file_tv);
        downloadingFileTv = findViewById(R.id.downloading_file_tv);
        downloadedFileRv = findViewById(R.id.downloaded_file_rv);
        toolbar = findViewById(R.id.appBar);
        coordinatorLayout = findViewById(R.id.coordinator_lyt);
        progressLayout = findViewById(R.id.progress_layout);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Downloads");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (isDark) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.black_window_light));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        /*downloading recycler view*/
        downloadingRv = findViewById(R.id.download_rv);
        FileDownloadingAdapter downloadingAdapter = new FileDownloadingAdapter(DownloadActivity.this, downloadingFileList);
        downloadingRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        downloadingRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        downloadingRv.hasFixedSize();
        downloadingRv.setAdapter(downloadingAdapter);

        /*downloaded recycler view*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        downloadedFileRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        downloadedFileRv.setLayoutManager(layoutManager);
        downloadHistoryAdapter = new DownloadHistoryAdapter(this, videoFiles);
        downloadedFileRv.setHasFixedSize(true);
        downloadedFileRv.setAdapter(downloadHistoryAdapter);
        downloadHistoryAdapter.setListener(this);

        DownloadViewModel viewModel = ViewModelProviders.of(this).get(DownloadViewModel.class);
        viewModel.getAllDownloads().observe(this, new Observer<List<DownloadInfo>>() {
            @Override
            public void onChanged(List<DownloadInfo> list) {
                if (list != null && list.size() > 0){
                    //update downloading adapter
                    coordinatorLayout.setVisibility(View.GONE);
                    downloadingRv.setVisibility(View.VISIBLE);
                    downloadingFileTv.setVisibility(View.VISIBLE);
                    downloadingFileList.clear();
                    downloadingFileList.addAll(list);
                    downloadingAdapter.notifyDataSetChanged();
                }else {
                    downloadingRv.setVisibility(View.GONE);
                    downloadingFileTv.setVisibility(View.GONE);
                    coordinatorLayout.setVisibility(View.VISIBLE);
                }
                getDownloadFiles();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDownloadFiles() {
        videoFiles.clear();
        downloadedFileRv.removeAllViews();

        String path = Constants.getDownloadDir(DownloadActivity.this) + "/" + getResources().getString(R.string.app_name);
        File directory = new File(path);
        File[] files = directory.listFiles();

        if (files != null && files.length > 0) {
            for (File file : files) {
                String fileName = file.getName();
                String filePath = file.getPath();

                if (!fileName.contains(".temp")) {
                    VideoFile vf = new VideoFile();
                    vf.setFileName(fileName);
                    vf.setLastModified(file.lastModified());
                    vf.setTotalSpace(file.length());
                    vf.setPath(filePath);
                    //vf.setFileExtension(extension);
                    videoFiles.add(vf);
                }
            }

            if (videoFiles.size() > 0) {
                coordinatorLayout.setVisibility(View.GONE);
                downloadHistoryAdapter.notifyDataSetChanged();
            } else {
                downloadedFileTV.setVisibility(View.GONE);
                downloadingRv.setVisibility(View.GONE);
                downloadedFileTV.setVisibility(View.GONE);
                coordinatorLayout.setVisibility(View.VISIBLE);
            }
        }else {
            downloadedFileTV.setVisibility(View.GONE);
            downloadingRv.setVisibility(View.GONE);
            downloadedFileTV.setVisibility(View.GONE);
            coordinatorLayout.setVisibility(View.VISIBLE);
        }
    }

    public void progressHideShowControl() {
        if (progressLayout.getVisibility() == View.VISIBLE) {
            progressLayout.setVisibility(View.GONE);
        } else {
            progressLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(playVideoBroadcast);
    }


    @Override
    public void onDeleteDownloadFile(VideoFile videoFile) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Attention");
        dialog.setMessage("Do you want to delete this file?");
        dialog.setIcon(R.drawable.ic_warning);
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFile(videoFile);
            }
        });
        dialog.setNegativeButton("No", null);
        dialog.show();

    }

    private void deleteFile(VideoFile videoFile) {
        File file = new File(videoFile.getPath());
        if (file.exists()) {
            try {
                boolean isDeleted = file.getCanonicalFile().delete();
                if (isDeleted) {
                    Toast.makeText(this, "File deleted successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (file.exists()) {
                boolean isDeleted = getApplicationContext().deleteFile(file.getName());
                if (isDeleted) {
                    Toast.makeText(this, "File deleted successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.something_went_text), Toast.LENGTH_SHORT).show();
                }
            }
        }

        getDownloadFiles();
    }
}
