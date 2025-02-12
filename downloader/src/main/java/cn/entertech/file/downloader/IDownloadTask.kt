package cn.entertech.file.downloader

// 下载任务抽象
interface IDownloadTask {
    fun start()
    fun cancel()
    fun getDownloadId(): String
}