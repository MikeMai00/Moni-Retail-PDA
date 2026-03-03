package com.example.moniretailpda

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moniretailpda.data.StaffActivity


class LoginActivity : AppCompatActivity() {



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

    }

    private fun initView() {
        val etEmail = findViewById<EditText>(R.id.edit_email)
        val etPassword = findViewById<EditText>(R.id.edit_password)
        val btnLogin = findViewById<Button>(R.id.signin_button)
        val btn_staff = findViewById<Button>(R.id.staff_button)
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            startActivity(Intent(this, MainActivity::class.java))

        }
        btn_staff.setOnClickListener{
            startActivity(Intent(this, StaffActivity::class.java))
        }
    }
}




