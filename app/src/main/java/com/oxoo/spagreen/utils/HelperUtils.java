package com.oxoo.spagreen.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.oxoo.spagreen.R;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class HelperUtils {
    private Activity activity;

    public HelperUtils(Activity activity) {
        this.activity = activity;
    }

    public boolean isVpnConnectionAvailable(){
        String iface = "";
        try {
            for (NetworkInterface networkInst : Collections.list(NetworkInterface.getNetworkInterfaces())){
                if (networkInst.isUp())
                    iface = networkInst.getName();
                if ( iface.contains("tun") || iface.contains("ppp") || iface.contains("pptp")) {
                    return true;
                }
            }


        }catch (SocketException e){
            e.printStackTrace();
        }
        return false;
    }

    public ApplicationInfo getRestrictApp() {
        ApplicationInfo restrictPackageInfo = null;
        final PackageManager pm = activity.getPackageManager();
        //get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals("com.guoshi.httpcanary") ||
                    packageInfo.packageName.equals("app.greyshirts.sslcapture") ||
                    packageInfo.packageName.equals("com.guoshi.httpcanary.premium") ||
                    packageInfo.packageName.equals("com.minhui.networkcapture.pro") ||
                    packageInfo.packageName.equals("com.minhui.networkcapture") ||
                    packageInfo.packageName.equals("com.egorovandreyrm.pcapremote") ||
                    packageInfo.packageName.equals("com.packagesniffer.frtparlak") ||
                    packageInfo.packageName.equals("jp.co.taosoftware.android.packetcapture") ||
                    packageInfo.packageName.equals("com.emanuelef.remote_capture") ||
                    packageInfo.packageName.equals("com.minhui.wifianalyzer") ||
                    packageInfo.packageName.equals("com.evbadroid.proxymon") ||
                    packageInfo.packageName.equals("com.evbadroid.wicapdemo") ||
                    packageInfo.packageName.equals("com.evbadroid.wicap") ||
                    packageInfo.packageName.equals("com.luckypatchers.luckypatcherinstaller") ||
                    packageInfo.packageName.equals("ru.UbLBBRLf.jSziIaUjL")) {
                //restrictPackageName = packageInfo.packageName;
                //restrictPackageName = packageInfo.loadLabel(activity.getPackageManager()).toString();
                restrictPackageInfo = packageInfo;
            }
        }

        return restrictPackageInfo;
    }

    public boolean isForeground( String myPackage){
        ActivityManager manager = (ActivityManager) activity.getSystemService(ACTIVITY_SERVICE);
        List< ActivityManager.RunningTaskInfo > runningTaskInfo = manager.getRunningTasks(1);

        ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
        Log.e("123456", "Background Apps: " + componentInfo.getPackageName());
        return componentInfo != null && componentInfo.getPackageName().equals(myPackage);
    }

    public boolean isAppRunning(Context context, String packageName){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        if (processInfos != null){
            for (ActivityManager.RunningAppProcessInfo processInfo : processInfos){
                if (processInfo.processName.equals(packageName)){
                    return true;
                }
            }
        }
        return false;
    }

    public void showWarningDialog(Activity context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                        context.finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

}
