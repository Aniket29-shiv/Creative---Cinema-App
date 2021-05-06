package com.oxoo.spagreen.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxoo.spagreen.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.oxoo.spagreen.models.GetCommentsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.OriginalViewHolder> {

    private List<GetCommentsModel> items = new ArrayList<>();
    private Context ctx;



    public ReplyAdapter(Context context, List<GetCommentsModel> items) {
        this.items = items;
        ctx = context;

    }


    @Override
    public ReplyAdapter.OriginalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ReplyAdapter.OriginalViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_reply, parent, false);
        vh = new ReplyAdapter.OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ReplyAdapter.OriginalViewHolder holder, final int position) {

        final GetCommentsModel obj = items.get(position);


        holder.name.setText(obj.getUserName());
        holder.comment.setText(obj.getComments());

        Picasso.get().load(obj.getUserImgUrl()).into(holder.imageView);




    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        private TextView name,comment;
        private View lyt_parent;
        private CircularImageView imageView;


        public OriginalViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            lyt_parent=v.findViewById(R.id.lyt_parent);
            imageView=v.findViewById(R.id.profile_img);
            comment=v.findViewById(R.id.comments);
        }
    }

}