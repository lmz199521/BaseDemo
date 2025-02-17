package com.lmz.widght.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import com.lmz.widget.R
import com.lmz.widget.databinding.CustomDialogLayoutBinding

/**
 * describe: 自定义dialog控件
 * Date:2024/12/25
 * Author:lmz
 */
class CustomDialog @JvmOverloads constructor(
    context: Context,
    themeResId: Int = R.style.CustomDialog
) : AppCompatDialog(context, themeResId) {
    private var mTitle: String? = null
    private var mContent: String? = null
    private var contentColor = 0
    private var listener: DialogClickInterface? = null
    private var viewBinding: CustomDialogLayoutBinding


    init {
        val inflater = LayoutInflater.from(context)
        viewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.custom_dialog_layout,
            null,
            false
        ) as CustomDialogLayoutBinding
        setContentView(viewBinding.root)
        setData()
    }


    private fun setData() {
        viewBinding.apply {
            msg = mContent
            title = mTitle
            if (contentColor != 0) {
                tvMsg.setTextColor(contentColor)
            }
            dialog = this@CustomDialog
        }
    }

    fun onClick(action: String) {
        when (action) {
            "ok" -> {
                Toast.makeText(context, "你点击了确定", Toast.LENGTH_SHORT).show()
                listener?.setOkClick()
                dismiss()
            }

            "cancel" -> {
                dismiss()
            }
        }
    }

    fun setTitle(title: String) {
        this.mTitle = title
    }

    fun setContent(content: String) {
        this.mContent = content
    }

    fun setListener(listener: DialogClickInterface) {
        this.listener = listener
    }

    override fun show() {
        super.show()
        window?.let {
            it.setBackgroundDrawableResource(android.R.color.transparent)
            val attr = it.attributes as WindowManager.LayoutParams
            attr.width = WindowManager.LayoutParams.MATCH_PARENT
            attr.height = WindowManager.LayoutParams.WRAP_CONTENT
            it.attributes = attr
            window?.setGravity(Gravity.BOTTOM)
        }
    }
}