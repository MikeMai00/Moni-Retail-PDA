package com.example.moniretailpda.data.dao

import androidx.room.Dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moniretailpda.data.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: ProductEntity)

    @Query("SELECT * FROM product_table WHERE barcode = :barcode")
    suspend fun getProductById(barcode: String): ProductEntity?

    @Query("UPDATE product_table SET itemName = :itemName, price = :price, cost = :cost, currentStock= :currentStock WHERE barcode = :barcode")
    suspend fun updateProduct(barcode:String,itemName:String,price:String,cost:String,currentStock:String)

    @Query("DELETE From product_table WHERE barcode = :barcode")
    suspend fun deleteProduct(barcode:String)

    @Query("UPDATE product_table SET currentStock = :currentStock WHERE barcode = :barcode")
    suspend fun updateStock(barcode: String, currentStock: String)
}