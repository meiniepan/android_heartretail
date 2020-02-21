package com.dengyun.baselibrary.base;

/**
 * @titile
 * @desc Created by seven on 2018/2/27.
 */

public class ApiBean<T>{
//    private static final long serialVersionUID = 5213230387175987834L;

    public String result;
    public String message;
    public T data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiBean{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
