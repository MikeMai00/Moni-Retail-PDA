package com.example.moniretailpda.data.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.moniretailpda.R
import com.example.moniretailpda.data.entity.ProductEntity
import com.example.moniretailpda.data.model.ProductViewModel
import com.example.moniretailpda.data.session.SessionManager

class ItemEditActivity : AppCompatActivity() {

    private lateinit var btnDetele: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnEnter: Button
    private lateinit var etBarcode: EditText
    private lateinit var etDescription: EditText
    private lateinit var etPrice: EditText
    private lateinit var etCost: EditText
    private lateinit var etStock: EditText
    private lateinit var viewModel: ProductViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_item_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ItemEdit)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        initViewModel()
        initObserver()
    }

    //搭界面 + 处理点击
    private fun initView() {
        btnDetele = findViewById<Button>(R.id.btnDelete)
        btnUpdate = findViewById<Button>(R.id.btnUpdate)
        btnEnter = findViewById<Button>(R.id.btnEnter)
        etBarcode = findViewById(R.id.etBarcode)
        etDescription = findViewById(R.id.etDescription)
        etPrice = findViewById(R.id.etPrice)
        etCost = findViewById(R.id.etCost)
        etStock = findViewById(R.id.etStock)
        sessionManager = SessionManager(this)

        btnEnter.setOnClickListener {
            val barcode = etBarcode.text.toString().trim()
            if (barcode.isEmpty()) {
                Toast.makeText(this, "Barcode is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.getProductById(barcode, sessionManager.getUserId().toString())

        }

        btnUpdate.setOnClickListener{
            val barcode = etBarcode.text.toString().trim()
            val itemName = etDescription.text.toString().trim()
            val price = etPrice.text.toString().trim()
            val cost = etCost.text.toString().trim()
            val stock = etStock.text.toString().trim()
            if (barcode.isEmpty()) {
                Toast.makeText(this, "Barcode is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(itemName.isEmpty()){
                Toast.makeText(this, "Item Name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.updateProduct(barcode,itemName,price,cost,stock)
            //收键盘
            val currentView = this.currentFocus
            if (currentView != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentView.windowToken, 0)
            }
        }

        btnDetele.setOnClickListener{
            val barcode = etBarcode.text.toString().trim()
            if (barcode.isEmpty()) {
                Toast.makeText(this, "Barcode is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.deleteProduct(barcode)
        }

    }

    //业务中枢
    private fun initViewModel() {
        viewModel = ProductViewModel(application)
    }

    //监听结果、更新界面
    private fun initObserver() {
        viewModel.product.observe(this) { product ->

            //判断界面有没有加载过数据
            if (viewModel.hasSearched.value != true) return@observe

            if (product == null) {
                showProductNotFoundDialog()
            } else {
                showProductInfo(product)
                btnDetele.isEnabled = true
                btnUpdate.isEnabled = true
            }
        }

        viewModel.productupdate.observe(this){ productupdate ->

            if(productupdate != true) return@observe

            Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show()
            clear()


        }


        //Log.d("TAG", "productdelete: $productdelete") 查询log,在logcat上需要输入 is:debug
        viewModel.productdelete.observe(this){ productdelete ->
            if(productdelete != true) return@observe
            AlertDialog.Builder(this)
                .setTitle("Delete Product")
                .setMessage("Are you sure you want to delete this product?")
                .setPositiveButton("Yes"){dialog,_->
                    Toast.makeText(this, "Product deleted successfully", Toast.LENGTH_SHORT).show()
                    btnDetele.isEnabled = false
                    clear()
                }
                .setNegativeButton("No"){dialog,_->
                    dialog.dismiss()
                }.show()

        }


    }


    private fun showProductNotFoundDialog(){
            AlertDialog.Builder(this)
                .setTitle("Not Found")
                .setMessage("Product not found. Do you want to add a new product?")
                .setPositiveButton("Yes"){dialog, _->
                    val intent = Intent(this, AddItemActivity::class.java)
                    intent.putExtra("unexistingbarcode",etBarcode.text.toString().trim())
                    startActivity(intent)
                }
                .setNegativeButton("No"){dialog, _-> dialog.dismiss()}
                .show()
    }

    private fun showProductInfo(product: ProductEntity){
        etDescription.setText(product.itemName)
        etPrice.setText(product.price)
        etCost.setText(product.cost)
        etStock.setText(product.currentStock)
    }



    private fun clear(){
        etBarcode.text.clear()
        etDescription.text.clear()
        etPrice.text.clear()
        etCost.text.clear()
        etStock.text.clear()
    }
}



