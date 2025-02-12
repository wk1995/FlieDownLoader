package cn.entertech.file.downloader

// 异常体系
sealed class DownloaderException(val errorCode: Int = -1, message: String = "") :
    Exception(message) {
    class NetworkError(errorCode: Int = -1, errorMsg: String = "") :
        DownloaderException(errorCode, "Network errorCode:$errorCode error: $errorMsg") {}

    class StorageError(errorCode: Int = -1, errorMsg: String = "") :
        DownloaderException(errorCode, "Storage error: $errorMsg")

    class DownloadException(errorCode: Int = -1, errorMsg: String = "") :
        DownloaderException(errorCode, "Network errorCode:$errorCode error: $errorMsg")

    class Canceled (message: String = ""): DownloaderException(message = "Download canceled $message")
}