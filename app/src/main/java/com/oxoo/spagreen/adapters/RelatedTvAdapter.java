package com.oxoo.spagreen.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oxoo.spagreen.LoginActivity;
import com.oxoo.spagreen.R;
import com.oxoo.spagreen.SubscriptionActivity;
import com.oxoo.spagreen.models.CommonModels;
import com.oxoo.spagreen.utils.ItemAnimation;
import com.oxoo.spagreen.utils.PreferenceUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RelatedTvAdapter  extends RecyclerView.Adapter<RelatedTvAdapter.RelatedViewHolder> {
    private List<CommonModels> items = new ArrayList<>();
    private Activity ctx;
    private String fromActivity;
    private RelatedTvClickListener listener;

    private int lastPosition = -1;
    private boolean on_attach = true;
    private int animation_type = 2;

    public RelatedTvAdapter(List<CommonModels> items, Activity ctx) {
        this.items = items;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public RelatedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_live_tv_home, parent, false);
        return new RelatedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedViewHolder holder, int position) {
        final CommonModels obj = items.get(position);
        holder.name.setText(obj.getTitle());
        Picasso.get().load(obj.getImageUrl()).into(holder.image);

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check app config data
                //mandatory login enabled or not
                if (PreferenceUtils.isMandatoryLogin(ctx)){
                    if (PreferenceUtils.isLoggedIn(ctx)){
                        if (obj.isPaid.equals("1")){
                            if (PreferenceUtils.isActivePlan(ctx)){
                                if (PreferenceUtils.isValid(ctx)){
                                    playContent(obj);
                                }else {
                                    PreferenceUtils.updateSubscriptionStatus(ctx);
                                }

                            }else {
                                //user doesnt have subscription
                                ctx.startActivity(new Intent(ctx, SubscriptionActivity.class));
                            }
                        }else {
                            //content is not paid
                            playContent(obj);
                        }

                    }else {
                        //user not logged in
                        ctx.startActivity(new Intent(ctx, LoginActivity.class));
                    }
                }else {
                    playContent(obj);
                }


            }
        });

        setAnimation(holder.itemView, position);
    }

    private void playContent(CommonModels obj) {
        if (listener != null){
            listener.onRelatedTvClicked(obj);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class RelatedViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView name;
        public View lyt_parent;

        public RelatedViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name =  itemView.findViewById(R.id.name);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
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

    public interface RelatedTvClickListener{
        void onRelatedTvClicked(CommonModels obj);
    }

    public void setListener(RelatedTvClickListener listener) {
        this.listener = listener;
    }
}
