package com.oxoo.spagreen.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oxoo.spagreen.R;
import com.oxoo.spagreen.network.model.InactiveSubscription;

import java.util.List;

public class InactiveSubscriptionAdapter extends RecyclerView.Adapter<InactiveSubscriptionAdapter.ViewHolder> {

    private List<InactiveSubscription> inactiveSubscriptions;
    private Context context;

    public InactiveSubscriptionAdapter(List<InactiveSubscription> inactiveSubscriptions, Context context) {
        this.inactiveSubscriptions = inactiveSubscriptions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.subscription_layout, parent,
                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        InactiveSubscription inactiveSubscription = inactiveSubscriptions.get(position);

        if (inactiveSubscription != null) {
            holder.serialNoTv.setText(position+1+"");
            holder.planTv.setText(inactiveSubscription.getPlanTitle());
            holder.purchaseDateTv.setText(inactiveSubscription.getPaymentTimestamp());
            holder.fromTv.setText(inactiveSubscription.getStartDate());
            holder.toTv.setText(inactiveSubscription.getExpireDate());
        }


    }

    @Override
    public int getItemCount() {
        return inactiveSubscriptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView serialNoTv, planTv, purchaseDateTv, fromTv, toTv, actionTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serialNoTv = itemView.findViewById(R.id.serial_no_tv);
            planTv = itemView.findViewById(R.id.plan_tv);
            purchaseDateTv = itemView.findViewById(R.id.purchase_date_tv);
            fromTv = itemView.findViewById(R.id.from_tv);
            toTv = itemView.findViewById(R.id.to_tv);

        }
    }
}

