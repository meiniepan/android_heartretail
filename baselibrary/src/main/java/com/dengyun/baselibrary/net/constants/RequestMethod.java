package com.dengyun.baselibrary.net.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Title 请求方式
 * @Desc: get postJson post表单
 * @Author: zhoubo
 * @CreateDate: 2019-05-15 17:49
 */
@IntDef({RequestMethod.GET,RequestMethod.POST_JSON,RequestMethod.POST_FORM})
@Retention(RetentionPolicy.SOURCE)
public @interface RequestMethod {
    int GET = 0;
    int POST_JSON = 1;
    int POST_FORM = 2;
}
