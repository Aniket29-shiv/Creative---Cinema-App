package com.oxoo.spagreen;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationClickHandler implements OneSignal.NotificationOpenedHandler {
    Context context2;

    public NotificationClickHandler(Context context) {
        context2 = context;
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        OSNotificationAction.ActionType actionType = result.action.type;

        JSONObject data = result.notification.payload.additionalData;
        String customKey;
        String id = null;
        String type = null;
        String openType = null;
        String webUrl = null;

        try {
            id= data.getString("id");
            type = data.getString("vtype");
            openType = data.getString("open");
            webUrl = data.getString("url");
            //Toast.makeText(context2, "id "+ openType, Toast.LENGTH_SHORT).show();
            Log.d("noti:", data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }



        if (openType.equalsIgnoreCase("web")) {

            Intent intent = new Intent(context2, TermsActivity.class);
            intent.putExtra("url", webUrl);
            intent.putExtra("title", result.notification.payload.title);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            context2.startActivity(intent);

        } else {
            Intent intent = new Intent(context2, DetailsActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("vType", type);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            context2.startActivity(intent);
        }


        /*if (data != null) {
            customKey = data.optString("customkey", null);
            if (customKey != null)
                Log.e("OneSignalExample", "customkey set with value: " + customKey);
        }*/

        if (actionType == OSNotificationAction.ActionType.ActionTaken) {
            Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);
        }
    }
}
