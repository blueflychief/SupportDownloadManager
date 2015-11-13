package com.tongbu.supportdownloadmanager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.gzsll.downloads.DownloadService;
import com.gzsll.downloads.SupportDownloadManager;

public class MainActivity extends Activity implements
        OnClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = MainActivity.class.getName();

    private BroadcastReceiver mReceiver;

    EditText etUrl;
    Button btStart;
    SupportDownloadManager mDownloadManager;
    Button btShowList;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDownloadManager = new SupportDownloadManager(getContentResolver(),
                getPackageName());
        buildComponents();
        startDownloadService();

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                showDownloadList();
            }
        };

        registerReceiver(mReceiver, new IntentFilter(
                SupportDownloadManager.ACTION_NOTIFICATION_CLICKED));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void buildComponents() {
        etUrl = (EditText) findViewById(R.id.etUrl);
        btStart = (Button) findViewById(R.id.btStart);
        btShowList = (Button) findViewById(R.id.btShowList);

        btStart.setOnClickListener(this);
        btShowList.setOnClickListener(this);

        etUrl.setText("http://down.mumayi.com/41052/mbaidu");
    }

    private void startDownloadService() {
        Intent intent = new Intent();
        intent.setClass(this, DownloadService.class);
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btStart:
                startDownload();
                break;
            case R.id.btShowList:
                showDownloadList();
                break;
            default:
                break;
        }
    }

    private void showDownloadList() {
        Intent intent = new Intent();
        intent.setClass(this, TaskListActivity.class);
        startActivity(intent);
    }

    private void startDownload() {
        String url = etUrl.getText().toString();
        Uri srcUri = Uri.parse(url);
        SupportDownloadManager.Request request = new SupportDownloadManager.Request(srcUri);
        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS, "/");
        request.setDescription("download mbaidu");
        mDownloadManager.enqueue(request);
    }
}