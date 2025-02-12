package cn.entertech.file.downloader

import java.io.File


interface IDownloadListener {
    fun onStart(downloadId: String)
    fun onProgress(downloadId: String, progress: Int, current: Long, total: Long)
    fun onSuccess(downloadId: String, file: File)
    fun onFailure(downloadId: String, exception: DownloaderException)
}