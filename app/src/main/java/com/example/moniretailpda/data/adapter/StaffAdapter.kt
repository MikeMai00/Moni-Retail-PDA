package com.example.moniretailpda.data.adapter


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
    var isEdit:Boolean = false,
    private val onStaffClicked: (StaffEntity) -> Unit
) : RecyclerView.Adapter<StaffAdapter.StaffViewHolder>() {
    val checkList = mutableListOf<String>()

    inner class StaffViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_staff, parent,false)
        return StaffViewHolder(view)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val tvName = holder.itemView.findViewById<TextView>(R.id.tvName)
        val btn_checkbox = holder.itemView.findViewById<CheckBox>(R.id.btn_checkBox)
        tvName.text = staffList[position].name
        btn_checkbox.setOnCheckedChangeListener(null)  //1先解除监听器，防止复用导致的逻辑混乱
        btn_checkbox.isChecked = checkList.contains(staffList[position].name) //2根据 checkList 实时判断当前位置是否该勾选

        //3重新设置监听器
        btn_checkbox.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                checkList.add(staffList[position].name)
            }
            else{
                checkList.remove(staffList[position].name)
            }
        }


        btn_checkbox.visibility = if(isEdit) View.VISIBLE else View.GONE
        tvName.setOnClickListener {
            if(!isEdit){
                onStaffClicked(staffList[position])
            }
            else{
                btn_checkbox.isChecked = !btn_checkbox.isChecked
            }
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

    fun clearAllChecks(){
        checkList.clear()
        notifyDataSetChanged()
    }



}