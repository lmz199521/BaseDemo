package com.example.demo.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.demo.databinding.FragmentMainLayoutBinding


abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    var dataBinding: T? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = DataBindingUtil.inflate<T>(inflater, getLayoutId(), container, false)
        dataBinding = inflate
        initView()
        initListener()
        return inflate.root
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataBinding?.unbind()
        dataBinding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

    //region 对外暴漏的抽象方法
    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun initListener()
    //endregion

}