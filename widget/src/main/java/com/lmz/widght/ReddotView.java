package com.lmz.widght;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.lmz.widget.R;

/**
 * 2025/2/20
 * Administrator
 */
public class ReddotView extends androidx.appcompat.widget.AppCompatTextView{
    public ReddotView(@NonNull Context context) {
        super(context);
        init();
    }

    public ReddotView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReddotView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setPadding(30,0,30,0);
        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reddot_layout));
    }
}
