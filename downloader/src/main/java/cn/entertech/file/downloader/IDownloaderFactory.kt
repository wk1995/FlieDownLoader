package cn.entertech.file.downloader

import java.io.File

interface IDownloaderFactory {
    fun createDownloader(
        url: String,
        listener: IDownloadListener,
        config: IDownloadConfig
    ): IDownloadTask
}