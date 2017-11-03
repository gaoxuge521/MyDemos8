package com.gxg.demo8.mydemo8.tinker;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.gxg.demo8.mydemo8.R;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TinkerTestActivity extends AppCompatActivity {

    @Bind(R.id.test)
    Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinker_test);
        ButterKnife.bind(this);

        // 加载补丁
        loadPatch();
    }

    private void loadPatch() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/patch.apk";
        Log.e("sss", "loadPatch: "+path );
        File file = new File(path);
        if (file.exists()){
            // 加载补丁

            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
            Toast.makeText(this, "补丁存在"+path, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "补丁不存在", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.test)
    public void onViewClicked() {

//        int i=1/0;  // 当点击这个button的时候肯定会崩溃
        Toast.makeText(this, "我被修好了,不崩溃了", Toast.LENGTH_SHORT).show();
    }
}
