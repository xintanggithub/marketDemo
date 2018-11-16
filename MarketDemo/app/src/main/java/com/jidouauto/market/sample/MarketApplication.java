package com.jidouauto.market.sample;

import android.app.Application;

import com.jidouauto.download.lib.download.JDDownLoadManager;
import com.jidouauto.market.module.lib.common.FileUtils;
import com.jidouauto.market.module.lib.common.MarketConfig;

/**
 * Created tangxin
 * Time 2018/11/16 10:34 AM
 */
public class MarketApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //配置marketLib组件
        new MarketConfig(this)
                .setJdChannel("audi")//渠道设置
                .setAuthorities("com.jidouauto.market.sample.fileprovider")
                .debug(true);//是否开启debug日志

        //配置JDDownload模块
        JDDownLoadManager.getInstance().init(this)
                .setDebugLog(true) //开启debug日志
                .setMaxThreadCount(3)//并发任务3个
                .setPath(FileUtils.getDataPath() + "/download")////设置下载路径
                .setRetryCount(3);//重试次数3次
    }
}
