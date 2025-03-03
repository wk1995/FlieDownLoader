package cn.entertech.downloader.okhttp

import cn.entertech.file.downloader.IDownloadConfig
import cn.entertech.file.downloader.IDownloadTask
import cn.entertech.file.downloader.IDownloaderFactory
import okhttp3.OkHttpClient

class DefaultDownloaderFactory : IDownloaderFactory {

    override fun createDownloader(
        url: String,
        config: IDownloadConfig,
    ): IDownloadTask {
        return OkHttpDownloader(url,  config, OkHttpClient.Builder().build())
    }
}