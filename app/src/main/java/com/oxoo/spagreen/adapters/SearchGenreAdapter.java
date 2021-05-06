package com.oxoo.spagreen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.oxoo.spagreen.R;
import com.oxoo.spagreen.models.CommonModels;

import java.util.ArrayList;
import java.util.List;

public class SearchGenreAdapter extends RecyclerView.Adapter<SearchGenreAdapter.ViewHolder> {

    private List<CommonModels> genreModels;
    private Context context;
    private OnItemClickListener itemClickListener;
    String type;
    private List<ViewHolder> viewHolderArr = new ArrayList<>();
    int checkCount;

    public SearchGenreAdapter(List<CommonModels> genreModels, Context context, String type) {
        this.genreModels = genreModels;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_genre_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        viewHolderArr.add(holder);

        CommonModels cm = genreModels.get(position);

        if (cm != null) {
            holder.genreTv.setText(cm.getTitle());
        }

    }

    @Override
    public int getItemCount() {
        checkCount = genreModels.size();
        return genreModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        MaterialCheckBox checkBox;
        private TextView genreTv;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            genreTv = itemView.findViewById(R.id.genre_tv);
            linearLayout = itemView.findViewById(R.id.s_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CommonModels cm = genreModels.get(getAdapterPosition());
                    if (itemClickListener != null) {

                        if (getAdapterPosition() == 0) {
                            linearLayout.setSelected(!linearLayout.isSelected());
                            //linearLayout.setSelected(!linearLayout.isSelected());
                            if (linearLayout.isSelected()) {

                                for (int i = 0; i < viewHolderArr.size(); i++) {
                                    ViewHolder holder = viewHolderArr.get(i);
                                    holder.linearLayout.setSelected(true);
                                    checkCount = i;
                                }

                            } else {
                                for (int i = 0; i < viewHolderArr.size(); i++) {
                                    ViewHolder holder = viewHolderArr.get(i);
                                    holder.linearLayout.setSelected(false);
                                    checkCount = 0;
                                }
                            }

                        } else {

                            linearLayout.setSelected(!linearLayout.isSelected());

                            if (checkCount == viewHolderArr.size() || checkCount == viewHolderArr.size() -1) {
                                viewHolderArr.get(0).linearLayout.setSelected(true);
                            } else {
                                viewHolderArr.get(0).linearLayout.setSelected(false);
                            }

                        }

                        itemClickListener.onItemClick(cm.getTitle(), type);

                    }

                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(String genreName, String type);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
