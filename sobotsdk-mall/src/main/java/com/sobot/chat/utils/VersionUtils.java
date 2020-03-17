package com.sobot.chat.utils;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

public class VersionUtils {


	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void setBackground(Drawable imagebakground,TextView view){
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
	    	view.setBackground(imagebakground);
	    } else {
	    	view.setBackgroundDrawable(imagebakground);
	    }
	}
	
	@SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static void setBackground(Drawable imagebakground,ImageView view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(imagebakground);
        } else {
            view.setBackgroundDrawable(imagebakground);
        }
    }
}