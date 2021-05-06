package com.oxoo.spagreen.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubscriptionHistory {

    @SerializedName("active_subscription")
    @Expose
    private List<ActiveSubscription> activeSubscription = null;
    @SerializedName("inactive_subscription")
    @Expose
    private List<InactiveSubscription> inactiveSubscription = null;

    public List<ActiveSubscription> getActiveSubscription() {
        return activeSubscription;
    }

    public void setActiveSubscription(List<ActiveSubscription> activeSubscription) {
        this.activeSubscription = activeSubscription;
    }

    public List<InactiveSubscription> getInactiveSubscription() {
        return inactiveSubscription;
    }

    public void setInactiveSubscription(List<InactiveSubscription> inactiveSubscription) {
        this.inactiveSubscription = inactiveSubscription;
    }

}
