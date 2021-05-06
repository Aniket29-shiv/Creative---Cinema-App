package com.oxoo.spagreen.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.oxoo.spagreen.R;
import com.oxoo.spagreen.database.downlaod.DownloadViewModel;
import com.oxoo.spagreen.models.single_details.DownloadLink;
import com.oxoo.spagreen.service.DownloadHelper;
import com.oxoo.spagreen.utils.ItemAnimation;
import com.stripe.model.Card;

import java.util.List;

public class EpisodeDownloadAdapter extends RecyclerView.Adapter<EpisodeDownloadAdapter.SeasonDownloadViewModel> {
    private static final String TAG = "SeasonDownloadAdapter";
    private int lastPosition = -1;
    private boolean on_attach = true;
    private int animation_type = 2;

    private Activity context;
    private List<DownloadLink> downloadLinks;
    private DownloadViewModel viewModel;

    public EpisodeDownloadAdapter(Activity context, List<DownloadLink> downloadLinks, DownloadViewModel viewModel) {
        this.context = context;
        this.downloadLinks = downloadLinks;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public SeasonDownloadViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.season_download_item,  parent, false);
        return new SeasonDownloadViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonDownloadViewModel holder, int position) {
        if (downloadLinks != null){
            DownloadLink downloadLink = downloadLinks.get(position);
            holder.episodeName.setText(downloadLink.getLabel());
            holder.seasonDownloadLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (downloadLink.isInAppDownload()) {
                        //in app download enabled
                        if (viewModel != null) {
                            DownloadHelper helper = new DownloadHelper(
                                    downloadLink.getLabel(),
                                    downloadLink.getDownloadUrl(),
                                    context,
                                    viewModel);
                            helper.downloadFile();
                        }

                    } else {
                        String url = downloadLink.getDownloadUrl();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        context.startActivity(i);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return downloadLinks.size();
    }

    class SeasonDownloadViewModel extends RecyclerView.ViewHolder{
        private TextView episodeName;
        private ImageView downloadImageView;
        private CardView seasonDownloadLayout;

        public SeasonDownloadViewModel(@NonNull View itemView) {
            super(itemView);
            episodeName             = itemView.findViewById(R.id.episodeNameOfSeasonDownload);
            downloadImageView       = itemView.findViewById(R.id.downloadImageViewOfSeasonDownload);
            seasonDownloadLayout    = itemView.findViewById(R.id.seasonDownloadLayout);
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }

        });



        super.onAttachedToRecyclerView(recyclerView);
    }

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
            lastPosition = position;
        }
    }
}
