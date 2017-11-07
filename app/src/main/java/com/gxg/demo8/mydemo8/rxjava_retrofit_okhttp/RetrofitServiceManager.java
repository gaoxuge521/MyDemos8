package com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp;

import android.util.Log;

import com.google.gson.Gson;
import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.bean.MovieSubject;
//import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.exception.ApiException;
import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.result.HttpResult;
import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.service.MovieService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：Administrator on 2017/11/6 14:34
 * 邮箱：android_gaoxuge@163.com
 */
public class RetrofitServiceManager {

    public static final int DEFAULE_TIME_OUT = 5; //超时时间5s
    public static final int DEFAULT_READ_OUT = 10;

    public static final String BASEURL = "https://api.douban.com/v2/movie/";

    private Retrofit retrofit;
    private MovieService movieService;

    public static class SingletonHolder {
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    public static RetrofitServiceManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitServiceManager() {
        init();
    }


    /**
     * 进行网络请求，获取数据
     * @param subscriber
     * @param request
     */
    public void getMovieData(Subscriber<MovieSubject> subscriber, Map<String, Object> request){
        //统一处理数据，可以通过HttpResultFunc来实现
//        Observable<MovieSubject> map = movieService.rxPostData(request).map(new HttpResultFunc<MovieSubject>());
        Observable<MovieSubject> map = movieService.rxPostData(request);
        toSubscribe(map,subscriber);
    }



    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
//            if (!httpResult.getStatus().equals("1")) {
//                throw new ApiException(httpResult.getStatus(), httpResult.getMsg());
//            }

            return httpResult.getData() == null ? null : httpResult.getData();
        }
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    private void init() {
        //创建httpclient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULE_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_OUT,TimeUnit.SECONDS);//写操作的超级时间
        builder.readTimeout(DEFAULT_READ_OUT,TimeUnit.SECONDS);//读取的超时时间

        /**
         * 在实际项目中，每个接口都有一些基本的相同的参数，
         * 我们称之为公共参数，比如：userId、userToken、userName,deviceId等等，
         * 我们不必要，每个接口都去写，这样就太麻烦了，因此我们可以写一个拦截器，
         * 在拦截器里面拦截请求，为每个请求都添加相同的公共参数
         */
        //添加公共参数拦截器
//        HttpCommonInterceptor httpCommonInterceptor = new HttpCommonInterceptor.Builder()
//                .addHeaderParams("userId","")
//                .addHeaderParams("userToken","")
//                .build();
//        builder.addInterceptor(httpCommonInterceptor);


        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASEURL)
                .build();


        movieService = creat(MovieService.class);
        Log.e("sss", "init: 111111111111" );
    }

    /**
     * 创建对应的service
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T creat(Class<T> service) {
        return retrofit.create(service);
    }
}
