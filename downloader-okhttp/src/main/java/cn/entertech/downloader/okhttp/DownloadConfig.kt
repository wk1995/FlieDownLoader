package cn.entertech.downloader.okhttp

import cn.entertech.file.downloader.IDownloadConfig
import cn.entertech.file.downloader.IDownloaderFactory
import java.io.File

// 核心配置类（使用构建者模式）
class DownloadConfig private constructor(
    val connectTimeout: Long,
    val readTimeout: Long,
    val writeTimeout: Long,
    val retryCount: Int,
    val downloadDir: File,
    val allowMobileNetwork: Boolean,
) : IDownloadConfig {
    class Builder {
        private var connectTimeout = 15_000L
        private var readTimeout = 30_000L
        private var writeTimeout = 30_000L
        private var retryCount = 3
        private var downloadDir = File("default/path")
        private var allowMobileNetwork = false

        fun setConnectTimeout(timeout: Long) = apply { this.connectTimeout = timeout }
        fun setReadTimeout(timeout: Long) = apply { this.readTimeout = timeout }
        fun setRetryCount(count: Int) = apply { this.retryCount = count }
        fun setDownloadDir(dir: File) = apply { this.downloadDir = dir }
        fun allowMobileNetwork(allow: Boolean) = apply { this.allowMobileNetwork = allow }

        fun build(): DownloadConfig {
            return DownloadConfig(
                connectTimeout,
                readTimeout,
                writeTimeout,
                retryCount,
                downloadDir,
                allowMobileNetwork,
            )
        }
    }

    override fun getOutputFile(): File {
        return downloadDir
    }
}