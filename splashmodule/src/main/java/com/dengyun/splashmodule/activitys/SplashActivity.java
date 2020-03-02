package com.dengyun.splashmodule.activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.constants.ProjectType;
import com.dengyun.baselibrary.net.rx.RxObserver;
import com.dengyun.baselibrary.net.rx.RxSchedulers;
import com.dengyun.baselibrary.spconstants.SpUserConstants;
import com.dengyun.baselibrary.utils.GsonConvertUtil;
import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.Utils;
import com.dengyun.splashmodule.beans.MainConfig;
import com.dengyun.baselibrary.spconstants.SpMainConfigConstants;
import com.dengyun.splashmodule.beans.MainUrlConstants;
import com.dengyun.splashmodule.listeners.OnLoadMainUrlsListener;
import com.dengyun.splashmodule.utils.LocalAdInfoUtils;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.Iterator;

public class SplashActivity extends BaseSplashActivity {
    @Override
    public void loadMainAndAdUrls(final OnLoadMainUrlsListener onLoadMainUrlsListener) {
        Type type = new TypeToken<MainConfig>() {}.getType();
        NetOption netOption = NetOption.newBuilder(MainUrlConstants.MAINHTTP)
                .activity(this)
                .type(type)
                .isEncrypt(false)
                .projectType(ProjectType.IDENGYUN_FZX)
                .params("nowu_id", SpUserConstants.getUserId())
                .params("user_token",SpUserConstants.getUserToken())
                .params("company_id",SpUserConstants.getComPanyId())
                .isShowDialog(false)
                .build();
        NetApi.<MainConfig>getDataRX(netOption)
                .compose(RxSchedulers.<MainConfig>io_main())
                .subscribe(new RxObserver<MainConfig>(netOption) {
                    @Override
                    public void onNext(MainConfig mainConfig) {
                        //主配置请求成功后处理广告url
                        onLoadMainUrlsListener.setAdUrl(mainConfig.getAdvertising().ad_pic);
                        //保存本地MainConfig配置url
                        saveLocalMainconfig(mainConfig);
                        //每次进入判断是否提示登陆送云币
                        dealGoldWithNetSuccess(mainConfig.getData().gold_type,mainConfig.getData().gold_num);
                    }

                    @Override
                    public void onNoNet(Throwable e) {
                        super.onNoNet(e);
                        //没联网的操作
                    }
                });
    }

    private void saveLocalMainconfig( final MainConfig mainConfig){
        RxSchedulers.doOnIOThread(this, new RxSchedulers.IOTask() {
            @Override
            public void doOnIOThread() {
                //主配置接口信息
                SharedPreferencesUtil.saveDataBean(Utils.getApp(), SpMainConfigConstants.spFileName, mainConfig.getData());
                //广告信息
                LocalAdInfoUtils.saveAdInfo(mainConfig.getAdvertising().ad_pic,mainConfig.getAdvertising().ad_url);
                //删除之前的广告图本地存储，过几个版本之后删除
                SharedPreferencesUtil.removeAll(Utils.getApp(),"advertising");

                //新零售的存储主配置信息，待删除
                try {
                    SharedPreferences sp = activity.getSharedPreferences("nr_main_config", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();

                    JSONObject data = new JSONObject(GsonConvertUtil.toJson(mainConfig)).optJSONObject("data");
                    if (data != null) {
                        Iterator<String> keys = data.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            String value = data.optString(key);
                            String oldValue = sp.getString(key, "");
                            if (!oldValue.equals(value)) edit.putString(key, value);
                        }
                    }
                    edit.apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void dealGoldWithNetSuccess(String gold_type, int gold_num) {
        if (!TextUtils.isEmpty(gold_type) && gold_num > 0) {
            ToastUtils.showShort("" + gold_type + " +" + gold_num + "云币");
        }
    }


}

