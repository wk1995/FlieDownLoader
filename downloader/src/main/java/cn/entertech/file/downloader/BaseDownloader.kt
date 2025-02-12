package cn.entertech.file.downloader

// 核心下载器（模板方法模式）
abstract class BaseDownloader(
    protected val url: String,
    protected val listener: IDownloadListener,
    protected val config: IDownloadConfig
) : IDownloadTask {

    private var isCanceled = false
    private var lastUpdateTime = 0L
    private val progressInterval = 100L // 进度更新间隔

    final override fun start() {
        if (!checkNetwork()) {
            listener.onFailure(
                getDownloadId(), DownloaderException.NetworkError()
            )
            return
        }

        listener.onStart(getDownloadId())

        try {
            executeDownload()
        } catch (e: Exception) {
            listener.onFailure(
                getDownloadId(), DownloaderException.DownloadException(errorMsg = e.message ?: "")
            )
        }
    }

    protected abstract fun executeDownload()

    protected fun handleProgress(current: Long, total: Long) {
        val now = System.currentTimeMillis()
        if (now - lastUpdateTime >= progressInterval || current == total) {
            val progress = (current * 100 / total).toInt()
            listener.onProgress(getDownloadId(), progress, current, total)
            lastUpdateTime = now
        }
    }

    protected fun checkCancelState() {
        if (isCanceled) throw DownloaderException.Canceled("checkCancelState")
    }

    private fun checkNetwork(): Boolean {
        // 实现网络状态检查
        return true
    }

    override fun cancel() {
        isCanceled = true
        // 具体实现需要关闭网络连接
    }

    // 其他通用方法...
}