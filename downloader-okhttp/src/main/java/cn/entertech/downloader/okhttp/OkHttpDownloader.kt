package cn.entertech.downloader.okhttp

import cn.entertech.file.downloader.BaseDownloader
import cn.entertech.file.downloader.DownloaderException
import cn.entertech.file.downloader.IDownloadConfig
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.FileOutputStream
import java.io.IOException

// 具体实现示例（OkHttp）
class OkHttpDownloader(
    url: String, config: IDownloadConfig, private val client: OkHttpClient
) : BaseDownloader(url, config) {
    private var requestCall: Call? = null
    override fun executeDownload() {
        val downloadId = getDownloadId()
        val outputFile = config.getOutputFile()
        val request = Request.Builder().url(url).build()
        requestCall = client.newCall(request)
        requestCall?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                mDownloadListener?.onFailure(downloadId, DownloaderException.DownloadException())
            }

            override fun onResponse(call: Call, response: Response) {
                response.body()?.use { body ->
                    val input = body.byteStream()
                    val output = FileOutputStream(outputFile)
                    val total = body.contentLength()
                    var current: Long = 0
                    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                    var bytesRead: Int
                    try {
                        while (input.read(buffer).also { bytesRead = it } != -1) {
                            checkCancelState()
                            output.write(buffer, 0, bytesRead)
                            current += bytesRead
                            handleProgress(current, total)
                        }
                        mDownloadListener?.onSuccess(downloadId, outputFile)
                    } catch (e: Exception) {
                        mDownloadListener?.onFailure(
                            downloadId,
                            DownloaderException.Canceled("onResponse ${e.message ?: "null"}")
                        )
                    }
                }
            }
        })
    }

    override fun getDownloadId(): String {
        return ""
    }

    override fun cancel() {
        super.cancel()
    }
}