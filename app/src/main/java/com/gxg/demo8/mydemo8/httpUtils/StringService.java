package com.gxg.demo8.mydemo8.httpUtils;

import java.util.Map;

import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 作者：Administrator on 2018/6/25 09:44
 * 邮箱：android_gaoxuge@163.com
 */
public interface StringService {
    /**
     * get请求
     */
    @GET
    Observable<Response<String>> requestGet(@Url String url, @QueryMap Map<String, Object> map);

    /**
     * post请求
     */
    @FormUrlEncoded
    @POST
    Observable<Response<String>> requestPost(@Url String url,@FieldMap Map<String, Object> map);
}
