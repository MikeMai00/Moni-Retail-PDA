package com.example.moniretailpda

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moniretailpda.data.model.ProductViewModel

class  AddItemActivity : AppCompatActivity() {

    private lateinit var btnSave: Button
    private lateinit var etBarcode: EditText
    private lateinit var etItemName: EditText
    private lateinit var etPrice: EditText
    private lateinit var etCost: EditText
    private lateinit var etStock: EditText
    private lateinit var viewModel: ProductViewModel
    private var newBarcode : String = ""

    private companion object {
        const val unexistingBarcode = "unexistingbarcode"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.AddItem)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        initViewModel()
        initObserver()
    }

    private fun initView(){
        newBarcode = intent.getStringExtra(unexistingBarcode)?:""
        btnSave = findViewById<Button>(R.id.btnSaveProduct)
        etBarcode = findViewById<EditText>(R.id.etBarcode)
        etItemName = findViewById<EditText>(R.id.etItemName)
        etPrice = findViewById<EditText>(R.id.etPrice)
        etCost = findViewById<EditText>(R.id.etCost)
        etStock = findViewById<EditText>(R.id.etStock)

        etBarcode.setText(newBarcode)

        btnSave.setOnClickListener {
            val barcode = etBarcode.text.toString().trim()
            val itemName = etItemName.text.toString().trim()
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

            viewModel.addProduct(barcode,itemName,price,cost,stock)
        }
    }

    private fun initViewModel() {
        viewModel = ProductViewModel(application)
    }


    private fun initObserver() {
        viewModel.productboolean.observe(this){ success ->
            if(success){
                Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show()
                clear()
                finish()
            }
            else{
                Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clear(){
        etBarcode.text.clear()
        etItemName.text.clear()
        etPrice.text.clear()
        etCost.text.clear()
        etStock.text.clear()
    }
}