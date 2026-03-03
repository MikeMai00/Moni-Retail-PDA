package com.example.moniretailpda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moniretailpda.data.model.ProductViewModel

class StockTakeActivity : AppCompatActivity() {

    private lateinit var viewModel: ProductViewModel
    private var currentCount = 0
    private lateinit var btnScan : Button
    private lateinit var btnConfirm : Button
    private lateinit var btnUpdate : Button
    private lateinit var etBarcode : EditText
    private lateinit var tvCurrentStock: EditText
    private lateinit var tvCount : TextView
    private var newStock = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_stock_take)

        initView()
        initViewModel()
        initObserver()

    }

    private fun initView(){
        val tvCount = findViewById<TextView>(R.id.tvCount)
        val btnAdd = findViewById<ImageButton>(R.id.btnAdd)
        val btnMinus = findViewById<ImageButton>(R.id.btnMinus)
        val btnScan = findViewById<Button>(R.id.btnScan)
        val currentStock = findViewById<EditText>(R.id.tvCurrentStock)
        val etBarcode = findViewById<EditText>(R.id.etBarcode)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)




        btnScan.setOnClickListener{
            val barcode = etBarcode.text.toString().trim()
            if (barcode.isEmpty()) {
                Toast.makeText(this, "Barcode is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.getProductById(barcode)

        }

        btnConfirm.setOnClickListener{
            val currentStockNum = currentStock.text.toString().toInt()
            newStock = currentStockNum + currentCount
            val barcode = etBarcode.text.toString().trim()

            if (barcode.isEmpty()) {
                Toast.makeText(this, "Scan barcode first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (currentCount == 0) {
                Toast.makeText(this, "No stock change", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            currentStock.setText(newStock.toString())
        }

        btnUpdate.setOnClickListener{
            val barcode = etBarcode.text.toString().trim()
            val newStockText = newStock.toString()
            if (barcode.isEmpty()) {
                Toast.makeText(this, "Barcode is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.updateStock(barcode,newStockText)
        }

        btnAdd.setOnClickListener {
            currentCount++
            tvCount.text = currentCount.toString()
        }

        btnMinus.setOnClickListener {
            if (currentCount > 0) {
                currentCount--
                tvCount.text = currentCount.toString()
            }
        }
    }

    private fun initViewModel(){
        viewModel = ProductViewModel(application)
    }

    private fun initObserver() {
        val currentStock = findViewById<EditText>(R.id.tvCurrentStock)
        val barcode = findViewById<EditText>(R.id.etBarcode)
        val count = findViewById<TextView>(R.id.tvCount)

        viewModel.product.observe(this) { product ->

            //判断界面有没有加载过数据
            if (viewModel.hasSearched.value != true) return@observe

            try{
                if(product.currentStock == ""){
                    currentStock.setText("0")
                }
                else {
                    currentStock.setText(product.currentStock)
                }
            }
            catch (e:Exception){
                showProductNotFoundDialog()
            }

        }

        viewModel.productupdateStock.observe(this) { productupdateStock ->
            if (productupdateStock != true) return@observe
            barcode.text.clear()
            currentStock.text.clear()
            count.text = "0"
            currentCount= 0
            Toast.makeText(this, "Stock updated successfully", Toast.LENGTH_SHORT).show()
        }
    }



    private fun showProductNotFoundDialog(){
        val etbarcode = findViewById<EditText>(R.id.etBarcode)
        val barcode = etbarcode.text.toString().trim()
        AlertDialog.Builder(this)
            .setTitle("Not Found")
            .setMessage("Product not found. Do you want to add a new product?")
            .setPositiveButton("Yes"){diaglog, _->
                val intent = Intent(this, AddItemActivity::class.java)
                intent.putExtra("unexistingbarcode",barcode)
                startActivity(intent)
            }
            .setNegativeButton("No"){dialog, _-> dialog.dismiss()}
            .show()
    }


}


