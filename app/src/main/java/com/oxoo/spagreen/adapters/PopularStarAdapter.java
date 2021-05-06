package com.oxoo.spagreen.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.oxoo.spagreen.ItemMovieActivity;
import com.oxoo.spagreen.R;
import com.oxoo.spagreen.models.home_content.PopularStars;
import com.oxoo.spagreen.utils.ItemAnimation;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.oxoo.spagreen.ItemMovieActivity.INTENT_TYPE_STAR;

public class PopularStarAdapter extends RecyclerView.Adapter<PopularStarAdapter.StarViewHolder> {
    private Activity context;
    private List<PopularStars> starsList;
    private int lastPosition = -1;
    private boolean on_attach = true;
    private int animation_type = 2;

    public PopularStarAdapter(Activity context, List<PopularStars> starsList) {
        this.context = context;
        this.starsList = starsList;
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_stars_item, parent, false);
        return new StarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        if (starsList != null) {
            PopularStars star = starsList.get(position);
            holder.starName.setText(star.getStarName());
            Picasso.get().load(star.getImageUrl()).into(holder.imageView);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ItemMovieActivity.class);
                    intent.putExtra("id",star.getStartId());
                    intent.putExtra("title",star.getStarName());
                    intent.putExtra("type", INTENT_TYPE_STAR);
                    context.startActivity(intent);
                    Log.e("TAG", "onClick: " + star.getStarName() );
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return starsList.size();
    }


    class StarViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView imageView;
        private TextView starName;
        private LinearLayout cardView;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.star_image);
            starName  = itemView.findViewById(R.id.star_name_tv);
            cardView  = itemView.findViewById(R.id.star_card_view);
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
