package com.sobot.chat.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * @Title 整理客服的图片加载器
 * @Author: zhoubo
 * @CreateDate: 2019-05-30 16:45
 */
public class SobotImageLoader {

    public void displayImage(Context context, final ImageView imageView, final String path, @DrawableRes int loadingResId, @DrawableRes int failResId, int width, int height, final SobotDisplayImageListener listener) {
        RequestOptions options = new RequestOptions().placeholder(loadingResId).error(failResId).centerCrop();
        if (width != 0 || height != 0) {
            options.override(width, height);
        }
        RequestBuilder<Bitmap> builder = Glide.with(context)
                .asBitmap()
                .load(path)
                .apply(options);
        builder.listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                if (listener != null) {
                    listener.onSuccess(imageView, path);
                }
                return false;
            }
        }).into(imageView);
    }


    public void displayImage(Context context, final ImageView imageView, @DrawableRes int targetResId, @DrawableRes int loadingResId, @DrawableRes int failResId, int width, int height, final SobotDisplayImageListener listener) {
        RequestOptions options = new RequestOptions().placeholder(loadingResId).error(failResId).centerCrop();
        if (width != 0 || height != 0) {
            options.override(width, height);
        }
        RequestBuilder<Bitmap> builder = Glide.with(context)
                .asBitmap()
                .load(targetResId)
                .apply(options);
        builder.listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                if (listener != null) {
                    listener.onSuccess(imageView, "");
                }
                return false;
            }
        }).into(imageView);
    }

    public interface SobotDisplayImageListener {
        void onSuccess(View view, String path);
    }
}
