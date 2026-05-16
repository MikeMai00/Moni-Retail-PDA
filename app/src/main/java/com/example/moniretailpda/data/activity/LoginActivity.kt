package com.example.moniretailpda.data.activity

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moniretailpda.R
import com.example.moniretailpda.data.session.SessionManager
import com.example.moniretailpda.data.model.UserViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    private lateinit var sessionManager: SessionManager

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
        val btn_adduser = findViewById<Button>(R.id.adduser_button)
        sessionManager = SessionManager(this)

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

        }

        btn_adduser.setOnClickListener{
            startActivity(Intent(this, AddUserActivity::class.java))
        }

        btn_staff.setOnClickListener{
            startActivity(Intent(this, StaffActivity::class.java))
        }
    }

    private fun initViewModel(){
        viewModel = UserViewModel(application)
    }


    private fun initObserver(){
        viewModel.userentity.observe(this){userEntity->
            if(userEntity != null){
                sessionManager.saveSession(userEntity.id)
                startActivity(Intent(this, StaffActivity::class.java))
            }
            else{
                Toast.makeText(this,"Wrong username or password", Toast.LENGTH_SHORT).show()
            }

        }
    }
}




