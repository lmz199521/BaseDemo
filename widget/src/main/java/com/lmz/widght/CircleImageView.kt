package com.lmz.widght

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.lmz.widget.R

/**
 * describe: 圆形控件
 * Date:2024/12/21
 * Author:lmz
 */
class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: android.util.AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    private val path = Path()
    var left_top_radio = 0f
    var left_bottom_radio = 0f
    var right_top_radio = 0f
    var right_bottom_radio = 0f
    var type = 1

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
        left_top_radio = typedArray.getDimension(R.styleable.CircleImageView_left_top_radio, 0f)
        left_bottom_radio =
            typedArray.getDimension(R.styleable.CircleImageView_left_bottom_radio, 0f)
        right_top_radio = typedArray.getDimension(R.styleable.CircleImageView_right_top_radio, 0f)
        right_bottom_radio =
            typedArray.getDimension(R.styleable.CircleImageView_right_bottom_radio, 0f)
        type = typedArray.getInt(R.styleable.CircleImageView_type, 1)
    }

    // 目前4个角都还有点问题
    override fun onDraw(canvas: Canvas) {

        val rect = canvas.clipBounds
        if (type == 1) {
            val radius = rect.width().coerceAtMost(rect.height()) / 2f
            path.addCircle(rect.width() / 2f, rect.height() / 2f, radius, Path.Direction.CW)
        } else {
            val width = rect.width().toFloat()
            val height = rect.height().toFloat()
            val left_top = left_top_radio.coerceAtMost(width / 2f)
            val left_bottom = left_bottom_radio.coerceAtMost(width / 2f)
            val right_top = right_top_radio.coerceAtMost(width / 2f)
            val right_bottom = right_bottom_radio.coerceAtMost(width / 2f)
            Log.i(
                "circleView",
                "width:$width,height:$height,left_top.left_top:$left_top,left_bottom:$left_bottom,right_top:$right_top,right_bottom:$right_bottom"
            )

            // 移动路径到左上角
            path.moveTo(left_top, 0f)
            // 绘制右上角的直线
            path.lineTo(width - right_top, 0f)
            // 绘制右上角的圆角
            path.quadTo(width, 0f, width, right_top)
            // 绘制右下角的直线
            path.lineTo(width, height - right_bottom)
            // 绘制右下角的圆角
            path.quadTo(width, height, width - right_bottom, height)

            // 绘制左下角的直线
            path.lineTo(left_bottom, height)
            // 绘制左下角的圆角
            path.quadTo(0f, height, 0f, height - left_bottom)
            // 绘制左上角的直线
            path.lineTo(0f, left_top)
            // 绘制左上角的圆角
            path.quadTo(0f, 0f, left_top, 0f)

        }
        canvas.clipPath(path)
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

}