package com.lmz.widght;

import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * describe:
 * Date:2024/12/14
 * Author:lmz
 */
public class BindingAdapters {


    @BindingAdapter(value = {"title", "colorId", "defaultTitle"}, requireAll = false)
    public static void setViewText(@NonNull TextView textView, String title, Integer colorId, String defaultTitle) {
        String finalTitle = title == null ? defaultTitle : title;
        textView.setText(finalTitle);
        if (colorId != null) {
            textView.setTextColor(colorId);
        }
    }


    @BindingAdapter(value = {"imageUrl", "defaultRes"}, requireAll = false)
    public static void setImageViewUrl(@NonNull ImageView imageView, String imageUrl, int defaultRes) {
        Glide.with(imageView.getContext()).load(imageUrl).placeholder(defaultRes).into(imageView);
    }

}
