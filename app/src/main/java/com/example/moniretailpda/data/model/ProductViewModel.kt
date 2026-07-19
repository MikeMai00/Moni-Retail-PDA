package com.example.moniretailpda.data.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moniretailpda.data.database.PDA_Database
import com.example.moniretailpda.data.entity.ProductEntity
import com.example.moniretailpda.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application:Application): AndroidViewModel(application) {

    private val repository: ProductRepository


    //用于返回结果的LiveData
    //用于增加
    private val _productboolean = MutableLiveData<Boolean>()
    val productboolean: MutableLiveData<Boolean> = _productboolean

    //用于查询
    private val _product = MutableLiveData<ProductEntity>()
    val product: MutableLiveData<ProductEntity> = _product


    //用于Update
    private val _productupdate = MutableLiveData<Boolean>()
    val productupdate: MutableLiveData<Boolean> = _productupdate

    //用于delete
    private val _productdelete = MutableLiveData<Boolean>()
    val productdelete: MutableLiveData<Boolean> = _productdelete

    //用于更新库存
    private val _productupdateStock = MutableLiveData<Boolean>()
    val productupdateStock: MutableLiveData<Boolean> = _productupdateStock

    //是否已经执行过查询
    private val _hasSearched = MutableLiveData(false)
    val hasSearched: LiveData<Boolean> = _hasSearched

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> = _errorMsg


    init{
        val productDao = PDA_Database.getDatabase(application).productDao()
        repository = ProductRepository(productDao)

    }

    fun addProduct(barcode:String,itemName:String,price:String,cost:String,stock:String,belongUser:String){
        viewModelScope.launch(Dispatchers.IO) {
            val existingProduct = repository.getProductById(barcode, belongUser)
            if (existingProduct != null) {
                _errorMsg.postValue("Product already exists")
                _productboolean.postValue(false)
            } else {
                    try {
                        val product = ProductEntity(
                            barcode = barcode,
                            itemName = itemName,
                            price = price,
                            cost = cost,
                            currentStock = stock,
                            belongUser = belongUser
                        )

                        repository.addProduct(product)
                        _productboolean.postValue(true)
                    } catch (e: Exception) {
                        _productboolean.postValue(false)

                    }
            }
        }
    }

    fun getProductById(barcode: String,belongUser: String) {
        viewModelScope.launch {
            _hasSearched.value = true
            _product.value = repository.getProductById(barcode, belongUser)
        }

    }

    fun updateProduct(barcode:String,itemName: String, price: String, cost: String,stock:String){
        viewModelScope.launch(Dispatchers.IO){
            try{
                repository.updateProduct(barcode,itemName,price,cost,stock)
                _productupdate.postValue(true)
            }
            catch (e:Exception){
                _productupdate.postValue(false)

            }
        }
    }

    fun deleteProduct(barcode: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                repository.deleteProduct(barcode)
                _productdelete.postValue(true)
            }
            catch (e:Exception){
                _productdelete.postValue(false)
            }

        }
    }

    fun updateStock(barcode: String, currentStock:String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                repository.updateStock(barcode,currentStock)
                _productupdateStock.postValue(true)
            }
            catch(e:Exception){
                _productupdateStock.postValue(false)
            }
        }
    }
}