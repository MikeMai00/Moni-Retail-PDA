package com.example.moniretailpda.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moniretailpda.R
import com.example.moniretailpda.data.entity.PrinterEntity



class PrinterAdapter(
    var PrinterList: List<PrinterEntity> = emptyList(),
    private val onPrinterClicked: (PrinterEntity) -> Unit
): RecyclerView.Adapter<PrinterAdapter.PrintViewHolder>() {

    inner class PrintViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrintViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_printer, parent,false)
        return PrintViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrintViewHolder, position: Int) {
        val tvPrinterName = holder.itemView.findViewById<TextView>(R.id.tvPrinterName)
        val blackLine = holder.itemView.findViewById<View>(R.id.BlackLine)
        val printer = PrinterList[position]
        tvPrinterName.text = printer.printerName
        blackLine?.visibility = View.VISIBLE

        holder.itemView.setOnClickListener() {
            onPrinterClicked(printer)
        }
    }

    override fun getItemCount(): Int {
        return PrinterList.size
    }

}