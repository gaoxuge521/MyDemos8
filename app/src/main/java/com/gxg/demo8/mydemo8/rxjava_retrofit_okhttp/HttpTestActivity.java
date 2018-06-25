package com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gxg.demo8.mydemo8.R;
import com.gxg.demo8.mydemo8.httpUtils.HttpUtils;
import com.gxg.demo8.mydemo8.httpUtils.ProSubscriber;
import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.bean.MovieSubject;
import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.service.MovieService;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;

public class HttpTestActivity extends AppCompatActivity {

    @Bind(R.id.rv)
    RecyclerView rv;

    public static final String BASEURL = "https://api.douban.com/v2/movie/";

    Retrofit retrofit;
    private MovieSubject mMovieSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);
        ButterKnife.bind(this);



        getDataByError();

//        getDataByNew();

//配合rxjava的retrofit
//        rxPostData();


        //配合rxjava的retrofit
//        rxGetData();


        //get传参
//        getData();

        //get传map
//        getDataByMap();

        //post传参
//        postData();
        //post传map
//        postDataByMap();
    }

    private void getDataByError() {
        Map<String, Object> map = new HashMap<>();
        map.put("start","1");
        map.put("count","20");
        HttpUtils.getInstance().requestGet("/v2/movie/top250", map, new ProSubscriber<String>(HttpTestActivity.this,true) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                KLog.e("sss   ", "onError: "+e.getMessage());
            }

            @Override
            public void onNext(String o) {
                super.onNext(o);
                KLog.json(o);
                mMovieSubject =   new Gson().fromJson(o,MovieSubject.class);
                setData();
                KLog.e("sss   "+o);
            }
        });
//        RetrofitServiceManager.getInstance().getMovieData(new ProgressSubscriber<MovieSubject>(HttpTestActivity.this,true) {
//            @Override
//            public void onError(ExceptionHandle.ResponeThrowable throwable) {
//                int statusCode = throwable.code;
//                String message = throwable.message;
//                Log.e("sss", "onError: "+message+"   "+statusCode );
//                ToastByStr("请求失败"+message+"   "+statusCode);
//            }
//
//            @Override
//            public void onNext(MovieSubject movieSubject) {
//                super.onNext(movieSubject);
//                ToastByStr("请求成功"+movieSubject.getTitle());
//                mMovieSubject = movieSubject;
//                setData();
//            }
//
//            @Override
//            public void onCompleted() {
//                ToastByStr("请求结束");
//                super.onCompleted();
//            }
//        },map);
    }

    private void getDataByNew() {
        Map<String, Object> map = new HashMap<>();
        map.put("start","1");
        map.put("count","20");
        RetrofitServiceManager.getInstance().getMovieData(new Subscriber<MovieSubject>() {
            @Override
            public void onCompleted() {

                ToastByStr("请求结束");
            }

            @Override
            public void onError(Throwable throwable) {
                if(throwable instanceof HttpException){
                    //获取对应statusCode和Message
                    HttpException exception = (HttpException)throwable;
                    String message = exception.response().message();
                    int code = exception.response().code();
                    ToastByStr("请求失败"+message+"   "+code);
                }

                String message = throwable.getMessage();

                ToastByStr("请求失败"+throwable.toString()+"   ");

            }

            @Override
            public void onNext(MovieSubject movieSubject) {
                ToastByStr("请求成功"+movieSubject.getTitle());
                mMovieSubject = movieSubject;
                setData();
            }
        }, map);
    }

    public int DEFAULT_TIME_OUT = 5000;

    private void initData() {
        //添加okhttp配置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

        //添加公共参数拦截器
        // 添加公共参数拦截器
//        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
//                .addHeaderParam("userName","")//添加公共参数
//                .addHeaderParam("device","")
//                .build();
//
//        builder.addInterceptor(basicParamsInterceptor);


        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
    }

//    private void rxPostData() {
//
//        MovieService movieService = retrofit.create(MovieService.class);
//        Map<String,Object> map = new HashMap();
//        map.put("start","1");
//        map.put("count","20");
//        Subscription subscribe = movieService.rxPostData(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<MovieSubject>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("sss", "onError: "+e.toString());
//                    }
//
//                    @Override
//                    public void onNext(MovieSubject movieSubject) {
//                        mMovieSubject = movieSubject;
//                        setData();
//                    }
//                });
//    }

    /**
     *  配合rxjava的retrofit
     */
