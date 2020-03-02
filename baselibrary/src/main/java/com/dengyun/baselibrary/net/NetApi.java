package com.dengyun.baselibrary.net;

import com.dengyun.baselibrary.config.AppConfig;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.net.deal.DealParamsUtil;
import com.dengyun.baselibrary.net.exception.NoNetException;
import com.dengyun.baselibrary.net.rx.RxUtils;
import com.dengyun.baselibrary.net.util.NetworkUtils;
import com.dengyun.baselibrary.utils.AppLogUtil;
import com.dengyun.baselibrary.utils.ObjectUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.Response;

/**
 * getData          普通post网络请求，传泛型类
 * getString        普通post网络请求，不传泛型，回调json
 * getStringRX      rx+okgo的post请求，不传泛型，回调json
 * getDataRX        rx+okgo的post请求，传泛型类
 * displayImage     网络图片设置方法
 * upFile           okgo的上传方法
 *
 * @titile 网络工具类
 * @desc Created by seven on 2018/2/27.
 */

public class NetApi {

    /**
     * 网络请求封装，不传泛型，回调返回Json，普通okgo形式请求
     */
    public static void getString(NetOption netOption, JsonCallback<String> jsonCallback) {
        getData(netOption,jsonCallback);
    }

    public static Response getStringSync(NetOption netOption) {
        return getDataSync(netOption);
    }

    public static void getFormString(NetOption netOption, JsonCallback<String> jsonCallback) {
        getFormData(netOption,jsonCallback);
    }

    public static Response getFormStringSync(NetOption netOption)  {
        return getFormDataSync(netOption);
    }

    public static <T> void getData(NetOption netOption, JsonCallback<T> jsonCallback){
        getData(RequestMethod.POST_JSON,netOption,jsonCallback);
    }
    public static  Response getDataSync(NetOption netOption)  {
        return getDataSync(RequestMethod.POST_JSON,netOption);
    }

    /**
     * 网络请求封装，传泛型类，回调返回泛型类普通okgo形式请求
     */
    public static <T> void getFormData(NetOption netOption, JsonCallback<T> jsonCallback) {
        getData(RequestMethod.POST_FORM,netOption,jsonCallback);
    }

    public static <T> Response getFormDataSync(NetOption netOption) {
        return getDataSync(RequestMethod.POST_FORM,netOption);
    }

    /**
     * 网络请求封装，传泛型类，回调返回泛型类普通okgo形式请求
     * @param requestMethod 请求方式
     * @param netOption     配置参数类
     * @param jsonCallback  回调
     * @param <T>
     */
    public static <T> void getData(@RequestMethod int requestMethod,NetOption netOption, JsonCallback<T> jsonCallback) {
        if (!NetworkUtils.isConnected()) {
            jsonCallback.onNoNet();
            return;
        }
        String jsonParams = DealParamsUtil.getDealParams(netOption);
        pringLog(netOption.getUrl(),jsonParams);

        if(requestMethod == RequestMethod.GET){
            OkGo.<T>get(netOption.getUrl())
                    .tag(getRequestTag(netOption))
                    .headers(netOption.getHeaders())
                    .execute(jsonCallback);
        }else if(requestMethod == RequestMethod.POST_JSON){
            OkGo.<T>post(netOption.getUrl())
                    .tag(getRequestTag(netOption))
                    .headers(netOption.getHeaders())
                    .upJson(jsonParams)
                    .execute(jsonCallback);

        }else if(requestMethod == RequestMethod.POST_FORM){
            HttpParams httpParams = new HttpParams();
            httpParams.put(netOption.getParams());
            OkGo.<T>post(netOption.getUrl())
                    .tag(getRequestTag(netOption))
                    .headers(netOption.getHeaders())
                    .params(httpParams)
                    .execute(jsonCallback);
        }
    }

