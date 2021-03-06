package com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.result;

/**
 * 作者：Administrator on 2017/11/6 15:38
 * 邮箱：android_gaoxuge@163.com
 */
public class HttpResult<T> {

    private String status;
    private String msg;
    private T data;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
