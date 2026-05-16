package com.example.moniretailpda.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//用于产品的实体类
@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val itemName: String = "",
    val barcode:String = "",
    val price: String = "",
    val cost: String = "",
    val currentStock: String = "",
    val belongUser: String =""
) {

}