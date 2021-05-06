package com.oxoo.spagreen.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oxoo.spagreen.DetailsActivity;
import com.oxoo.spagreen.R;
import com.oxoo.spagreen.network.model.TvModel;
import com.oxoo.spagreen.utils.ItemAnimation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LiveTvAdapter2 extends RecyclerView.Adapter<LiveTvAdapter2.ViewHolder> {

    private Context context;
    private List<TvModel>tvModels;

    private int lastPosition = -1;
    private boolean on_attach = true;
    private int animation_type = 2;

    public LiveTvAdapter2(Context context, List<TvModel> tvModels) {
        this.context = context;
        this.tvModels = tvModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_live_tv_home, parent,
                false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TvModel tvModel = tvModels.get(position);
        if (tvModel != null) {

            holder.name.setText(tvModel.getTvName());
            Picasso.get().load(tvModel.getPosterUrl()).into(holder.image);

        }
        setAnimation(holder.itemView, position);


    }

    @Override
    public int getItemCount() {
        return tvModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public View lyt_parent;


        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image);
            name = v.findViewById(R.id.name);
            lyt_parent = v.findViewById(R.id.lyt_parent);

            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, DetailsActivity.class);
                    intent.putExtra("vType","tv");
                    intent.putExtra("id",tvModels.get(getAdapterPosition()).getLiveTvId());
                    context.startActivity(intent);
                }
            });

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
