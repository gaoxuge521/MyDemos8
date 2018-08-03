package com.gxg.demo8.mydemo8.httpUtils;

import android.support.annotation.NonNull;

import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.exception.ApiException;
import com.socks.library.KLog;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：Administrator on 2018/6/25 09:29
 * 邮箱：android_gaoxuge@163.com
 */
public class HttpUtils {
    public static final int DEFAULE_TIME_OUT = 5; //超时时间5s
    public static final int DEFAULT_READ_OUT = 10;
    // Retrofit2 的baseUlr 必须以 /（斜线） 结束，不然会抛出一个IllegalArgumentException,
    public static final String BASEURL = "https://api.douban.com/v2/movie/";
    private Retrofit retrofit;
    private StringService stringService;
    /**
     * get请求
     */
    public void requestGet(@NonNull String url, Map<String,Object> map, Subscriber<String> subscriber){
        Observable<String> observable = stringService.requestGet(url, map).map(new HttpResultFunc());
        toSubscriber(observable,subscriber);
    }

    /**
     * post请求
     */
    public void requestPost(@NonNull String url, Map<String,Object> map, Subscriber<String> subscriber){
        Observable<String> observable = stringService.requestPost(url, map).map(new HttpResultFunc());
        toSubscriber(observable,subscriber);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     */
    private class HttpResultFunc implements Func1<Response, String> {
        @Override
        public String call(Response response) {
            KLog.e("sss  "+response.code());
            if(response.code()==200){
                return response.body().toString();
            }else{
                throw new ApiException(response.code()+"", response.message());
            }

        }
    }
    private<T> void toSubscriber(Observable<T> o,Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public static class HttpHolder {
        private static final HttpUtils INSTANCE = new HttpUtils();
    }

    public static HttpUtils getInstance() {
        return HttpHolder.INSTANCE;
    }

    private HttpUtils() {
        init();
    }

    private void init() {
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
                .baseUrl(BASEURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(builder.build())
                .build();
        stringService = creat(StringService.class);
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
