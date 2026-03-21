package com.example.moniretailpda

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewModelScope
import com.example.moniretailpda.data.StaffActivity

import com.example.moniretailpda.data.model.UserViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        initViewModel()
        initObserver()

    }

    private fun initView() {
        val etEmail = findViewById<EditText>(R.id.edit_email)
        val etPassword = findViewById<EditText>(R.id.edit_password)
        val btnLogin = findViewById<Button>(R.id.signin_button)
        val btn_staff = findViewById<Button>(R.id.staff_button)
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please input username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                viewModel.login(email,password)
            }
            //startActivity(Intent(this, MainActivity::class.java))

        }
        btn_staff.setOnClickListener{
            startActivity(Intent(this, StaffActivity::class.java))
        }
    }

    private fun initViewModel(){
        viewModel = UserViewModel(application)
    }


    private fun initObserver(){
        viewModel.userboolean.observe(this){success ->
            if(success){
                startActivity(Intent(this, MainActivity::class.java))
            }
            else{
                Toast.makeText(this,"Wrong username or password", Toast.LENGTH_SHORT).show()
            }

        }
    }
}




