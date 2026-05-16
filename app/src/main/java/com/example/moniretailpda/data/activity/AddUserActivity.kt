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
import com.example.moniretailpda.data.model.UserViewModel

class AddUserActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    private lateinit var btnSave: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adduser)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        initModel()
        initObserver()
    }

    private fun initView(){
        etEmail = findViewById<EditText>(R.id.etEmail)
        etPassword = findViewById<EditText>(R.id.etPassword)
        btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener{
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (email.isEmpty()){
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.addUser(email,password)
        }

    }

    private fun initModel(){
        viewModel = UserViewModel(application)
    }

    private fun initObserver(){
        viewModel.userboolean.observe(this){ success ->
            if(success){
                Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Failed to add User", Toast.LENGTH_SHORT).show()
            }
        }
    }
}