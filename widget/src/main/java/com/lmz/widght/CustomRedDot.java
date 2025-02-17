package com.lmz.widght;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.Tools.ScreenUtils;

/**
 * 2025/2/15
 * Administrator
 * 自定义红点View
 */
public class CustomRedDot extends androidx.appcompat.widget.AppCompatTextView {

    private Paint dotPaint;
    private Paint textPaint;
    private Rect textRect;
    private float cornerRadius = 10f;
    private final int DEFAULT_COLOR_DOT = Color.RED;
    private final int DEFAULT_COLOR_TEXT = Color.WHITE;
    private int mDotColor = DEFAULT_COLOR_DOT;
    private int mTextColor = DEFAULT_COLOR_TEXT;
    private final float DEFAULT_TEXT_SIZE = 10f;
    private float mTextSize = DEFAULT_TEXT_SIZE;


    public CustomRedDot(@NonNull Context context) {
        this(context, null);
    }

    public CustomRedDot(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRedDot(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dotPaint = new Paint();
        dotPaint.setAntiAlias(true);
        dotPaint.setColor(mDotColor);
        dotPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(mTextColor);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);
        textPaint.setTextSize(ScreenUtils.sp2px(mTextSize));
        textRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 测量文本宽度和高度
        textPaint.getTextBounds(getText().toString(), 0, getText().length(), textRect);
        float textWidth = textRect.width();
        float textHeight = textRect.height();

        // 根据文本宽度和其他需求设置视图的宽度和高度
        int width = (int) (textWidth + getPaddingLeft() + getPaddingRight());
        int height = (int) (textHeight + getPaddingTop() + getPaddingBottom());


        // 确保宽度和高度至少为圆角矩形的最小尺寸
        int minWidth = (int) (2 * cornerRadius + getPaddingLeft() + getPaddingRight());
        int minHeight = (int) (2 * cornerRadius + getPaddingTop() + getPaddingBottom());
        setMeasuredDimension(Math.max(width, minWidth), Math.max(height, minHeight));
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 计算圆角矩形的边界
        int radius = getHeight() / 2;
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), radius, radius, dotPaint);

        // 绘制文本
        textPaint.getTextBounds(getText().toString(), 0, getText().length(), textRect);
        float textHeight = textRect.height();

        int x = getWidth() / 2;
        float y = (getHeight() + textHeight) / 2 - textRect.bottom;

        canvas.drawText(getText().toString(), x, y, textPaint);
    }

    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }

    public void setmDotColor(int mDotColor) {
        this.mDotColor = mDotColor;
        updateDot();
    }

    private void updateDot() {
        if (textPaint == null) {
            textPaint = new Paint();
            textPaint.setAntiAlias(true);
            textPaint.setFakeBoldText(true);
        }
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(ScreenUtils.sp2px(mTextSize));

        if (dotPaint == null) {
            dotPaint = new Paint();
            dotPaint.setAntiAlias(true);
            dotPaint.setStyle(Paint.Style.FILL);
        }
        dotPaint.setColor(mDotColor);
        invalidate();
    }

}