package com.example.demo.activity

import android.net.http.HttpException
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.demo.R
import com.example.demo.base.BaseActivity
import com.example.demo.databinding.ActivityTestLayoutBinding
import com.example.demo.extension.toJsonString
import com.lmz.widght.dialog.CustomDialog
import kotlinx.coroutines.CoroutineScope
import org.json.JSONException

/**
 * describe: 用于测试的Activity
 * Date:2024/12/14
 * Author:lmz
 */
class TestActivity : BaseActivity<ActivityTestLayoutBinding>() , View.OnClickListener{
    override fun getLayoutId(): Int {
        return R.layout.activity_test_layout
    }

    override fun initView() {
        getColor(R.color.black)
        R.color.purple_200
        binding?.let {
            it.imageUrl="https://pic.rmb.bdstatic.com/bjh/3f19e4398b/240612/db6d81a1b8b56df7c0a40e5ec4317d9d.jpeg@h_1280"
            it.defaultRes=R.drawable.icon_ava
        }
    }

    override fun initListener() {
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


    fun btDialog(v: View?){
        val customDialog = CustomDialog(this)
        customDialog.apply {
            setTitle("dialog弹框")
            setContent("这是一个自定义弹框")

        }.show()

        val dialog = AlertDialog.Builder(this)
    }

}