
package com.gzsll.downloads;

import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;


public interface SystemFacade {
    /**
     * @see System#currentTimeMillis()
     */
    public long currentTimeMillis();

    /**
     * @return Network type (as in ConnectivityManager.TYPE_*) of currently active network, or null
     * if there's no active connection.
     */
    public Integer getActiveNetworkType();

    /**
     * @see android.telephony.TelephonyManager#isNetworkRoaming
     */
    public boolean isNetworkRoaming();

    /**
     * @return maximum size, in bytes, of downloads that may go over a mobile connection; or null if
     * there's no limit
     */
    public Long getMaxBytesOverMobile();

    /**
     * @return recommended maximum size, in bytes, of downloads that may go over a mobile
     * connection; or null if there's no recommended limit.  The user will have the option to bypass
     * this limit.
     */
    public Long getRecommendedMaxBytesOverMobile();

    /**
     * Send a broadcast intent.
     */
    public void sendBroadcast(Intent intent);

    /**
     * Returns true if the specified UID owns the specified package name.
     */
    public boolean userOwnsPackage(int uid, String pckg) throws NameNotFoundException;

    /**
     * Post a system notification to the NotificationManager.
     */
    public void postNotification(long id, Notification notification);

    /**
     * Cancel a system notification.
     */
    public void cancelNotification(long id);

    /**
     * Cancel all system notifications.
     */
    public void cancelAllNotifications();

    /**
     * Start a thread.
     */
    public void startThread(Thread thread);


    /**
     * 最大下载数量，超过该数量的任务会自动修改为pending状态
     *
     * @return 最大同时下载数量
     */
    int getMaxDownloadCount();
}
