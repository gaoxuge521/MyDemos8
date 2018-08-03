package com.gxg.demo8.mydemo8.toobarDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxg.demo8.mydemo8.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：Administrator on 2018/6/28 09:10
 * 邮箱：android_gaoxuge@163.com
 */
public class ToobarDemoActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.ll_right)
    LinearLayout llRight;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toobar_test);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        title.setText("这是标题这是标题这是标题这是标题");

    }

    @OnClick({R.id.ll_left, R.id.ll_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_right:
                break;
        }
    }
}
