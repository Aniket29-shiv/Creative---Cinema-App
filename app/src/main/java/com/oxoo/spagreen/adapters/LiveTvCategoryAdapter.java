package com.oxoo.spagreen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oxoo.spagreen.R;
import com.oxoo.spagreen.network.model.LiveTvCategory;

import java.util.List;

public class LiveTvCategoryAdapter extends RecyclerView.Adapter<LiveTvCategoryAdapter.ViewHolder> {

    private Context context;
    private List<LiveTvCategory> liveTvCategories;

    public LiveTvCategoryAdapter(Context context, List<LiveTvCategory> liveTvCategories) {
        this.context = context;
        this.liveTvCategories = liveTvCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_livetv_category_item, parent,
                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LiveTvCategory liveTvCategory = liveTvCategories.get(position);

        if (liveTvCategory != null) {

            holder.categoryNameTv.setText(liveTvCategory.getTitle());

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);

            ChannelAdapter channelAdapter = new ChannelAdapter(context, liveTvCategory.getChannels());

            holder.channelRv.setLayoutManager(layoutManager);
            holder.channelRv.setAdapter(channelAdapter);
            holder.channelRv.setHasFixedSize(true);

        }

    }

    @Override
    public int getItemCount() {
        return liveTvCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryNameTv;
        Button moreBt;
        RecyclerView channelRv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryNameTv = itemView.findViewById(R.id.tv_name);
            moreBt = itemView.findViewById(R.id.btn_more);
            channelRv = itemView.findViewById(R.id.recyclerView);
            moreBt.setVisibility(View.GONE);
        }
    }
}
