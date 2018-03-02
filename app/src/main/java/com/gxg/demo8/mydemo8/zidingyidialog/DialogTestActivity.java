package com.gxg.demo8.mydemo8.zidingyidialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.gxg.demo8.mydemo8.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：Administrator on 2018/3/2 13:48
 * 邮箱：android_gaoxuge@163.com
 */
public class DialogTestActivity extends AppCompatActivity {
    @Bind(R.id.btn_dialog)
    Button btnDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_test);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_dialog)
    public void onViewClicked() {
        ErrorDialog errorDialog = new ErrorDialog(DialogTestActivity.this,R.style.Dialog);
        errorDialog.show();
    }
}
