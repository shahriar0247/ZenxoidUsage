package com.shahriar.zenxoid.zenxoidusage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.usage.EventStats;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class Activity1 extends AppCompatActivity {

    // getting service
    private UsageStatsManager mUsageStatsManager;
    private LayoutInflater mInflater;
    private PackageManager mPm;

    public String lol = "day";

    //setting listview
    private MainActivity.UsageStatsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);

        gettingservice();


        List<UsageStats> stats = getting_all_apps_usage("day");
        for (int i = 0; i < stats.size(); i++){

            try {
                ApplicationInfo appinfo = mPm.getApplicationInfo(stats.get(i).getPackageName(), 0);
                create_item(appinfo.loadLabel(mPm).toString(), appinfo.loadIcon(mPm), converttime(Long.toString(stats.get(i).getTotalTimeInForeground())));

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }

    }




    void getpermission(){
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);}

    void gettingservice(){
        mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPm = getPackageManager();

    }




    List<UsageStats> getting_all_apps_usage(String time){

        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        int type;

        if (time == "day"){

            type = UsageStatsManager.INTERVAL_DAILY;
            cal.add(Calendar.DAY_OF_YEAR, -1);
            cal2.add(Calendar.DAY_OF_YEAR, 0);

        }
        else if (time == "week"){
            type = UsageStatsManager.INTERVAL_WEEKLY;
            cal.add(Calendar.WEEK_OF_YEAR, -1);
            cal2.add(Calendar.WEEK_OF_YEAR, 0);
        }
        else if (time == "month"){
            type = UsageStatsManager.INTERVAL_MONTHLY;
            cal.add(Calendar.MONTH, -1);
            cal2.add(Calendar.MONTH, 0);
        }

        else if (time == "year"){
            type = UsageStatsManager.INTERVAL_YEARLY;
            cal.add(Calendar.YEAR, -1);
            cal2.add(Calendar.YEAR, 0);
        }
        else{
            type = UsageStatsManager.INTERVAL_BEST;
        }



        List<UsageStats> stats =
                mUsageStatsManager.queryUsageStats(type,
                        cal.getTimeInMillis() , System.currentTimeMillis());
        if (stats == null) {
            return stats;
        }
        return stats;
    }


    void create_item(String appname, Drawable appicon, String time_used){

        if (time_used == "none"){
            return;
        }
        else{
            LinearLayout llayout1 = findViewById(R.id.llayout1);

            ConstraintLayout itemlayout = new ConstraintLayout(this);
            itemlayout.setLayoutParams(new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
            itemlayout.setId(View.generateViewId());
            llayout1.addView(itemlayout);




            TextView name_field = new TextView(this);
            name_field.setText(appname);
            name_field.setLayoutParams(new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
            name_field.setId(View.generateViewId());
            //designing name_filed
            name_field.setTextColor(Color.parseColor("#222222"));

            ImageView icon_filed = new ImageView(this);
            icon_filed.setLayoutParams(new ConstraintLayout.LayoutParams(150, 150));
            icon_filed.setId(View.generateViewId());
            icon_filed.setImageDrawable(appicon);


            TextView package_name_field = new TextView(this);
            package_name_field.setText(time_used);
            package_name_field.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
            package_name_field.setId(View.generateViewId());
            package_name_field.setTextColor(Color.parseColor("#222222"));
            package_name_field.setTextSize(12);

            LinearLayout names_layout = new LinearLayout(this);
            names_layout.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
            names_layout.setId(View.generateViewId());
            names_layout.setOrientation(LinearLayout.VERTICAL);

            itemlayout.addView(names_layout);
            itemlayout.addView(icon_filed);
            names_layout.addView(name_field);
            names_layout.addView(package_name_field);
            /*        itemlayout.addView(package_name_field);*/

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(itemlayout);
            constraintSet.connect(names_layout.getId(),ConstraintSet.LEFT,icon_filed.getId(),ConstraintSet.RIGHT,16);
            constraintSet.connect(icon_filed.getId(),ConstraintSet.RIGHT,names_layout.getId(),ConstraintSet.LEFT,0);
            constraintSet.connect(icon_filed.getId(),ConstraintSet.LEFT,itemlayout.getId(),ConstraintSet.LEFT,32);
            constraintSet.connect(names_layout.getId(),ConstraintSet.RIGHT,itemlayout.getId(),ConstraintSet.RIGHT,32);
            constraintSet.connect(icon_filed.getId(),ConstraintSet.TOP,itemlayout.getId(),ConstraintSet.TOP,16);
            constraintSet.connect(icon_filed.getId(),ConstraintSet.BOTTOM,itemlayout.getId(),ConstraintSet.BOTTOM,0);
            constraintSet.connect(names_layout.getId(),ConstraintSet.TOP,itemlayout.getId(),ConstraintSet.TOP,0);
            constraintSet.connect(names_layout.getId(),ConstraintSet.BOTTOM,itemlayout.getId(),ConstraintSet.BOTTOM,0);
            constraintSet.applyTo(itemlayout);
        }}

    String converttime(String time){
        int second = Integer.parseInt(time) / 1000;
        int minute = 0;
        int hour = 0;
        if (Integer.parseInt(time) == 0) {
            String time2 = "none";
            return time2;
        }

        while (second > 60){
            minute = minute + 1;
            second  = second - 60;
        }

        while (minute > 60){
            hour = hour + 1;
            minute  = minute - 60;

        }
        String time2 = hour + " h " + minute + " m " + second + " s ";
        return  time2;
    }

    void setstats(List<UsageStats> stats){
        for (int i=0; i<stats.size(); i++)
        {
            //looping through all apps
            try {

                UsageStats appstat = stats.get(i);
                ApplicationInfo appInfo = mPm.getApplicationInfo(appstat.getPackageName(), 0);
                create_item(appInfo.loadLabel(mPm).toString(), appInfo.loadIcon(mPm), converttime(Long.toString(appstat.getTotalTimeInForeground())));


            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
