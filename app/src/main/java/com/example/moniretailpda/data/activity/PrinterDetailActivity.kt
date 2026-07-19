package com.example.moniretailpda.data.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moniretailpda.R
import com.example.moniretailpda.data.entity.PrinterEntity
import com.example.moniretailpda.data.model.PrinterViewModel

class PrinterDetailActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etIp: EditText
    private lateinit var tvType: TextView
    private lateinit var btnSave: Button
    private lateinit var viewModel: PrinterViewModel
    private var currentPrinter: PrinterEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_printer_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.printerDetail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        initModel()
        initObserver()
    }
    private fun initView(){
        etName = findViewById(R.id.etDetailName)
        etIp = findViewById(R.id.etDetailIp)
        tvType = findViewById(R.id.tvConnectionType)
        btnSave = findViewById(R.id.btnSaveDetail)
        viewModel = PrinterViewModel(application)

        // 1. 接收从上一个 Activity 传过来的对象
        currentPrinter = intent.getSerializableExtra("PRINTER_DATA") as? PrinterEntity

        // 2. 将数据显示在界面上
        currentPrinter?.let { printer ->
            etName.setText(printer.printerName)
            etIp.setText(printer.ipAddress.ifEmpty { "Not Configured" })
            tvType.text = "Network"
        }

        // 3. 点击保存按钮更新数据
        btnSave.setOnClickListener {
            val newName = etName.text.toString().trim()
            val newIp = etIp.text.toString().trim()

            if (newName.isEmpty() || newIp.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            currentPrinter?.let { printer ->
                viewModel.updatePrinter(newName, newIp, "Network", printer.belongUser)
            }
        }
    }

    private fun initModel(){
        viewModel = PrinterViewModel(application)
    }

    private fun initObserver(){
        viewModel.printerupdate.observe(this){success ->
            if (success){
                Toast.makeText(this, "Printer updated", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Failed to update printer", Toast.LENGTH_SHORT).show()
            }
        }
    }
}