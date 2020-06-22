package com.wogame.bugly;

import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;
import com.wogame.util.AppInfoUtil;


public class BuglyManager {
    private static BuglyManager instance;

    public static BuglyManager getInstance(){
        if(instance == null){
            instance = new BuglyManager();
        }
        return instance;
    }

    public void initApplication(Context context,String buglyId){
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context.getApplicationContext());
        CrashReport.initCrashReport(context.getApplicationContext(), buglyId, false, strategy);

        strategy.setAppChannel(AppInfoUtil.mChannelId); //设置渠道
        strategy.setAppVersion(AppInfoUtil.getVersionName()); //App的版本
        strategy.setAppPackageName(AppInfoUtil.mPackageName); //App的包名
        //Bugly会在启动10s后联网同步数据。若您有特别需求，可以修改这个时间
        //strategy.setAppReportDelay(20000); //改为20s
    }
}
