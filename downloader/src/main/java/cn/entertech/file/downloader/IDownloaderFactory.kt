package cn.entertech.file.downloader

interface IDownloaderFactory {

    fun createDownloader(
        url: String,
        config: IDownloadConfig
    ): IDownloadTask
}