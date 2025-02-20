package com.example.demo.activity

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.example.Tools.ScreenUtils
import com.example.demo.R
import com.example.demo.base.BaseActivity
import com.example.demo.databinding.ActivityTestLayoutBinding
import com.lmz.widght.CustomRedDot
import com.lmz.widght.ReddotView
import com.lmz.widght.dialog.CustomDialog

/**
 * describe: 用于测试的Activity
 * Date:2024/12/14
 * Author:lmz
 */
class TestActivity : BaseActivity<ActivityTestLayoutBinding>(), View.OnClickListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_test_layout
    }

    override fun initView() {
        getColor(R.color.black)
        R.color.purple_200
        binding?.let {
            it.imageUrl =
                "https://pic.rmb.bdstatic.com/bjh/3f19e4398b/240612/db6d81a1b8b56df7c0a40e5ec4317d9d.jpeg@h_1280"
            it.defaultRes = R.drawable.icon_ava

            /*            it.navigationView.apply {
                            visibility = View.VISIBLE // 确保CustomNavigationView可见
                            addMenu(
                                ContextCompat.getDrawable(this@TestActivity, R.drawable.icon_ava),
                                "首页",
                                1
                            )
                            addMenu(
                                ContextCompat.getDrawable(this@TestActivity, R.drawable.icon_more),
                                "视频",
                                1
                            )
                            addMenu(
                                ContextCompat.getDrawable(
                                    this@TestActivity,
                                    com.lmz.widget.R.drawable.icon_close
                                ), "我的", 1
                            )
                        }*/

            it.scrollRoot.apply {
                val redDot = CustomRedDot(this@TestActivity).apply {
                    maxHeight = ScreenUtils.dp2px(24f).toInt()
                    setPadding(
                        ScreenUtils.dp2px(6f).toInt(),
                        ScreenUtils.dp2px(4f).toInt(),
                        ScreenUtils.dp2px(6f).toInt(),
                        ScreenUtils.dp2px(4f).toInt()
                    )

                    text = "热门闷闷"
                }
                addView(redDot)
                it.icon1.viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        getViewTreeObserver().removeOnGlobalLayoutListener(this)
                        // 获取 icon1 的位置和大小
                        val location = IntArray(2)
                        it.icon1.getLocationOnScreen(location)



                        val location2 = IntArray(2)
                        redDot.getLocationOnScreen(location2)
                        // 计算目标位置
                        val targetX = it.icon1.x + it.icon1.width /2
                        val targetY = it.icon1.y - redDot.y
                        Log.i(
                            "redDot",
                            "targetX:$targetX ,   targetY$targetY,   redDotx:${redDot.x}, redDoty:${redDot.y}"
                        )

                        Log.i("redDot2", "redDotx:${redDot.x}, redDoty:${redDot.y}")
                        // 创建属性动画
                        val animatorX = ObjectAnimator.ofFloat(
                            redDot,
                            "translationX",
                            redDot.x,
                            targetX.toFloat()
                        )
                        val animatorY = ObjectAnimator.ofFloat(
                            redDot,
                            "translationY",
                            redDot.y,
                            targetY.toFloat()
                        )

                        // 设置动画持续时间
                        animatorX.duration = 2500
                        animatorY.duration = 2500

                        // 启动动画
                        animatorX.start()
                        animatorY.start()

                        // todo  这样写不行，因为linerlayout，即使是设置了margin也是最线性布局的前提下，不在出现在锚定的view上

                        // 编写一个属性动画，可以移动view到指定的位置


                    }
                })

                redDot.setOnClickListener {
                    Toast.makeText(this@TestActivity, "点击了红点", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun initListener() {
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


    fun btDialog(v: View?) {
        val customDialog = CustomDialog(this)
        customDialog.apply {
            setTitle("dialog弹框")
            setContent("这是一个自定义弹框")

        }.show()
        //  binding?.navigationView?.showRedDot()
        val dialog = AlertDialog.Builder(this)
    }
    var pop: PopupWindow? = null;



    val leftoffset = ScreenUtils.dp2px(16f)
    val topOffset = ScreenUtils.dp2px(2f)

    fun reddot(v: View?) {
        binding?.iconImg?.apply {
            val location = IntArray(2)
            this.getLocationOnScreen(location)
            Log.i("lmzlmz", "location:" + location[0] + ">>>>" + location[1])
            val redView = ReddotView(this@TestActivity).apply {
                text = "热门"
            }

            val rect = Rect()
            this.getGlobalVisibleRect(rect)
            Log.i("lmzlmz", "rect:" + rect.left + ">>>>" + rect.top)
            pop = PopupWindow(redView).apply {
                setWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                setOutsideTouchable(true)
                setFocusable(true)
            }
            pop?.showAtLocation(
                this,
                Gravity.TOP or Gravity.START,
                location[0] + leftoffset.toInt(),
                location[1] - topOffset.toInt()
            )
        }
    }

    fun clear(v: View?){
        pop?.dismiss()
        pop = null;
    }


}