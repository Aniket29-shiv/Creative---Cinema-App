package com.oxoo.spagreen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.oxoo.spagreen.network.model.Package;

import com.oxoo.spagreen.R;

import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {

    private Context context;
    private List<Package> packageList;
    private int c;
    private OnItemClickListener itemClickListener;
    private String currency;

    public PackageAdapter(Context context, List<Package> packageList, String currency) {
        this.context = context;
        this.packageList = packageList;
        this.currency = currency;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_package_item_2, parent,
                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //holder.buyNowTv.setBackgroundColor(context.getResources().getColor(getColor()));
        Package pac = packageList.get(position);
        if (pac != null) {
            holder.packageTv.setText(currency + " " + pac.getPrice() +" - " + pac.getName());
        }

    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /*TextView buyNowTv, priceTv, packageNameTv, packageDesTv;
        RelativeLayout packageLayout;*/

        TextView packageTv;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            packageTv = itemView.findViewById(R.id.package_tv);
            linearLayout. setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(packageList.get(getAdapterPosition()));
                    }

                }
            });


        }
    }

    private int getColor(){

        int colorList[] = {R.color.red_400,R.color.blue_400,R.color.indigo_400,R.color.orange_400,R.color.light_green_400,R.color.blue_grey_400};
        //int colorList2[] = {R.drawable.gradient_1 ,R.drawable.gradient_2,R.drawable.gradient_3,R.drawable.gradient_4,R.drawable.gradient_5,R.drawable.gradient_6};

        if (c>=6){
            c=0;
        }

        int color = colorList[c];
        c++;

        return color;

    }

    public interface OnItemClickListener {
        void onItemClick(Package pac);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
