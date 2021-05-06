package com.oxoo.spagreen.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oxoo.spagreen.R;
import com.oxoo.spagreen.network.model.ActiveSubscription;
import com.oxoo.spagreen.utils.Constants;

import java.util.List;

public class ActiveSubscriptionAdapter extends RecyclerView.Adapter<ActiveSubscriptionAdapter.ViewHolder> {

    private List<ActiveSubscription> activeSubscriptions;
    private Context context;
    private OnItemClickLiestener onItemClickLiestener;

    public ActiveSubscriptionAdapter(List<ActiveSubscription> activeSubscriptions, Context context) {
        this.activeSubscriptions = activeSubscriptions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.active_subscription_layout, parent,
                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ActiveSubscription activeSubscription = activeSubscriptions.get(position);

        if (activeSubscription != null) {
            holder.serialNoTv.setText(position+1+"");
            holder.planTv.setText(activeSubscription.getPlanTitle());
            holder.purchaseDateTv.setText(activeSubscription.getPaymentTimestamp());
            holder.fromTv.setText(activeSubscription.getStartDate());
            holder.toTv.setText(activeSubscription.getExpireDate());
        }


    }

    @Override
    public int getItemCount() {
        return activeSubscriptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView serialNoTv, planTv, purchaseDateTv, fromTv, toTv;
        Button actionBt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serialNoTv = itemView.findViewById(R.id.serial_no_tv);
            planTv = itemView.findViewById(R.id.plan_tv);
            purchaseDateTv = itemView.findViewById(R.id.purchase_date_tv);
            fromTv = itemView.findViewById(R.id.from_tv);
            toTv = itemView.findViewById(R.id.to_tv);
            actionBt = itemView.findViewById(R.id.action_bt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickLiestener != null) {
                        onItemClickLiestener.onItemClick();
                    }
                }
            });

            actionBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickLiestener != null) {
                        onItemClickLiestener.onCancelBtClick(activeSubscriptions.get(getAdapterPosition())
                                .getSubscriptionId(), getAdapterPosition());
                    }
                }
            });

        }
    }

    public void setOnItemClickLiestener(OnItemClickLiestener onItemClickLiestener) {
        this.onItemClickLiestener = onItemClickLiestener;
    }

    public interface OnItemClickLiestener {
        void onItemClick();
        void onCancelBtClick(String subscriptionId, int position);
    }



}


