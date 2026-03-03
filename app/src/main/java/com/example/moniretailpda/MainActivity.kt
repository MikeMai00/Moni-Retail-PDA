package com.example.moniretailpda

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.OnBackPressedCallback


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 在这里留空，或者弹出一个 Toast 提示用户

            }
        }

        onBackPressedDispatcher.addCallback(this, callback)

        initView()
    }

    private fun initView(){
        val btn_itemEdit = findViewById<Button>(R.id.btn_itemEdit)
        val btn_stockTake = findViewById<Button>(R.id.btn_stockTake)
        val btn_priceCheck = findViewById<Button>(R.id.btn_priceCheck)
        val btn_signOut = findViewById<Button>(R.id.btn_signOut)

        btn_itemEdit.setOnClickListener {
            startActivity(Intent(this, ItemEditActivity::class.java))

        }

        btn_stockTake.setOnClickListener {
            startActivity(Intent(this, StockTakeActivity::class.java))

        }

        btn_priceCheck.setOnClickListener{
            startActivity(Intent(this, PriceCheckActivity::class.java))

        }

        btn_signOut.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }


}
