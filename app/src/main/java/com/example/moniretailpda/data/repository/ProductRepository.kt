package com.example.moniretailpda.data.repository

import com.example.moniretailpda.data.dao.ProductDao
import com.example.moniretailpda.data.entity.ProductEntity

class ProductRepository(private val productDao: ProductDao) {

    suspend fun addProduct(product: ProductEntity){
        productDao.addProduct(product)
    }

    suspend fun getProductById(barcode: String): ProductEntity?{
        return productDao.getProductById(barcode)
    }

    suspend fun updateProduct(barcode:String,itemName: String, price: String, cost: String,stock:String){
        productDao.updateProduct(barcode,itemName,price,cost,stock)
    }

    suspend fun deleteProduct(barcode: String){
        productDao.deleteProduct(barcode)
    }

    suspend fun updateStock(barcode: String, currentStock: String){
        productDao.updateStock(barcode,currentStock)

    }
}