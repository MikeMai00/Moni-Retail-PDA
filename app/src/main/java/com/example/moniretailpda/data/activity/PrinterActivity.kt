package com.example.moniretailpda.data.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moniretailpda.R
import com.example.moniretailpda.data.adapter.PrinterAdapter
import com.example.moniretailpda.data.entity.PrinterEntity
import com.example.moniretailpda.data.model.PrinterViewModel
import com.example.moniretailpda.data.session.SessionManager

class PrinterActivity : AppCompatActivity(){
    private lateinit var viewModel: PrinterViewModel
    private lateinit var btn_addPrinter : Button
    private lateinit var adapter : PrinterAdapter
    private lateinit var rvPrinter: RecyclerView
    private lateinit var sessionManager: SessionManager
    private var currentPrinterCount = 0 //用于记录当前列表长度
    private var currenetSelectedPrinter: PrinterEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_printer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.printeractivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        initModel()
        initObserver()
    }

    private fun initView(){
        rvPrinter = findViewById<RecyclerView>(R.id.rvPrinter)
        btn_addPrinter = findViewById<Button>(R.id.btn_addPrinter)
        sessionManager = SessionManager(this)
        adapter = PrinterAdapter(){selectedPrinter ->
            currenetSelectedPrinter = selectedPrinter
            val intent = Intent(this, PrinterDetailActivity::class.java)
            intent.putExtra("PRINTER_DATA", selectedPrinter)
            startActivity(intent)
        }
        rvPrinter.adapter = adapter
        rvPrinter.layoutManager = LinearLayoutManager(this)


        btn_addPrinter.setOnClickListener(){
            val newPrinterName = "Printer ${++currentPrinterCount}"
            viewModel.addPrinter(newPrinterName,"","",sessionManager.getUserId().toString())
        }
    }
    private fun initModel(){
        viewModel = PrinterViewModel(application)

    }

    private fun initObserver(){
        viewModel.printerboolean.observe(this)
        {
            if (it) {
                viewModel.getAllPrinter(sessionManager.getUserId().toString())
            }
        }
        viewModel.printerlist.observe(this) { printerList ->
            adapter.PrinterList = printerList
            adapter.notifyDataSetChanged()
            currentPrinterCount = printerList.size
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllPrinter(sessionManager.getUserId().toString())

    }
}