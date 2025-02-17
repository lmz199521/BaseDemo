package com.example.demo.activity

import android.content.Intent
import com.example.demo.R
import com.example.demo.base.BaseActivity
import com.example.demo.databinding.ActivityMainLayoutBinding
import com.example.demo.http.RetrofitUtils
import com.example.extension.getService
import com.example.extension.loadHttp
import com.lmz.network.http.IHttpRequestService

class MainActivity : BaseActivity<ActivityMainLayoutBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_main_layout

    override fun initView() {
        binding?.helloBt?.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }
    }

    override fun initListener() {
        //演示使用方式
        loadHttp({ getService(IHttpRequestService::class.java).getArticleById(1) }, onSuccess = {

        })
    }

}