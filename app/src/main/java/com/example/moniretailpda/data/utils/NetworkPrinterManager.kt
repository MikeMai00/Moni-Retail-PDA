package com.example.moniretailpda.data.utils

import com.example.moniretailpda.data.entity.ProductEntity
import com.example.moniretailpda.data.session.SessionManager
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket

class NetworkPrinterManager(product: ProductEntity) {
    private var socket: Socket? = null
    private var outputStream: OutputStream? = null
    private lateinit var sessionManager: SessionManager

    // 连接打印机 (IP地址通常是 192.168.x.x, 端口默认 9100)
    suspend fun connect(ip: String, port: Int = 9100): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            socket = Socket()
            // 设置 5 秒超时，防止网络不通时卡死
            socket?.connect(InetSocketAddress(ip, port), 5000)
            outputStream = socket?.outputStream
            true
        } catch (e: IOException) {
            e.printStackTrace()
            close()
            false
        }
    }

    // 发送数据 (可以直接复用你之前的 PrinterCommands)
    suspend fun sendData(data: ByteArray): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            outputStream?.write(data)
            outputStream?.flush()
            true
        } catch (e: IOException) {
            false
        }
    }

    private fun doNetworkPrint(){

    }

    object PrinterCommands {
        val RESET = byteArrayOf(0x1B, 0x40)            // 初始化
        val LINE_FEED = byteArrayOf(0x0A)             // 换行
        val ALIGN_CENTER = byteArrayOf(0x1B, 0x61, 0x01) // 居中
        val ALIGN_LEFT = byteArrayOf(0x1B, 0x61, 0x00)   // 左对齐
        val FONT_SIZE_LARGE = byteArrayOf(0x1D, 0x21, 0x11) // 倍高倍宽

        fun textToBytes(text: String): ByteArray {
            // 通常小票打印机使用 GBK 编码处理中文
            return (text + "\n").toByteArray(charset("GBK"))
        }
    }

    fun close() {
        try {
            outputStream?.close()
            socket?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}