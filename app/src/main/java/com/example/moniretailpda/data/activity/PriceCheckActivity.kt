package com.example.moniretailpda.data.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moniretailpda.R
import com.example.moniretailpda.data.model.ProductViewModel
import com.example.moniretailpda.data.session.SessionManager

class PriceCheckActivity : AppCompatActivity() {

    private lateinit var btnSearch: Button
    private lateinit var etBarcode: EditText
    private lateinit var tvProductName: TextView
    private lateinit var tvNormalPrice: TextView
    private lateinit var viewModel: ProductViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_price_check)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.PriceCheck)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        initViewModel()
        initObserver()
    }

    private fun initView() {
        btnSearch = findViewById<Button>(R.id.btnSearch)
        etBarcode = findViewById<EditText>(R.id.etBarcode)
        tvProductName = findViewById<TextView>(R.id.tvProductName)
        tvNormalPrice = findViewById<TextView>(R.id.tvNormalPrice)
        sessionManager = SessionManager(this)

        btnSearch.setOnClickListener {
            val barcode = etBarcode.text.toString().trim()
            if (barcode.isEmpty()) {
                Toast.makeText(this, "Barcode is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.getProductById(barcode, sessionManager.getUserId().toString())
        }

    }

    private fun initViewModel() {
        viewModel = ProductViewModel(application)
    }

    private fun initObserver() {
        viewModel.product.observe(this) { product ->
            if (viewModel.hasSearched.value != true) return@observe

            if (product == null) {
                showProductNotFoundDialog()
            } else {
                if(product.price == ""){
                    etBarcode.setText(product.barcode)
                    etBarcode.setSelection(product.barcode.length)
                    tvProductName.setText(product.itemName)
                    tvNormalPrice.setText("$0.00")
                }
                else{
                    etBarcode.setText(product.barcode)
                    etBarcode.setSelection(product.barcode.length)
                    tvProductName.setText(product.itemName)
                    tvNormalPrice.setText("$" + product.price)
                }
            }
        }
    }

    private fun showProductNotFoundDialog(){
        val barcode= etBarcode.text.toString().trim()
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