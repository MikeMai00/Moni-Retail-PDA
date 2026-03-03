package com.example.moniretailpda.data.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moniretailpda.data.entity.StaffEntity
import com.example.moniretailpda.R


class StaffAdapter(
    var staffList: List<StaffEntity> = emptyList(),
    var isEdit:Boolean = false
) : RecyclerView.Adapter<StaffAdapter.StaffViewHolder>() {
    val TAG = "StaffAdapter"
    val checkList = mutableListOf<String>()

    inner class StaffViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_staff, parent,false)
        return StaffViewHolder(view)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        Log.d(TAG, "bind value: $position")

        val tvName = holder.itemView.findViewById<TextView>(R.id.tvName)
        val btn_checkbox = holder.itemView.findViewById<CheckBox>(R.id.btn_checkBox)
        btn_checkbox.setOnCheckedChangeListener(null)
        btn_checkbox.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                checkList.add(staffList[position].name)
            }
            else{
                checkList.remove(staffList[position].name)
            }
        }

        tvName.text = staffList[position].name
        btn_checkbox.visibility = if(isEdit) View.VISIBLE else View.GONE
        tvName.setOnClickListener {
            btn_checkbox.isChecked = !btn_checkbox.isChecked
        }

    }

    override fun getItemCount(): Int {
        return staffList.size
    }

    fun setList( list: List<StaffEntity> ){
        staffList = list
        notifyDataSetChanged()
    }

    fun mode(mode:Boolean = false){
        isEdit = mode
        notifyDataSetChanged()

    }

    fun getCheckedList():List<String>{
        return checkList
    }



}