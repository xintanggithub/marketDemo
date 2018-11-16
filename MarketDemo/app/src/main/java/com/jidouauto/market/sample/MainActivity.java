package com.jidouauto.market.sample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jidouauto.download.lib.callback.DownloadListener;
import com.jidouauto.download.lib.download.JDDownLoadManager;
import com.jidouauto.market.module.lib.api.bean.Apps;
import com.jidouauto.market.module.lib.api.bean.MarketBaseResponse;
import com.jidouauto.market.module.lib.api.bean.MarketClassifyResponse;
import com.jidouauto.market.module.lib.api.bean.MarketCommentRequest;
import com.jidouauto.market.module.lib.api.bean.MarketCommentsResponse;
import com.jidouauto.market.module.lib.api.bean.MarketDetailResponse;
import com.jidouauto.market.module.lib.api.bean.MarketDiffUrlResponse;
import com.jidouauto.market.module.lib.api.bean.MarketUpAppStatusRequest;
import com.jidouauto.market.module.lib.api.bean.MarketUpdateDownloadSizeRequest;
import com.jidouauto.market.module.lib.api.bean.PackageListResponse;
import com.jidouauto.market.module.lib.module.classify.MarketClassifyContract;
import com.jidouauto.market.module.lib.module.classify.MarketClassifyImpl;
import com.jidouauto.market.module.lib.module.comment.MarketCommentContract;
import com.jidouauto.market.module.lib.module.comment.MarketCommentImpl;
import com.jidouauto.market.module.lib.module.common.MarketCommonContract;
import com.jidouauto.market.module.lib.module.common.MarketCommonImpl;
import com.jidouauto.market.module.lib.module.detail.MarketDetailContract;
import com.jidouauto.market.module.lib.module.detail.MarketDetailImpl;
import com.jidouauto.market.module.lib.module.list.MarketListContract;
import com.jidouauto.market.module.lib.module.list.MarketListImpl;
import com.jidouauto.market.module.lib.module.util.MarketInstallUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MarketListContract.MarketListView,
        MarketDetailContract.MarketDetailView, MarketCommonContract.MarketCommonView,
        MarketCommentContract.MarketCommentView, MarketClassifyContract.MarketClassifyView {

    /**
     * 列表模块
     */
    private MarketListContract.MarketListPresenter mListPresenter;
    /**
     * 详情模块
     */
    private MarketDetailContract.MarketDetailPresenter mDetailPresenter;
    /**
     * 公共模块
     */
    private MarketCommonContract.MarketCommonPresenter mCommonPresenter;
    /**
     * 评论模块
     */
    private MarketCommentContract.MarketCommentPresenter mCommentPresenter;
    /**
     * 分类模块
     */
    private MarketClassifyContract.MarketClassifyPresenter mClassifyPresenter;

    private TextView tvContent;
    private TextView tvDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = findViewById(R.id.tv_content);
        tvDownload = findViewById(R.id.tv_download);
        mListPresenter = new MarketListImpl(this);
        mDetailPresenter = new MarketDetailImpl(this);
        mCommonPresenter = new MarketCommonImpl(this);
        mCommentPresenter = new MarketCommentImpl(this);
        mClassifyPresenter = new MarketClassifyImpl(this);
        JDDownLoadManager.bindChangeListener(downloadListener);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListPresenter.getMarketList(1, 300, null, "");
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListPresenter.getRecommend();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailPresenter.getMarketDetail(22, "chejiidtest");
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommonPresenter.getMarketDiffUrl(22, "testMd5", 1);
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketUpdateDownloadSizeRequest body = new MarketUpdateDownloadSizeRequest();
                body.setVersionId(22);
                mCommonPresenter.updateDownloadCount(body);
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketUpAppStatusRequest body = new MarketUpAppStatusRequest();
                body.setCid("testCarId");

                //模拟应用数据
                Apps apps1 = new Apps();
                List<Apps.AppItem> appItems = new ArrayList<>();

                Apps.AppItem item = new Apps.AppItem();
                item.setPackageName("com.tencent.qqmusiccar");
                item.setVersionCode(11);

                appItems.add(item);

                apps1.setAppItems(appItems);

                body.setApps(apps1.getApps());

                mCommonPresenter.marketSynStatus(body);
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketCommentRequest body = new MarketCommentRequest();
                body.setRate(5);
                body.setVersionId(22);
                //test token
                body.setToken("eyJraWQiOiI5NTdiZTE2M2ViNzE0MTY5OTAxYTRlNjhhZDk1MWRmMyIsInR5cCI6" +
                        "IkpXVCIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJ0ZXN0dXNlciIsImlzcyI6IkpETyIsInZ" +
                        "pbiI6IjMyNDIzNDIzIiwiZXhwIjoxNTQwNTI2MTkzLCJpYXQiOjE1NDA0Mzk3OTN9.VkBRk" +
                        "jEYfiqMUYm4FhzyOJY_X6RJ59Oe1VEqcOAsZh39z42vhOrop9KqMX0j3sSvOhs0gM1Gz0jk" +
                        "ReyujYZC8yB3M6BDAkP0lFzOFD6fXaQD5vX0ryHn1D5sdBr0am56ErZ9XuzOijOvC-TpAK8" +
                        "AUF1urBKwBPSyZ4u-84PkggmR-2DE2nUGc5oPdSz4ucwIqMT3B5n3YDnFIVWYZDaN-W_e1F" +
                        "OrEVVIq0UCZYm6cXyMJ31WRZb4LSmpCh8jvWjnpdLsDSVJNeYeEBkwQ83G532Uet22i93iV" +
                        "h3OCyxAoty0oivGRmMSRSZrUwPESoPQFU9PPfuCCJeNNiFH8GJMkg");
                mCommentPresenter.comment(body);
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试数据  packageId 对应百度地图
                mCommentPresenter.getMarketComments(1, 20, 12);
            }
        });
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClassifyPresenter.getMarketClassifyList();
            }
        });

        findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JDDownLoadManager.getJDDownloader().start("http://apk-download.jidouauto.com/asterix/appstore_resource/qqmusiccar_1.6.0.9_android_r2198_release.apk");
            }
        });
    }

    @Override
    public void before() {
        tvContent.setText("获取数据...");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void error(Throwable throwable) {
        tvContent.setText("获取失败：" + JSON.toJSONString(throwable));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getMarketClassifySuccess(MarketClassifyResponse response) {
        tvContent.setText("应用分类获取成功:" + JSON.toJSONString(response));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void commentSuccess(MarketBaseResponse response) {
        tvContent.setText("评论提交成功:" + JSON.toJSONString(response));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getMarketComments(MarketCommentsResponse response) {
        tvContent.setText("获取评论成功:" + JSON.toJSONString(response));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getMarketListSuccess(PackageListResponse response) {
        tvContent.setText("应用列表获取成功:" + JSON.toJSONString(response));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getRecommendSuccess(PackageListResponse response) {
        tvContent.setText("应用推荐列表获取成功:" + JSON.toJSONString(response));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void marketDiffUrlSuccess(MarketDiffUrlResponse response) {
        tvContent.setText("应用下载链接获取成功:" + JSON.toJSONString(response));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateDownloadCountSuccess(MarketBaseResponse response) {
        tvContent.setText("更新应用下载次数成功:" + JSON.toJSONString(response));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void marketSynStatusSuccess(MarketBaseResponse response) {
        tvContent.setText("上传应用信息成功:" + JSON.toJSONString(response));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getMarketDetailSuccess(MarketDetailResponse response) {
        tvContent.setText("应用详情获取成功:" + JSON.toJSONString(response));
    }

    DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.progress(task, soFarBytes, totalBytes);
            String content = "progress: " + soFarBytes + "/" + totalBytes + "     "
                    + task.getSpeed() + "kb/s";
            tvDownload.setText(content);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void completed(BaseDownloadTask baseDownloadTask) {
            tvDownload.setText("completed ! installing \n path:" + baseDownloadTask.getPath());
            //安装
            MarketInstallUtil.getInit().install(baseDownloadTask.getPath());
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.download:
                startActivity(new Intent(this, DownloadAActivity.class));
                break;
            default:
                break;
        }
        return true;
    }

}

