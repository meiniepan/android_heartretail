/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dengyun.baselibrary.net.rx;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.net.util.JsonConvert;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;
import com.lzy.okrx2.adapter.ObservableBody;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2017/5/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class RxUtils {

    public static <T> Observable<T> requestPost(@RequestMethod int requestMethod, NetOption netOption, String dealJsonParams) {
        PostRequest<T>  postRequest = OkGo.<T>post(netOption.getUrl())
                .tag(NetApi.getRequestTag(netOption))
                .headers(netOption.getHeaders());
        if(requestMethod == RequestMethod.GET){
            // TODO: 2019-05-15 没有完善get请求
        }else if(requestMethod == RequestMethod.POST_JSON){
            postRequest.upJson(dealJsonParams);
        }else if(requestMethod == RequestMethod.POST_FORM){
            HttpParams httpParams = new HttpParams();
            httpParams.put(netOption.getParams());
            postRequest.params(httpParams);
        }
        postRequest.converter(new JsonConvert<T>(netOption));
        return postRequest.adapt(new ObservableBody<T>());

    }

}
