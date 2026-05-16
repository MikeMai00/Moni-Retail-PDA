package com.example.moniretailpda.data.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moniretailpda.R
import com.example.moniretailpda.data.session.SessionManager
import com.example.moniretailpda.data.model.StaffViewModel

class AddStaffActivity : AppCompatActivity() {

    private lateinit var viewModel: StaffViewModel
    private lateinit var btnSave: Button
    private lateinit var etStaffName: EditText
    private lateinit var etPassword: EditText
    private lateinit var sessionManager: SessionManager
    private var pinDialog: PinLoginDialog? = null

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

        etPassword.setOnClickListener{
            pindialog()
        }

        btnSave.setOnClickListener{
            val staffName = etStaffName.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (staffName.isEmpty()){
                Toast.makeText(this, "Staff Name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                Toast.makeText(this, "Pin is required", Toast.LENGTH_SHORT).show()
                if(password.length <4){
                    Toast.makeText(this, "Pin must be 4 digits", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            sessionManager = SessionManager(this)
            viewModel.addStaff(staffName,password,sessionManager.getUserId().toString())
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

    private fun pindialog(){
        pinDialog = PinLoginDialog { pin ->
            etPassword.setText(pin)
            pinDialog?.dismiss()
        }
        pinDialog?.show(supportFragmentManager, "PinLoginDialog")}
}