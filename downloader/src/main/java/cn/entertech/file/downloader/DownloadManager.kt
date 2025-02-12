package cn.entertech.file.downloader

import java.util.concurrent.ConcurrentHashMap

// 下载管理器（单例）
object DownloadManager {
    private val tasks by lazy { ConcurrentHashMap<String, IDownloadTask>() }

    fun download(
        url: String,
        downloaderFactory: IDownloaderFactory,
        listener: IDownloadListener,
        config: IDownloadConfig
    ): String {
        downloaderFactory.createDownloader(url, listener, config).apply {
            val id = getDownloadId()
            tasks[id] = this
            start()
            return id
        }
    }

    fun cancel(downloadId: String) {
        tasks[downloadId]?.cancel()
        tasks.remove(downloadId)
    }


}