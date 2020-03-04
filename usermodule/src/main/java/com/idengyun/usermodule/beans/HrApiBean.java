package com.idengyun.usermodule.beans;

/**
 * @titile
 * @desc Created by seven on 2018/2/27.
 */

public class HrApiBean<T>{
//    private static final long serialVersionUID = 5213230387175987834L;

    public String code;
    public String msg;
    public T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HrApiBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
