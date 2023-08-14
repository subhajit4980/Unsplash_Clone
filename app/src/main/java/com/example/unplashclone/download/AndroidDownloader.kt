package com.example.unplashclone.download

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.media3.common.util.UnstableApi
import com.example.unplashclone.BuildConfig
@RequiresApi(Build.VERSION_CODES.M)
class AndroidDownloader(private val context: Context) : Download {
    @SuppressLint("UnsafeOptInUsageError")
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun downloadFile(url: String, filename: String, path:String): Long {
        val request = DownloadManager.Request(url.toUri()).setMimeType("image/jpeg")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(filename).addRequestHeader("Authorization", BuildConfig.API_KEY)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,path+filename)
        return downloadManager.enqueue(request)
    }

}