package com.example.moniretailpda.data.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import com.example.moniretailpda.R

// 改为继承 DialogFragment，接收一个回调函数来返回输入的 PIN
class PinLoginDialog(private val onPinEntered: (String) -> Unit) : DialogFragment() {

    private lateinit var etPinDisplay: EditText
    private var currentPin = ""
    private val MAX_PIN_LENGTH = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. 新增：禁用点击外部区域或按返回键关闭弹窗
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // 使用 inflater 加载布局
        return inflater.inflate(R.layout.activity_pin_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        etPinDisplay = view.findViewById(R.id.etPinDisplay)

        // 绑定数字按钮 0-9
        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
            R.id.btn8, R.id.btn9
        )

        buttons.forEach { id ->
            view.findViewById<Button>(id).setOnClickListener {
                val digit = (it as Button).text.toString()
                handleInput(digit)
            }
        }

        // 删除键 (X)
        view.findViewById<Button>(R.id.btnDelete).setOnClickListener {
            if (currentPin.isNotEmpty()) {
                currentPin = currentPin.dropLast(1)
                etPinDisplay.setText(currentPin)
            }
        }

        // 清空键 (CLEAR)
        view.findViewById<Button>(R.id.btnClear).setOnClickListener {
            currentPin = ""
            etPinDisplay.setText("")
        }

        //关闭键
        view.findViewById<ImageButton>(R.id.btnClose).setOnClickListener{
            dismiss()
        }
    }

    private fun handleInput(digit: String) {
        if (currentPin.length < MAX_PIN_LENGTH) {
            currentPin += digit
            etPinDisplay.setText(currentPin)

            if (currentPin.length == MAX_PIN_LENGTH) {
                // 【核心复用逻辑】达到4位后，直接把 PIN 传回给调用者
                onPinEntered(currentPin)
            }
        }
    }

    // 供外部调用：如果 Activity 校验失败，调用此方法显示动画
    fun showWrongPinAnimation() {
        val shakeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
        etPinDisplay.startAnimation(shakeAnimation)
        etPinDisplay.performHapticFeedback(android.view.HapticFeedbackConstants.LONG_PRESS)
        currentPin = ""
        etPinDisplay.setText("")
    }


    //供外部调用：如果 Activity 校验失败，调用此方法使手机物理震动
    fun performHapticFeedback() {
        // 触发长震动反馈（类似报错的触感）
        etPinDisplay.performHapticFeedback(
            android.view.HapticFeedbackConstants.LONG_PRESS,
            android.view.HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
        )
    }

    override fun onStart() {
        super.onStart()
        // 设置弹窗宽度占满屏幕，背景透明
        dialog?.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }
}