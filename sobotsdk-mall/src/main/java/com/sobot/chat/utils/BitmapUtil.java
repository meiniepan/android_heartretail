package com.sobot.chat.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sobot.chat.imageloader.SobotImageLoader;

public class BitmapUtil {

    private static SobotImageLoader sImageLoader;

    private static final SobotImageLoader getImageLoader() {
        if (sImageLoader == null) {
            synchronized (BitmapUtil.class) {
                if (sImageLoader == null) {
                    sImageLoader = new SobotImageLoader();
                }
            }
        }
        return sImageLoader;
    }

    private static final boolean isClassExists(String classFullName) {
        try {
            Class.forName(classFullName);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void display(Context context, String url,
                               ImageView imageView, int defaultPic, int error) {
        getImageLoader().displayImage(context, imageView, url, defaultPic, error, imageView.getWidth(), imageView.getHeight(), null);
    }

    @SuppressWarnings("deprecation")
    public static void display(Context context, String url, ImageView imageView) {

        if (!TextUtils.isEmpty(url) && !url.startsWith("http")) {
            url = "file://" + url;
        }

        getImageLoader().displayImage(context, imageView, url, ResourceUtils.getIdByName(context, "drawable",
                "sobot_default_pic"), ResourceUtils.getIdByName(context, "drawable",
                "sobot_default_pic_err"), imageView.getWidth(), imageView.getHeight(), null);
    }

    public static void display(Context context, int resourceId, ImageView imageView) {
        getImageLoader().displayImage(context, imageView, resourceId, 0, 0, imageView.getWidth(), imageView.getHeight(), null);
    }

    /**
     * 加载头像的方法
     *
     * @param context
     * @param url       头像的路径
     * @param imageView
     * @param defId     默认图片的id
     */
    public static void displayRound(Context context, String url, ImageView imageView, int defId) {
        getImageLoader().displayImage(context, imageView, url, defId, defId, imageView.getWidth(), imageView.getHeight(), null);
    }

    /**
     * 加载头像的方法
     *
     * @param context
     * @param resId     头像的资源ID
     * @param imageView
     * @param defId     默认图片的id
     */
    public static void displayRound(Context context, int resId, ImageView imageView, int
            defId) {
        getImageLoader().displayImage(context, imageView, resId, defId, defId, imageView.getWidth(), imageView.getHeight(), null);
    }

    @SuppressWarnings("deprecation")
    public static Bitmap compress(String filePath, Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 设置后decode图片不会返回一个bitmap对象，但是会将图片的信息封装到Options中
        BitmapFactory.decodeFile(filePath, options);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算采样大小
     *
     * @param options   选项
     * @param reqWidth  最大宽度
     * @param reqHeight 最大高度
     * @return 采样大小
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}