package com.gxg.demo8.mydemo8.smartRefresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxg.demo8.mydemo8.R;
import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.RetrofitServiceManager;
import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.SubjectAdapter;
import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.bean.MovieSubject;
import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.exception.ExceptionHandle;
import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.subscriber.ProgressSubscriber;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SmartrefreshActivity extends AppCompatActivity {

    @Bind(R.id.rv_smart)
    RecyclerView rvSmart;
    @Bind(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private MovieSubject mMovieSubject;
    private SubjectAdapter subjectAdapter;
    private int start = 0;
    private ConvenientBanner banner;
    private List<MovieSubject.SubjectsBean.CastsBean> castsBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartrefresh);
        ButterKnife.bind(this);
        rvSmart.setLayoutManager(new LinearLayoutManager(this));
        subjectAdapter = new SubjectAdapter(new ArrayList<MovieSubject.SubjectsBean>());
        rvSmart.setAdapter(subjectAdapter);
        subjectAdapter.addHeaderView(getHeadView());

        initData();

        addListener();

    }

    private void addListener() {
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                smartRefresh.finishRefresh(2000);
                start = 1;
                initData();
            }
        });

        subjectAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                start++;
                initData();
            }
        },rvSmart);
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("start",start);
        map.put("count","20");
        RetrofitServiceManager.getInstance().getMovieData(new ProgressSubscriber<MovieSubject>(SmartrefreshActivity.this,true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable throwable) {
                int statusCode = throwable.code;
                String message = throwable.message;
                Log.e("sss", "onError: "+message+"   "+statusCode );
                ToastByStr("请求失败"+message+"   "+statusCode);
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onNext(MovieSubject movieSubject) {
                super.onNext(movieSubject);
                ToastByStr("请求成功"+movieSubject.getTitle());
                if(start>1){
                    subjectAdapter.addData(mMovieSubject.getSubjects());
                    subjectAdapter.loadMoreComplete();
                }else{
                    mMovieSubject = movieSubject;
                    subjectAdapter.setNewData(mMovieSubject.getSubjects());
                }

                setBinner();
            }
        },map);
    }

    private void setBinner() {

        if(subjectAdapter!=null){
            castsBeanList = subjectAdapter.getData().get(0).getCasts();
            if(castsBeanList.size()>1){
                banner.setCanLoop(true);
                banner.setScrollDuration(2000);
//                banner.set
            }else{
                banner.setCanLoop(false);
            }
            banner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
                @Override
                public LocalImageHolderView createHolder() {
                    return new LocalImageHolderView();
                }
            },castsBeanList)
            .setPageIndicator(new int[]{R.drawable.mor,R.drawable.pro})
            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
            ;
        }
    }


    public void ToastByStr(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public View getHeadView() {
        View headView = View.inflate(this,R.layout.head_smart,null);
        banner = (ConvenientBanner) headView.findViewById(R.id.banner);

        return headView;
    }
}
