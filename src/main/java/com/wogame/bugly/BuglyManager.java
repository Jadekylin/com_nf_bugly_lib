package com.wogameo.bugly;

import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;
import com.wogame.common.Common;
import com.wogame.util.AppInfoUtil;

public class BuglyManager {
    private static BuglyManager instance;

    public static BuglyManager getInstance(){
        if(instance == null){
            instance = new BuglyManager();
        }
        return instance;
    }

    public void initWithApplication(Context _context,String buglyId){
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(_context.getApplicationContext());
        strategy.setAppChannel(AppInfoUtil.m_channelId);  //设置渠道
        CrashReport.initCrashReport(_context.getApplicationContext(), buglyId, false, strategy);
    }
}
