package com.dengyun.baselibrary.base;

/**
 * @titile
 * @desc Created by seven on 2018/2/27.
 */

public class ApiSimpleBean{

//    private static final long serialVersionUID = -1477609349345966116L;

    public String result;
    public String message;

    public ApiBean toApiBean() {
        ApiBean apiBean = new ApiBean();
        apiBean.result = result;
        apiBean.message = message;
        return apiBean;
    }
}
