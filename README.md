`SupportDownloadManager`由2.3.7版本源码修改，添加一些常用的功能，支持2.3以上版本。

>替换HttpClient为[okhttp](https://github.com/square/okhttp "okhttp")
>添加最大并行下载任务设置,超过数量的任务会被修改为`pending`状态，修改`RealSystemFacade`中`DOWNLOAD_MAX_COUNT`，默认为2
>支持断点续传，支持暂停任务、继续任务、重新开始任务
>添加下载速度、剩余下载时间


##使用方法##
        
>下载一项任务

        SupportDownloadManager mDownloadManager = new SupportDownloadManager(this)
        Uri srcUri = Uri.parse(url);
        SupportDownloadManager.Request request = new SupportDownloadManager.Request(srcUri);
        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS, "/");
        request.setDescription("download test");
        mDownloadManager.enqueue(request);
        
>暂停任务

        public int pauseDownload(long... ids)

>继续任务

        public int resumeDownload(long... ids)


>任务下载完成监听
       
任务下载完成会发送`SupportDownloadManager.ACTION_DOWNLOAD_COMPLETE`这个广播，
监听该广播就可以实现下载完成监听

·注意，任务取消也有此广播，如果只需要处理下载完成，则通过状态自行判断

``
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            //下载完成监听 ||取消时也有Complete事件。
            if (SupportDownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                //获取intent数据
                long downloadId = intent.getLongExtra(SupportDownloadManager.EXTRA_DOWNLOAD_ID, -1);
                int status = intent.getIntExtra(SupportDownloadManager.EXTRA_DOWNLOAD_STATUS, -1);
            }
``

>点击通知栏监听

和任务下载完成一样，点击通知栏也会发送广播`SupportDownloadManager.ACTION_NOTIFICATION_CLICKED`


>获取已下载数、下载速度等

``
 public int[] getBytesAndStatus(long downloadId) 
``
        