package com.example.moniretailpda.data

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moniretailpda.R
import com.example.moniretailpda.data.adapter.StaffAdapter
import com.example.moniretailpda.data.model.StaffViewModel


//CTRL + ALT + L   Reformat code
class StaffActivity : AppCompatActivity() {
    private val TAG = "StaffActivity"

    private lateinit var btn_addStaff: Button
    private lateinit var viewModel: StaffViewModel
    private lateinit var rvStaff: RecyclerView
    private lateinit var adapter: StaffAdapter
    private lateinit var btn_editStaff: Button
    private lateinit var btn_deleteStaff: Button
    private lateinit var btn_done: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_staff)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.staffactivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d(TAG, "onCreate: ")
        initView()
        initModel()
        initObserver()
    }

    private fun initView() {
        Log.d(TAG, "initview: ")

        rvStaff = findViewById<RecyclerView>(R.id.rvStaff)
        btn_editStaff = findViewById<Button>(R.id.btn_editStaff)
        btn_addStaff = findViewById<Button>(R.id.btn_addStaff)
        btn_deleteStaff = findViewById<Button>(R.id.btn_deleteStaff)
        btn_done = findViewById<Button>(R.id.btn_done)

        btn_addStaff.setOnClickListener() {
            startActivity(Intent(this, AddStaffActivity::class.java))
        }
        btn_editStaff.setOnClickListener() {
            Log.d(TAG, "edit mode: ")
            adapter.mode(true)
            if (btn_editStaff.isVisible) {
                btn_editStaff.visibility = View.GONE
                btn_deleteStaff.visibility = View.VISIBLE
                btn_addStaff.visibility = View.GONE
                btn_done.visibility = View.VISIBLE
            }

        }

        btn_done.setOnClickListener() {
            Log.d(TAG, "done mode: ")
            adapter.mode(false)
            if (btn_done.isVisible) {
                btn_editStaff.visibility = View.VISIBLE
                btn_deleteStaff.visibility = View.GONE
                btn_addStaff.visibility = View.VISIBLE
                btn_done.visibility = View.GONE
            }
        }

        btn_deleteStaff.setOnClickListener() {
            Log.d(TAG, "delete mode: ")
            viewModel.deleteStaff(adapter.getCheckedList())
        }


        adapter = StaffAdapter()
        rvStaff.adapter = adapter
        rvStaff.layoutManager = LinearLayoutManager(this)


    }

    private fun initModel() {
        viewModel = StaffViewModel(application)
    }

    private fun initObserver() {
        viewModel.stafflist.observe(this) { staffList ->
            Log.d(TAG, "create adapter: ")
            adapter.setList(staffList)
        }
        viewModel.staffdelete.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Staff deleted successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to add Staff", Toast.LENGTH_SHORT).show()
            }

        }
    }


    override fun onStart() {
        super.onStart()
        viewModel.getAllStaff()

    }


}