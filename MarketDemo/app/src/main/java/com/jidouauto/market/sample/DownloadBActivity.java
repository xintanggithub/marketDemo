package com.jidouauto.market.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jidouauto.download.lib.callback.DownloadListener;
import com.jidouauto.download.lib.download.JDDownLoadManager;
import com.liulishuo.filedownloader.BaseDownloadTask;

public class DownloadBActivity extends AppCompatActivity {

    int taskID = -1;
    TextView tvDownloadStatus;
    String downloadUrl = "http://apk-download.jidouauto.com/asterix/appstore_resource/2018-11-12/baidumaplite_AndroidPhone_v10.8.22(2.1.1)_1021363h.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_b);

        String content = getIntent().getStringExtra("content");
        taskID = getIntent().getIntExtra("taskID", -1);
        tvDownloadStatus = findViewById(R.id.tv_content);
        tvDownloadStatus.setText(content);

        JDDownLoadManager.bindChangeListener(downloadListener);

        findViewById(R.id.tv_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JDDownLoadManager.getJDDownloader().pause(taskID);
            }
        });

        findViewById(R.id.tv_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JDDownLoadManager.getJDDownloader().start(downloadUrl);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JDDownLoadManager.unBindChangeListener(downloadListener);
    }

    DownloadListener downloadListener = new DownloadListener() {

        @Override
        public void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.pending(task, soFarBytes, totalBytes);
            taskID = task.getId();
            String content = "pending : soFarBytes/totalBytes   " + soFarBytes + "/" + totalBytes
                    + "   Speed:" + task.getSpeed() + "kb/s \n" +
                    tvDownloadStatus.getText().toString();
            tvDownloadStatus.setText(content);
        }

        @Override
        public void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.progress(task, soFarBytes, totalBytes);
            taskID = task.getId();
            String content = "progress : soFarBytes/totalBytes   " + soFarBytes + "/" + totalBytes
                    + "   Speed:" + task.getSpeed() + "kb/s \n" +
                    tvDownloadStatus.getText().toString();
            tvDownloadStatus.setText(content);
        }

        @Override
        public void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.paused(task, soFarBytes, totalBytes);
            String content = "paused : soFarBytes/totalBytes   " + soFarBytes + "/" + totalBytes
                    + "   Speed:" + task.getSpeed() + "kb/s \n" +
                    tvDownloadStatus.getText().toString();
            tvDownloadStatus.setText(content);
        }

        @Override
        public void error(BaseDownloadTask task, Throwable e) {
            super.error(task, e);
            String content = "error : " + e.getMessage() + "\n" +
                    tvDownloadStatus.getText().toString();
            tvDownloadStatus.setText(content);
        }

        @Override
        public void completed(BaseDownloadTask baseDownloadTask) {
            String content = "completed : download success !\n" +
                    tvDownloadStatus.getText().toString() + "\n path:" + baseDownloadTask.getPath();
            tvDownloadStatus.setText(content);
        }
    };

}
