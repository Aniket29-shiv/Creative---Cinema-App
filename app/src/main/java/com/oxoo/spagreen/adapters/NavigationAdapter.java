package com.oxoo.spagreen.adapters;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oxoo.spagreen.R;
import com.oxoo.spagreen.models.NavigationModel;

import java.util.ArrayList;
import java.util.List;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.OriginalViewHolder> {

    private List<NavigationModel> items = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    NavigationAdapter.OriginalViewHolder viewHolder;
    private String menuStyle;

    public interface OnItemClickListener {
        void onItemClick(View view, NavigationModel obj, int position,OriginalViewHolder holder);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }


    public NavigationAdapter(Context context, List<NavigationModel> items, String menuStyle) {
        this.items = items;
        ctx = context;
        this.menuStyle = menuStyle;
    }


    @Override
    public NavigationAdapter.OriginalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NavigationAdapter.OriginalViewHolder vh;
        View v;
        if (menuStyle.equals("grid")) {
             v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_nav_view, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_nav_view_2, parent, false);
        }
        vh = new NavigationAdapter.OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final NavigationAdapter.OriginalViewHolder holder, final int position) {

        NavigationModel obj = items.get(position);

        if (position==0){
            viewHolder=holder;
            if (menuStyle.equals("grid")) {
                holder.cardView.setCardBackgroundColor(ctx.getResources().getColor(R.color.colorPrimary));
                holder.name.setTextColor(ctx.getResources().getColor(R.color.white));
            } else {
                holder.selectedLayout.setBackground(ctx.getResources().getDrawable(R.drawable.round_grey_transparent));
                holder.name.setTextColor(ctx.getResources().getColor(R.color.colorPrimary));
            }

        }
        holder.name.setText(obj.getTitle());
//        holder.image.setImageResource(obj.getImg());
        holder.image.setImageResource(getImageId(ctx,obj.getImg()));
        if (menuStyle.equals("grid")) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, items.get(position), position,holder);
                    }
                }
            });
        } else {
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, items.get(position), position,holder);
                    }
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView name;
        public CardView cardView;
        public LinearLayout itemLayout;
        public LinearLayout selectedLayout;

        public OriginalViewHolder(View v) {
            super(v);
            image =  v.findViewById(R.id.image);
            name = v.findViewById(R.id.name);

            if (menuStyle.equals("grid")) {
                cardView=v.findViewById(R.id.card_view_home);
            }else {
                itemLayout=v.findViewById(R.id.item_layout);
                selectedLayout=v.findViewById(R.id.selected_layout);
            }
        }

    }

    public void chanColor(NavigationAdapter.OriginalViewHolder holder,int pos,int bgColor){

        if (pos!=0){
            if (menuStyle.equals("grid")) {
                viewHolder.cardView.setCardBackgroundColor(ctx.getResources().getColor(bgColor));
                viewHolder.name.setTextColor(ctx.getResources().getColor(R.color.grey_60));
            } else {
                viewHolder.name.setTextColor(ctx.getResources().getColor(R.color.default_text));
                viewHolder.selectedLayout.setBackgroundColor(ctx.getResources()
                        .getColor(android.R.color.transparent));
            }
        }

        if (holder!=null){
            if (menuStyle.equals("grid")) {
                holder.cardView.setCardBackgroundColor(ctx.getResources().getColor(bgColor));
                holder.name.setTextColor(ctx.getResources().getColor(R.color.grey_60));
            } else {
                holder.name.setTextColor(ctx.getResources().getColor(R.color.default_text));
                holder.selectedLayout.setBackgroundColor(ctx.getResources()
                        .getColor(android.R.color.transparent));
            }

        }

    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
