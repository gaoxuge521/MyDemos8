package com.gxg.demo8.mydemo8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gxg.demo8.mydemo8.customaccelerateball.CustomAccelerateBallActivity;
import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.HttpTestActivity;
import com.gxg.demo8.mydemo8.smartRefresh.SmartrefreshActivity;
import com.gxg.demo8.mydemo8.tagcloudview.TagCloudViewActivity;
import com.gxg.demo8.mydemo8.tinker.TinkerTestActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tinker)
    TextView tinker;
    @Bind(R.id.other)
    TextView other;
    @Bind(R.id.smartRefresh)
    TextView smartRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.CustomAccelerateBall,R.id.TagCloudView,R.id.tinker, R.id.other,R.id.smartRefresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.smartRefresh:
                startActivityByClass(SmartrefreshActivity.class);
                break;
            case R.id.tinker:
                startActivityByClass(TinkerTestActivity.class);
                break;

            case R.id.other:
                startActivityByClass(HttpTestActivity.class);
                break;
            case R.id.TagCloudView:
                startActivityByClass(TagCloudViewActivity.class);
                break;
            case R.id.CustomAccelerateBall:
                startActivityByClass(CustomAccelerateBallActivity.class);
                break;
        }
    }

    private void startActivityByClass(Class  cls) {
        startActivity(new Intent(this,cls));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
