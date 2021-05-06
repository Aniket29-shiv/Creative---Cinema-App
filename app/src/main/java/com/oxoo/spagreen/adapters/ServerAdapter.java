package com.oxoo.spagreen.adapters;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxoo.spagreen.DetailsActivity;
import com.oxoo.spagreen.R;
import com.oxoo.spagreen.models.CommonModels;

import java.util.ArrayList;
import java.util.List;

public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.OriginalViewHolder> {

    private String type;
    private List<CommonModels> items = new ArrayList<>();
    private Context ctx;

    private ServerAdapter.OnItemClickListener mOnItemClickListener;

    private ServerAdapter.OriginalViewHolder viewHolder;



    public interface OnItemClickListener {
        void onItemClick(View view, CommonModels obj, int position, OriginalViewHolder holder);
        void getFirstUrl(String url);
        void hideDescriptionLayout();
    }

    public void setOnItemClickListener(ServerAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;

    }



    public ServerAdapter(Context context, List<CommonModels> items, String type) {
        this.items = items;
        ctx = context;
        this.type = type;
    }


    @Override
    public ServerAdapter.OriginalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ServerAdapter.OriginalViewHolder vh;
        View v ;
        if (type.equals("tv")) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_server, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_server_two, parent, false);
        }
        vh = new ServerAdapter.OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ServerAdapter.OriginalViewHolder holder, final int position) {

        CommonModels obj = items.get(position);
        holder.name.setText(obj.getTitle());

        if (position==0 && type.equals("tv")){
            viewHolder=holder;

            ((DetailsActivity)ctx).initMoviePlayer(obj.getStremURL(),obj.getServerType(),ctx);

            holder.name.setTextColor(ctx.getResources().getColor(R.color.colorPrimary));

            ((DetailsActivity)ctx).initServerTypeForTv(obj.getServerType());

            if (mOnItemClickListener != null) {
                mOnItemClickListener.getFirstUrl(obj.getStremURL());
            }

        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, items.get(position), position,holder);
                    mOnItemClickListener.hideDescriptionLayout();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public CardView cardView;

        public OriginalViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            cardView=v.findViewById(R.id.card_view_home);
        }
    }

    public void chanColor(ServerAdapter.OriginalViewHolder holder, int pos){

        /*if (pos!=0){
            viewHolder.name.setTextColor(ctx.getResources().getColor(R.color.grey_60));
        }*/

        if (holder!=null){
            holder.name.setTextColor(ctx.getResources().getColor(R.color.grey_60));
        }



    }

}