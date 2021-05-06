package com.oxoo.spagreen.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oxoo.spagreen.ItemMovieActivity;
import com.oxoo.spagreen.R;
import com.oxoo.spagreen.models.CastCrew;
import com.oxoo.spagreen.utils.ItemAnimation;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.oxoo.spagreen.ItemMovieActivity.INTENT_TYPE_STAR;

public class CastCrewAdapter extends RecyclerView.Adapter<CastCrewAdapter.ViewHolder> {

    private Context context;
    private List<CastCrew> castCrewList;

    private int lastPosition = -1;
    private boolean on_attach = true;
    private int animation_type = 2;

    public CastCrewAdapter(Context context, List<CastCrew> castCrewList) {
        this.context = context;
        this.castCrewList = castCrewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.layout_cast_crew_item, parent,
                false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CastCrew castCrew = castCrewList.get(position);
        if (castCrew != null) {
            holder.castNameTv.setText(castCrew.getName());
            Picasso.get().load(castCrew.getImageUrl()).into(holder.castIv);
            holder.castCrewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ItemMovieActivity.class);
                    intent.putExtra("id", castCrew.getId());
                    intent.putExtra("title", castCrew.getName());
                    intent.putExtra("type", INTENT_TYPE_STAR);
                    context.startActivity(intent);
                }
            });
        }
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return castCrewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView castIv;
        TextView castNameTv;
        LinearLayout castCrewLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            castIv          = itemView.findViewById(R.id.cast_iv);
            castNameTv      = itemView.findViewById(R.id.crew_name_tv);
            castCrewLayout  = itemView.findViewById(R.id.cast_crew_layout);
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
