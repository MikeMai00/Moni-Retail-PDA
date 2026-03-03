package com.example.moniretailpda.data

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moniretailpda.R
import com.example.moniretailpda.data.model.StaffViewModel

class AddStaffActivity : AppCompatActivity() {

    private lateinit var viewModel: StaffViewModel
    private lateinit var btnSave: Button
    private lateinit var etStaffName: EditText
    private lateinit var etPassword: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_staff)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addstaff)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        initModel()
        initObserver()
    }

    private fun initView(){
        etStaffName = findViewById<EditText>(R.id.etStaffName)
        etPassword = findViewById<EditText>(R.id.etPassword)
        btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener{
            val staffName = etStaffName.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (staffName.isEmpty()){
                Toast.makeText(this, "Staff Name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.addStaff(staffName,password)
        }

    }

    private fun initModel(){
        viewModel = StaffViewModel(application)
    }

    private fun initObserver(){
        viewModel.staffboolean.observe(this){ success ->
            if(success){
                Toast.makeText(this, "Staff added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Failed to add Staff", Toast.LENGTH_SHORT).show()
            }
        }
    }
}