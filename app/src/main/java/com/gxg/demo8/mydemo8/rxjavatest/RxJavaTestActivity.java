package com.gxg.demo8.mydemo8.rxjavatest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gxg.demo8.mydemo8.R;
import com.gxg.demo8.mydemo8.httpUtils.HttpUtils;
import com.gxg.demo8.mydemo8.httpUtils.ProSubscriber;
import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.bean.MovieSubject;
import com.socks.library.KLog;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：Administrator on 2018/6/27 13:31
 * 邮箱：android_gaoxuge@163.com
 */
public class RxJavaTestActivity extends AppCompatActivity {
    @Bind(R.id.tv_getjson)
    TextView tvGetjson;
    @Bind(R.id.tv_getjson2)
    TextView tvGetjson2;
    @Bind(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_test);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.tv_getjson, R.id.tv_getjson2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_getjson:
                getjsonbyRx();
                break;
            case R.id.tv_getjson2:
                Map<String, Object> map = new HashMap<>();
                map.put("start","1");
                map.put("count","20");
                HttpUtils.getInstance().requestGet("/v2/movie/top250", map, new ProSubscriber<String>(RxJavaTestActivity.this,true){
                    @Override
                    public void onNext(String o) {
                        getImgByUrl(o);
                    }
                } );

                break;
        }
    }

    private void getImgByUrl(final String s) {

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                subscriber.onNext(s);
            }
        }).map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String s) {
                MovieSubject mMovieSubject =   new Gson().fromJson(s,MovieSubject.class);
                KLog.e("sss  "+mMovieSubject.getTitle());
                Bitmap bitmap = null;
                try {
                    URL imageurl = new URL(mMovieSubject.getSubjects().get(0).getImages().getLarge());
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }

                return bitmap;
            }
        })
                .filter(new Func1<Bitmap, Boolean>() {
                    @Override
                    public Boolean call(Bitmap bitmap) {
                        return bitmap!=null;//筛选掉空文件
                    }
                }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        KLog.e("sss  "+bitmap);
                        if(bitmap!=null){
                            img.setImageBitmap(bitmap);
                        }

                    }
                });
    }

    /**
     * 读取json转成对象
     */
    private void getjsonbyRx() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                KLog.e("sss   "+Thread.currentThread().getName());
                String json = new GetJsonDataUtil().getJson(RxJavaTestActivity.this, "citys.json");
                subscriber.onNext(json);
                subscriber.onCompleted();
            }
        })
                .map(new Func1<String, List<CityListBean>>() {
                    @Override
                    public List<CityListBean> call(String s) {
                        KLog.e("sss   "+Thread.currentThread().getName());
                        KLog.e("sss  "+s.trim());
                        List<CityListBean> objectList = GsonUtil.getObjectList(s, CityListBean.class);
                        KLog.e("sss  "+objectList.size());
                        return objectList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CityListBean>>() {
                    @Override
                    public void onCompleted() {
                        KLog.e("sss   "+Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e("sss   "+Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(List<CityListBean> cityListBeen) {
                        KLog.e("sss   "+Thread.currentThread().getName());
                        KLog.e("sss  "+cityListBeen.size());
                    }
                });

    }
}
