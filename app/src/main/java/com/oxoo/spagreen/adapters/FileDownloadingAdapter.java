package com.oxoo.spagreen.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oxoo.spagreen.R;
import com.oxoo.spagreen.models.DownloadInfo;

import java.util.ArrayList;
import java.util.List;

public class FileDownloadingAdapter extends RecyclerView.Adapter<FileDownloadingAdapter.ViewHolder> {
    private Context context;
    private List<DownloadInfo> downloadInfoList = new ArrayList<>();

    public FileDownloadingAdapter(Context context, List<DownloadInfo> downloadInfoList) {
        this.context = context;
        this.downloadInfoList = downloadInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.layout_file_download_item, parent,
                false);

        return new ViewHolder(v);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DownloadInfo info = downloadInfoList.get(position);
        holder.fileNameTv.setText(info.getFileName());
        holder.progressBar.setProgress(info.getPercentage());
        holder.downloadAmountTv.setText(info.getPercentage() + "%");
    }

    @Override
    public int getItemCount() {
        return downloadInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fileNameTv, downloadAmountTv;
        public ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fileNameTv = itemView.findViewById(R.id.file_name_tv);
            downloadAmountTv = itemView.findViewById(R.id.download_amount_tv);
            progressBar = itemView.findViewById(R.id.progressBarOne);
        }
    }

}
