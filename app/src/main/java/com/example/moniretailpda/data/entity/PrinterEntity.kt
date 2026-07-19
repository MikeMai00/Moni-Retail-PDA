package com.example.moniretailpda.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "printer_table")
data class PrinterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val printerName: String = "",
    val ipAddress: String = "",
    val connectionType: String ="",
    val belongUser: String = "",
) : java.io.Serializable{

}