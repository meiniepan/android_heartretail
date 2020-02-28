package com.idengyun.heartretail;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.utils.GsonConvertUtil;
import com.dengyun.baselibrary.utils.encode.EncryptUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author aLang
 */
public final class SystemUIHelper {
    public static String createBody(Map<String, Object> bodyMap) throws JSONException {
        String parameters = new JSONObject(bodyMap).toString();

        // 加密字段拼接在请求json前后，得md5值
        String signMd5 = EncryptUtils.stringToMD5(parameters + "secret");

        JSONObject content = new JSONObject();
        content.put("jsonStr", parameters);
        content.put("sign", signMd5);

        return content.toString();
    }

    public static String createQuery(Map<String, Object> queryMap) {
        if (queryMap.isEmpty()) return "";

        StringBuilder builder = new StringBuilder();

        /* 真实请求参数 */
        Object[] array = queryMap.keySet().toArray();
        Arrays.sort(array);
        for (Object key : array) {
            Object value = queryMap.get(key);
            builder.append(key).append("=").append(value).append("&");
        }

        /* MD5加密参数 */
        String parameters = builder.substring(0, builder.length() - 1);
        String sign = EncryptUtils.stringToMD5(parameters + "secret");
        builder.append("sign").append("=").append(sign);

        return builder.toString();
    }

    //    class ImageUploader {
//        /**
//         * 获取本地图片对应的后台地址
//         */
//        fun toImageUrl(file: String, url: String): String = try {
//            JSONObject(uploadImage(file, url)).run { getString("file_url") }
//        } catch (e: Exception) {
//            ""
//        }
//
//        /**
//         * android上传文件到服务器
//         *
//         * @param pathname       需要上传的文件
//         * @param spec  请求的url
//         * @return 返回响应的内容
//         */
//        private fun uploadImage(pathname: String, spec: String): String? {
//            val file = File(pathname)
//            val boundary = UUID.randomUUID().toString()//边界标识 随机生成
//            val sb = StringBuilder()
//                    .append("--$boundary\r\n")
//                    .append("Content-Disposition:form-data;name=\"${file.name}\";filename=\"${file.name}\"\r\n")
//                    .append("Content-Type:${getMIMEType(file.name)}\r\n")
//                    .append("\r\n")
//            val header = sb.toString().toByteArray()
//            val footer = "\r\n--$boundary--\r\n".toByteArray()
//            val contentLength = header.size + file.length() + footer.size
//            return (URL(spec).openConnection() as? HttpURLConnection)?.run {
//                readTimeout = 60_000
//                connectTimeout = 60_000
//                doOutput = true
//                requestMethod = "POST"
//                setRequestProperty("Charset", "UTF-8")
//                setRequestProperty("Content-Type", "multipart/form-data;boundary=$boundary")
//                setRequestProperty("Content-Length", "$contentLength")
//                val content = FileInputStream(file).use { it.readBytes() }
//                DataOutputStream(outputStream).use { it.run { write(header); write(content); write(footer); flush() } }
//                when (responseCode) {
//                    200 -> BufferedReader(InputStreamReader(inputStream)).use { it.readText() }
//                else -> errorStream?.run { BufferedReader(InputStreamReader(this)).use { it.readText() } }
//                }
//            }
//        }
//
//        private fun getMIMEType(fileName: String) = URLConnection.getFileNameMap().getContentTypeFor(fileName)
//    }
    void get(String spec) throws IOException {
        URL url = new URL(spec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(30_000);
        connection.setReadTimeout(30_000);
        connection.setDoInput(false);
        InputStream inputStream = connection.getInputStream();
        new BufferedReader(new InputStreamReader(inputStream));

    }

    void bbbbbbb() throws IOException {
        URL url = new URL("");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(30_000);
        connection.setReadTimeout(30_000);
        connection.setDoInput(false);
        connection.getOutputStream();
        connection.getInputStream();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30L, TimeUnit.SECONDS)
                .readTimeout(30L, TimeUnit.SECONDS)
                .writeTimeout(30L, TimeUnit.SECONDS)
                .build();
    }

    public static void applySystemUI(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            new Theme(activity);
        }
    }

    private SystemUIHelper() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public static class Theme implements View.OnApplyWindowInsetsListener, View.OnSystemUiVisibilityChangeListener {
        private static final int STYLE_LIGHT = 0;
        private static final int STYLE_DARK = 1;

        private int style = STYLE_LIGHT;

        private Window window;
        private View decorView;
        private android.app.ActionBar actionBar;
        private android.support.v7.app.ActionBar supportActionBar;

        public Theme(@NonNull Activity activity) {
            this.window = activity.getWindow();
            this.decorView = window.getDecorView();
            this.actionBar = activity.getActionBar();
            if (activity instanceof AppCompatActivity) {
                this.supportActionBar = ((AppCompatActivity) activity).getSupportActionBar();
            }

            init();
        }

        private void init() {
            decorView.setOnApplyWindowInsetsListener(this);
            decorView.setOnSystemUiVisibilityChangeListener(this);

            hideActionBar();

            if (style == STYLE_LIGHT) {
                setLightTheme();
            }
        }

        @Override
        public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (style == STYLE_LIGHT) {
                    v.setBackground(new ColorDrawable(Color.WHITE));
                } else {
                    v.setBackground(new ColorDrawable(Color.BLACK));
                }
                int bottom = insets.getStableInsetBottom();
                v.setPadding(0, 0, 0, bottom);
                return insets.replaceSystemWindowInsets(0, 0, 0, 0);
            }
            return insets;
        }

        @Override
        public void onSystemUiVisibilityChange(int visibility) {
            /*if (decorView == null || window == null) return;
            if (style == STYLE_LIGHT) setLightTheme();*/
        }

        private void setLightTheme() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        private void hideActionBar() {
            if (actionBar != null) actionBar.hide();
            if (supportActionBar != null) supportActionBar.hide();
        }

    }
}
