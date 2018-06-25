package com.gxg.demo8.mydemo8.httpUtils;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 作者：Administrator on 2018/6/25 09:44
 * 邮箱：android_gaoxuge@163.com
 */
public interface StringService {
    @GET
    Observable<String> requestGet(@Url String url,@QueryMap Map<String, Object> map);
}