//    private void rxGetData() {
//        MovieService movieService = retrofit.create(MovieService.class);
//        Map<String,String> map = new HashMap();
//        map.put("start","1");
//        map.put("count","20");
//        Subscription subscription = movieService.rxGetData(map)
//                .subscribeOn(Schedulers.io())//改变调用它之前代码的线程
//                .observeOn(AndroidSchedulers.mainThread())//改变调用它之后代码的线程
//                .subscribe(new Subscriber<MovieSubject>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("sss", "onError: "+e.toString());
//                    }
//
//                    @Override
//                    public void onNext(MovieSubject movieSubject) {
//                        mMovieSubject = movieSubject;
//                        setData();
//                    }
//
//
//                });
//
//    }

    /**
     * 给recycleview设置数据
     */
    private void setData() {
        if (mMovieSubject != null) {
            rv.setLayoutManager(new LinearLayoutManager(this));
            SubjectAdapter subjectAdapter = new SubjectAdapter(mMovieSubject.getSubjects());
            rv.setAdapter(subjectAdapter);

        }
    }

    /**
     * 只有retrofit
     * get请求传参数
     */
    private void postData() {

        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieSubject> call = movieService.getTop250ByPost(0, 20);
        //异步
        call.enqueue(new Callback<MovieSubject>() {
            @Override
            public void onResponse(Call<MovieSubject> call, Response<MovieSubject> response) {
                mMovieSubject = response.body();
                ToastByStr(mMovieSubject.getTitle());
                setData();
            }

            @Override
            public void onFailure(Call<MovieSubject> call, Throwable t) {
                ToastByStr("请求失败" + t.toString());
            }
        });

    }
    /**
     * 只有retrofit
     * post请求传map
     */
//    private void postDataByMap() {
//        MovieService movieService = retrofit.create(MovieService.class);
//        Map<String,String> map = new HashMap();
//        map.put("start","1");
//        map.put("count","20");
//        Call<MovieSubject> call = movieService.getTop250ByPostOrMap(map);
//        call.enqueue(new Callback<MovieSubject>() {
//            @Override
//            public void onResponse(Call<MovieSubject> call, Response<MovieSubject> response) {
//                mMovieSubject = response.body();
//                ToastByStr(mMovieSubject.getTitle());
//
//                setData();
//            }
//
//            @Override
//            public void onFailure(Call<MovieSubject> call, Throwable t) {
//                ToastByStr("请求失败"+t.toString());
//            }
//        });
//    }

    /**
     * 只有retrofit
     * get请求传map
     */
//    private void getDataByMap() {
//        MovieService movieService = retrofit.create(MovieService.class);
//        Map<String,String> map = new HashMap();
//        map.put("start","1");
//        map.put("count","20");
//        Call<MovieSubject> call = movieService.getTop250ByMap(map);
//        call.enqueue(new Callback<MovieSubject>() {
//            @Override
//            public void onResponse(Call<MovieSubject> call, Response<MovieSubject> response) {
//                mMovieSubject = response.body();
//                ToastByStr(mMovieSubject.getTitle());
//
//                setData();
//            }
//
//            @Override
//            public void onFailure(Call<MovieSubject> call, Throwable t) {
//                ToastByStr("请求失败"+t.toString());
//            }
//        });
//    }


    /**
     * 只有retrofit
     * get请求传参数
     */
    private void getData() {

        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieSubject> call = movieService.getTop250(0, 20);
        //异步
        call.enqueue(new Callback<MovieSubject>() {
            @Override
            public void onResponse(Call<MovieSubject> call, Response<MovieSubject> response) {
                mMovieSubject = response.body();
                ToastByStr(mMovieSubject.getTitle());
                setData();
            }

            @Override
            public void onFailure(Call<MovieSubject> call, Throwable t) {
                ToastByStr("请求失败" + t.toString());
            }
        });

    }

    public void ToastByStr(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
