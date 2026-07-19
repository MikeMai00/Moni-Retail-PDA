package com.example.moniretailpda.data.utils

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.OutputStream
import java.util.UUID

class BluetoothPrinterManager(productName:String ,barcode:String,price:String) {
    private val TAG = "PrinterManager"
    private val SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var bluetoothSocket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null

    // 获取已配对设备列表
    @SuppressLint("MissingPermission")
    fun getPairedDevices(): List<BluetoothDevice> {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        return adapter?.bondedDevices?.toList() ?: emptyList()
    }

    // 连接设备 (协程实现)
    @SuppressLint("MissingPermission")
    suspend fun connect(device: BluetoothDevice): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(SPP_UUID)
            bluetoothSocket?.connect()
            outputStream = bluetoothSocket?.outputStream
            Log.d(TAG, "Connected to ${device.name}")
            true
        } catch (e: IOException) {
            Log.e(TAG, "Connection failed", e)
            close()
            false
        }
    }

    // 发送字节数据 (ESC/POS 指令)
    suspend fun sendData(data: ByteArray): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            outputStream?.write(data)
            outputStream?.flush()
            true
        } catch (e: IOException) {
            false
        }
    }

    fun close() {
        try {
            outputStream?.close()
            bluetoothSocket?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}