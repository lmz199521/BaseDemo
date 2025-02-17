package com.example.demo.base

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : ComponentActivity() {

    var binding: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        initIntent()
        initView()
        initListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.unbind()
        binding = null
    }

    //region 对外暴漏的抽象方法
    abstract fun getLayoutId(): Int

    open fun initIntent() {

    }

    abstract fun initView()

    abstract fun initListener()
    //endregion

}