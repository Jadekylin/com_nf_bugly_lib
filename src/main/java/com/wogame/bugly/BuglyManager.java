package com.wogame.bugly;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.CrashReport.*;
import com.wogame.util.AppInfoUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class BuglyManager {
    private static BuglyManager instance;

    public static BuglyManager getInstance(){
        if(instance == null){
            instance = new BuglyManager();
        }
        return instance;
    }

    public void initApplication(Context context,String buglyId){
        UserStrategy strategy = new UserStrategy(context.getApplicationContext());
        CrashReport.initCrashReport(context.getApplicationContext(), buglyId, false, strategy);
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        strategy.setUploadProcess(processName == null || processName.equals(AppInfoUtil.mPackageName));
        strategy.setAppChannel(AppInfoUtil.mChannelId); //设置渠道
        strategy.setAppVersion(AppInfoUtil.getVersionName()); //App的版本
        strategy.setAppPackageName(AppInfoUtil.mPackageName); //App的包名
        //Bugly会在启动10s后联网同步数据。若您有特别需求，可以修改这个时间
        strategy.setAppReportDelay(20000); //改为20s

        //CrashReport.setIsDevelopmentDevice(context,BuildConfig.DEBUG);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