    /**
     * 网络请求封装 同步请求
     * @param requestMethod 请求方式
     * @param netOption     配置参数类
     * @param <T>
     */
    public static <T> Response getDataSync(@RequestMethod int requestMethod, NetOption netOption) {

        String jsonParams = DealParamsUtil.getDealParams(netOption);
        pringLog(netOption.getUrl(),jsonParams);

        if(requestMethod == RequestMethod.GET){
            try {
                return OkGo.<T>get(netOption.getUrl())
                        .tag(getRequestTag(netOption))
                        .headers(netOption.getHeaders())
                        .execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(requestMethod == RequestMethod.POST_JSON){
            try {
                return OkGo.<T>post(netOption.getUrl())
                        .tag(getRequestTag(netOption))
                        .headers(netOption.getHeaders())
                        .upJson(jsonParams)
                        .execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(requestMethod == RequestMethod.POST_FORM){
            HttpParams httpParams = new HttpParams();
            httpParams.put(netOption.getParams());
            try {
                return OkGo.<T>post(netOption.getUrl())
                        .tag(getRequestTag(netOption))
                        .headers(netOption.getHeaders())
                        .params(httpParams)
                        .execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @return 设置请求的tag，用于之后的取消请求，默认为当前请求的activity或Fragment的类名
     */
    public static String getRequestTag(NetOption netOption){
        //设置请求的key，用于之后的取消，默认为当前activity或fragment的类名
        String mKey = netOption.getUrl();
        if (null!= netOption.getTag()){
            mKey = netOption.getTag();
        }else {
            if (null != netOption.getFragment()) {
                mKey = ObjectUtils.getClassPath(netOption.getFragment());
            } else if (null != netOption.getActivity()) {
                mKey = ObjectUtils.getClassPath(netOption.getActivity());
            }
        }
        return mKey;
    }

    /**
     * 网络请求封装，不传泛型，使用rxjava形式返回observable
     */
    public static Observable<String> getStringRX(NetOption netOption) {
        return getDataRX(netOption);
    }

    public static Observable<String> getFormStringRX(NetOption netOption) {
        return getFormDataRX(netOption);
    }

    public static <T> Observable<T> getDataRX(NetOption netOption){
        return getDataRX(RequestMethod.POST_JSON,netOption);
    }

    public static <T> Observable<T> getFormDataRX(NetOption netOption){
        return getDataRX(RequestMethod.POST_FORM,netOption);
    }

    /**
     * 网络请求封装，传泛型ApiBean类，使用rxjava形式返回observable
     * @param requestMethod 请求方式
     * @param netOption
     * @return
     * @param <T>
     */
    public static <T> Observable<T> getDataRX(@RequestMethod int requestMethod, NetOption netOption) {
        if (!NetworkUtils.isConnected()) {
            return Observable.error(new NoNetException());
        }
        String jsonParams = DealParamsUtil.getDealParams(netOption);
        pringLog(netOption.getUrl(),jsonParams);
        return RxUtils.requestPost(requestMethod,netOption,jsonParams);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 上传的api方法
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 上传的请求，传泛型，返回泛型的内容
     *
     * @param <T>  泛型
     */
    public static <T> void upFileData(NetOption netOption, File file, JsonCallback<T> jsonCallback) {
        if (!NetworkUtils.isConnected()) {
            jsonCallback.onNoNet();
            return;
        }

        /*String jsonParams = DealParamsUtil.getDealParams(netOption);
        if (AppConfig.isDebug) {
            Logger.d(netOption.getUrl());
            Logger.d(jsonParams);
        }
        HttpParams httpParams = new HttpParams();
        httpParams.put(netOption.getParams());*/

        OkGo.<T>post(netOption.getUrl())
                .tag(getRequestTag(netOption))
                .headers(netOption.getHeaders())
                .params("file",file)
                .execute(jsonCallback);
    }

    /**
     * @param tag 取消某个tag的请求
     */
    public static void cancelTag(Object tag){
        OkGo.getInstance().cancelTag(tag);
    }

    /**
     * 取消所有请求
     */
    public static void cancelAll(){
        OkGo.getInstance().cancelAll();
    }

    private static void pringLog(String url,String jsonParams){
        if(!AppConfig.isDebug)return;
        Logger.t("real-request").d(url);
        Logger.t("real-request").d(jsonParams);
    }
}