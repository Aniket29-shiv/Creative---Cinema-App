package com.oxoo.spagreen.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oxoo.spagreen.DownloadActivity;
import com.oxoo.spagreen.R;
import com.oxoo.spagreen.models.VideoFile;
import com.oxoo.spagreen.utils.Constants;
import com.oxoo.spagreen.utils.Tools;

import java.io.File;
import java.util.List;

public class DownloadHistoryAdapter extends RecyclerView.Adapter<DownloadHistoryAdapter.ViewHolder> {
    private OnDeleteDownloadFileListener listener;
    private Context context;
    private List<VideoFile> videoFiles;

    public DownloadHistoryAdapter(Context context, List<VideoFile> videoFiles) {
        this.context = context;
        this.videoFiles = videoFiles;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_download_history, parent,
                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final VideoFile videoFile = videoFiles.get(position);
        holder.fileNameTv.setText(videoFile.getFileName());
        holder.fileSizeTv.setText("Size: " + Tools.byteToMb(videoFile.getTotalSpace()));
        holder.dateTv.setText(Tools.milliToDate(videoFile.getLastModified()));
        holder.item_holder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null){
                    listener.onDeleteDownloadFile(videoFile);
                }
                return false;
            }
        });

        holder.item_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoFile.getPath()));
                intent.setDataAndType(Uri.parse(videoFile.getPath()), "video/*");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoFiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fileNameTv, fileSizeTv, dateTv;
        RelativeLayout item_holder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fileNameTv = itemView.findViewById(R.id.file_name_tv);
            fileSizeTv = itemView.findViewById(R.id.file_size_tv);
            dateTv = itemView.findViewById(R.id.date_tv);
            item_holder = itemView.findViewById(R.id.item_view);

        }
    }

    public interface OnDeleteDownloadFileListener{
        void onDeleteDownloadFile(VideoFile videoFile);
    }

    public void setListener(OnDeleteDownloadFileListener listener) {
        this.listener = listener;
    }
}
